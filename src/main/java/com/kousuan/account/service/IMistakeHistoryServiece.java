package com.kousuan.account.service;

import com.kousuan.account.entity.MistakeHistoryEntity;
import com.kousuan.common.util.Result;

import java.util.List;

public interface IMistakeHistoryServiece {


    /**
     * 根据userID来获取错题
     * @param userId
     * @return
     */
    Result<List<MistakeHistoryEntity>> getMistakeHistoryByUserId(Integer userId);



    /**
     * 修改错题
     * @param userId
     * @param id
     * @param question
     * @param userAnswer
     * @param successAnswer
     * @param analyse
     * @param type
     * @param errorNums
     * @return
     */

    Result<String> updateMistakeHistory(Integer userId,
                                       int id,
                                       String question,
                                       String userAnswer,
                                       String successAnswer,
                                       String analyse,
                                       String type,
                                       int errorNums);

}
