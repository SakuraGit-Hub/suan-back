package com.kousuan.account.service;

import com.kousuan.account.entity.CheckedDaysEntity;
import com.kousuan.account.entity.dto.LearningStatsDTO;
import com.kousuan.account.mapper.CheckedDaysMapper;
import com.kousuan.account.mapper.PracticeHistoryMapper;
import com.kousuan.common.util.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class LearningStatsService implements ILearningStatsService{
    @Resource
    private PracticeHistoryMapper practiceHistoryMapper;

    @Resource
    private CheckedDaysMapper checkedDaysMapper;

    @Override
    public Result<LearningStatsDTO> getUserStats(Integer userId) {
        try {
            LearningStatsDTO statsDTO = new LearningStatsDTO();

            // 统计练习记录总数
            Integer practiceCount = practiceHistoryMapper.countByUserId(userId);
            statsDTO.setCumulativePractice(practiceCount != null ? practiceCount : 0);

            // 计算正确率
            Map<String, Object> result = practiceHistoryMapper.sumCorrectAndTotalNums(userId);
            if (result != null && !result.isEmpty()) {
                BigDecimal correctNumsBD = (BigDecimal) result.get("totalCorrectNums");
                BigDecimal totalNumsBD = (BigDecimal) result.get("totalQuestionNums");

                if (totalNumsBD != null && totalNumsBD.signum() != 0 && correctNumsBD != null) {
                    double accuracy = (correctNumsBD.doubleValue() / totalNumsBD.doubleValue()) * 100;
                    statsDTO.setAccuracyRate((int) Math.round(accuracy));
                } else {
                    statsDTO.setAccuracyRate(0);
                }
            } else {
                statsDTO.setAccuracyRate(0);
            }

            // 计算连续打卡天数
            int continuousCheckins = calculateContinuousCheckins(userId);
            statsDTO.setContinuousCheckins(continuousCheckins);

            return Result.success(statsDTO);
        } catch (Exception e) {
            return Result.fail(500, "获取学习统计信息失败: " + e.getMessage());
        }
    }

    private int calculateContinuousCheckins(Integer userId) {
        LocalDate today = LocalDate.now();
        int continuousCount = 0;
        LocalDate currentDate = today;

        while (currentDate != null) {
            String month = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            CheckedDaysEntity record = checkedDaysMapper.getByUserIdAndMonth(userId, month);

            // 跳过无记录的月份，继续检查上月
            if (record == null || record.getCheckedDays() == null) {
                currentDate = currentDate.minusMonths(1);
                continue; // 跳过，不中断
            }

            String days = record.getCheckedDays();
            if (days.length() < 31) {
                days = String.format("%-31s", days).replace(' ', '0');
            }

            int maxDay = currentDate.lengthOfMonth();
            int startDay = (currentDate.equals(today)) ? today.getDayOfMonth() : maxDay;
            boolean isContinuous = true;
            int currentMonthCount = 0;

            for (int i = startDay - 1; i >= 0; i--) {
                if (days.charAt(i) != '1') {
                    isContinuous = false;
                    break;
                }
                currentMonthCount++;
            }

            continuousCount += currentMonthCount;

            if (!isContinuous) {
                break;
            }

            currentDate = currentDate.minusMonths(1);
        }

        return continuousCount;
    }
}