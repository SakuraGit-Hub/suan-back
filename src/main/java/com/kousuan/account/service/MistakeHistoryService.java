package com.kousuan.account.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kousuan.account.entity.MistakeHistoryEntity;
import com.kousuan.account.mapper.MistakeHistoryMapper;
import com.kousuan.common.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-05-30
 * @Description: 获取错题服务
 * @Version: 1.0
 */

@Service
@RequiredArgsConstructor
public class MistakeHistoryService implements  IMistakeHistoryServiece{


    private final MistakeHistoryMapper mistakeHistoryMapper;

    @Override
    public Result<String> updateMistakeHistory(Integer userId, int id, String question, String userAnswer, String successAnswer, String analyse, String type, int errorNums) {
        return null;
    }

    @Override
    public Result<List<MistakeHistoryEntity>> getMistakeHistoryByUserId(Integer userId) {
        LambdaQueryWrapper<MistakeHistoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MistakeHistoryEntity::getUserId, userId);

        List<MistakeHistoryEntity> list = mistakeHistoryMapper.selectList(wrapper);

        if (!list.isEmpty()) {
            return Result.success(list);
        } else {
            return  Result.fail(404, "没有找到该用户的错题");
        }
    }
}
