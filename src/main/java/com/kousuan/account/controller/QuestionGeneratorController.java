

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-06-02
 * @Description: 用来控制题目的生成
 * @Version: 1.0
 */

package com.kousuan.account.controller;

import com.kousuan.account.service.IQuestionGenerationStrategy;
import com.kousuan.account.service.QuestionGenerationStrategyService;
import com.kousuan.common.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/questionGenerator")
public class QuestionGeneratorController {

    private final QuestionGenerationStrategyService questionGenerationStrategy;

    public QuestionGeneratorController(QuestionGenerationStrategyService questionGenerationStrategy) {
        this.questionGenerationStrategy = questionGenerationStrategy;
    }


    @PostMapping("/generateQuestions")
    public Result<Map<String, List<Map<String, Object>>>> generateQuestions(@RequestBody Map<String, Object> request) {
        // 解析请求参数
        List<String> operationTypes = (List<String>) request.get("operationTypes");
        Integer questionCount = (Integer) request.get("questionCount");
        String numberRange = (String) request.get("numberRange");

        // 调用服务生成题目
        Map<String, List<Map<String, Object>>> questions = questionGenerationStrategy.generateQuestions(operationTypes, questionCount, numberRange);

        // 返回结果
        return Result.success(questions);
    }
}
