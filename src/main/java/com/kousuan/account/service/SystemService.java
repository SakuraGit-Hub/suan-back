package com.kousuan.account.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kousuan.account.entity.SystemSettingsEntity;
import com.kousuan.account.entity.dto.SystemDTO;
import com.kousuan.account.mapper.SystemSettingsMapper;
import com.kousuan.common.util.Result;
import org.springframework.stereotype.Service;

/**
 * 设置服务实现类
 */
@Service
public class SystemService extends ServiceImpl<SystemSettingsMapper, SystemSettingsEntity> implements ISystemService {

    @Override
    public Result<Boolean> updateSettings(Integer userId, SystemDTO settingsDTO) {
        try {
            // 查询用户现有设置
            SystemSettingsEntity settings = this.lambdaQuery()
                    .eq(SystemSettingsEntity::getUserId, userId)
                    .one();

            if (settings == null) {
                // 若记录不存在则创建新记录
                settings = new SystemSettingsEntity();
                settings.setUserId(userId);
            }

            // 更新设置
            settings.setSoundSwitch(settingsDTO.getSoundEffectChecked());
            settings.setShockSwitch(settingsDTO.getVibrationFeedbackChecked());
            settings.setWarnSwitch(settingsDTO.getDailyReminderChecked());
            settings.setAnswerTime(settingsDTO.getAnswerTime());
            settings.setQuestionNums(settingsDTO.getQuestionNum());
            settings.setLevel(settingsDTO.getDefaultDifficulty());

            // 保存或更新
            boolean success = this.saveOrUpdate(settings);
            return Result.success(success);
        } catch (Exception e) {
            log.error("更新系统设置失败", e);
            return Result.fail(500, "更新设置失败，请稍后重试");
        }
    }

    @Override
    public Result<SystemDTO> getSettingsByUserId(Integer userId) {
        try {
            SystemSettingsEntity settings = this.lambdaQuery()
                    .eq(SystemSettingsEntity::getUserId, userId)
                    .one();

            if (settings == null) {
                // 返回默认设置
                return Result.success(createDefaultSettingsDTO());
            }

            // 转换为DTO
            SystemDTO dto = new SystemDTO();
            dto.setSoundEffectChecked(settings.getSoundSwitch());
            dto.setVibrationFeedbackChecked(settings.getShockSwitch());
            dto.setDailyReminderChecked(settings.getWarnSwitch());
            dto.setAnswerTime(settings.getAnswerTime());
            dto.setQuestionNum(settings.getQuestionNums());
            dto.setDefaultDifficulty(settings.getLevel());

            return Result.success(dto);
        } catch (Exception e) {
            log.error("获取系统设置失败", e);
            return Result.fail(500, "获取设置失败，请稍后重试");
        }
    }

    private SystemDTO createDefaultSettingsDTO() {
        SystemDTO dto = new SystemDTO();
        dto.setSoundEffectChecked(false);
        dto.setVibrationFeedbackChecked(false);
        dto.setDailyReminderChecked(true);
        dto.setAnswerTime(30);
        dto.setQuestionNum(20);
        dto.setDefaultDifficulty("一年级");
        return dto;
    }
}