package com.kousuan.account.service;

import com.kousuan.account.mapper.PracticeHistoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-06-01
 * @Description: 实现通过userId和dateTime返回历史的id
 * @Version: 1.0
 */

@Service
public class PracticeHistoryService implements  IPracticeHistoryService{

    @Resource
    private final PracticeHistoryMapper PracticeHistoryMapper;

    public PracticeHistoryService(com.kousuan.account.mapper.PracticeHistoryMapper practiceHistoryMapper) {
        PracticeHistoryMapper = practiceHistoryMapper;
    }

    @Override
    public Integer getIdByUserIdAndDateTime(Integer userId, String dateTime) {
        return PracticeHistoryMapper.getIdByUserIdAndDateTime(userId, dateTime);
    }
}
