package com.kousuan.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kousuan.account.entity.SystemSettingsEntity;
import com.kousuan.account.entity.dto.SystemDTO;
import com.kousuan.common.util.Result;

public interface ISystemService extends IService<SystemSettingsEntity> {
    /**
     * 更新用户系统设置
     */
    Result<Boolean> updateSettings(Integer userId, SystemDTO settingsDTO);

    /**
     * 获取用户系统设置
     */
    Result<SystemDTO> getSettingsByUserId(Integer userId);
}