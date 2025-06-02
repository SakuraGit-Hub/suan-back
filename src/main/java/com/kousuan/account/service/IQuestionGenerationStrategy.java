package com.kousuan.account.service;



import java.util.List;
import java.util.Map;
public interface IQuestionGenerationStrategy {

    /**
     * 根据给定的参数生成题目
     * @param operationTypes 运算类型
     * @param questionCount 题目数量
     * @param numberRange 数字范围
     * @return 生成的题目列表
     */
    Map<String, List<Map<String, Object>>> generateQuestions(List<String> operationTypes, Integer questionCount, String numberRange);
}
