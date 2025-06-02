package com.kousuan.account.service;

import com.kousuan.account.entity.MistakeHistoryEntity;
import com.kousuan.account.entity.PracticeDetailEntity;
import com.kousuan.account.entity.PracticeHistoryEntity;
import com.kousuan.account.mapper.MistakeHistoryMapper;
import com.kousuan.account.mapper.PracticeDetailMapper;
import com.kousuan.account.mapper.PracticeHistoryMapper;
import com.kousuan.common.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-06-01
 * @Description: 做题记录服务
 * @Version: 1.0
 */

@Service
public class PracticeRecordService implements IPracticeRecordService {

    @Autowired
    private MistakeHistoryMapper mistakeHistoryMapper;

    @Autowired
    private PracticeDetailMapper practiceDetailMapper;

    @Autowired
    private PracticeHistoryMapper practiceHistoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(PracticeRecordService.class);

    /**
     * 批量添加错题记录
     */
    @Override
    public Result<String> addMistakeHistory(List<MistakeHistoryEntity> mistakeHistoryEntities) {
        if (mistakeHistoryEntities == null || mistakeHistoryEntities.isEmpty()) {
            return Result.fail(400, "没有可添加的错题记录");
        }
        // 打印接收到的数据（调试用）
        logger.info("接收到的错题记录数量: {}", mistakeHistoryEntities.size());
        for (MistakeHistoryEntity entity : mistakeHistoryEntities) {
            logger.info("错题记录: {}", entity);
        }

        try {
            mistakeHistoryMapper.batchInsertOrUpdate(mistakeHistoryEntities);
            return Result.success("错题记录添加或更新成功");
        } catch (Exception e) {
            logger.error("错题记录插入或更新失败", e);
            return Result.fail(500, "数据库操作失败，请重试");
        }
    }

    /**
     * 批量添加练习详情记录
     */
    @Override
    public Result<String> addPracticeDetail(List<PracticeDetailEntity> practiceDetailEntities) {
        if (practiceDetailEntities == null || practiceDetailEntities.isEmpty()) {
            return Result.fail(400, "没有可添加的练习详情记录");
        }

        try {
            practiceDetailMapper.batchInsertOrUpdate(practiceDetailEntities);
            return Result.success("做题记录添加或更新成功");
        } catch (Exception e) {
            logger.error("错题记录插入或更新失败", e);
            return Result.fail(500, "数据库操作失败，请重试");
        }
    }

    /**
     * 添加练习历史记录
     */
    @Override
    public Result<String> addPracticeHistory(PracticeHistoryEntity practiceHistoryEntity) {
        if (practiceHistoryEntity == null) {
            return Result.fail(400, "练习记录不能为空");
        }

        boolean result = practiceHistoryMapper.insert(practiceHistoryEntity) > 0;
        if (result) {
            return Result.success("练习历史记录添加成功");
        } else {
            return Result.fail(500, "练习历史记录添加失败");
        }
    }
}
