package com.kousuan.account.service;

import com.kousuan.account.entity.dto.LearningStatsDTO;
import com.kousuan.common.util.Result;

public interface ILearningStatsService {
    /**
     * 获取用户学习统计信息
     * @param userId 用户ID
     * @return 包含累计练习、正确率和连续打卡的统计信息
     */
    Result<LearningStatsDTO> getUserStats(Integer userId);
}
