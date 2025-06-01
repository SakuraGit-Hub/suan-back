package com.kousuan.account.service;

import com.kousuan.account.entity.CheckedDaysEntity;
import com.kousuan.account.entity.dto.LearningStatsDTO;
import com.kousuan.account.mapper.LoginDayMapper;
import com.kousuan.account.mapper.PracticeContentMapper;
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
    private PracticeContentMapper practiceContentMapper;

    @Resource
    private LoginDayMapper loginDayMapper;

    @Override
    public Result<LearningStatsDTO> getUserStats(Integer userId) {
        try {
            LearningStatsDTO statsDTO = new LearningStatsDTO();

            // 修正：统计练习记录行数（而不是题目数量总和）
            Integer practiceCount = practiceContentMapper.countByUserId(userId);
            statsDTO.setCumulativePractice(practiceCount != null ? practiceCount : 0);

            // 计算正确率
            Map<String, Object> result = practiceContentMapper.sumCorrectAndTotalNums(userId);
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
            CheckedDaysEntity record = loginDayMapper.getByUserIdAndMonth(userId, month);
            if (record == null || record.getCheckedDays() == null) {
                break;
            }

            String days = record.getCheckedDays();
            if (days.length() < 31) {
                days = String.format("%-31s", days).replace(' ', '0');
            }

            int maxDay = currentDate.lengthOfMonth();
            int startDay = (currentDate.equals(today)) ? today.getDayOfMonth() : maxDay;
            boolean isContinuous = true;

            for (int i = startDay - 1; i >= 0; i--) {
                if (days.charAt(i) != '1') {
                    isContinuous = false;
                    break;
                }
                continuousCount++;
            }

            if (!isContinuous) {
                break;
            }

            currentDate = currentDate.minusMonths(1);
        }

        return continuousCount;
    }
}