package com.kousuan.account.service;

import com.kousuan.account.entity.MistakeHistoryEntity;
import com.kousuan.account.entity.PracticeDetailEntity;
import com.kousuan.account.entity.PracticeHistoryEntity;
import com.kousuan.common.util.Result;

import java.util.List;

public interface IPracticeRecordService {

    /**
     * 添加错误记录
     *
     * @return
     */

    Result<String> addMistakeHistory(List<MistakeHistoryEntity> mistakeHistoryEntities);

    /**
     * 添加练习记录详情
     *
     */
    Result<String> addPracticeDetail(List<PracticeDetailEntity> practiceDetailEntities);

    /**
     * 添加练习记录
     *
     */

    Result<String> addPracticeHistory(PracticeHistoryEntity practiceHistoryEntity);
}
