package com.kousuan.account.service;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-06-02
 * @Description: 实现生成题目的服务
 * @Version: 1.0
 */

@Service
public class QuestionGenerationStrategyService implements IQuestionGenerationStrategy{
    private static final Random RANDOM = new Random();

    @Override
    public Map<String, List<Map<String, Object>>> generateQuestions(List<String> operationTypes, Integer questionCount, String numberRange) {
        List<Map<String, Object>> questions = new ArrayList<>();

        for (int i = 0; i < questionCount; i++) {
            String operationType = operationTypes.get(RANDOM.nextInt(operationTypes.size()));
            int num1 = generateNumber(numberRange);
            int num2 = generateNumber(numberRange);

            Map<String, Object> question = new HashMap<>();
            int answer = 0;
            String questionStr = "";

            switch (operationType) {
                case "addition":
                    answer = num1 + num2;
                    questionStr = num1 + " + " + num2 + " = ?";
                    break;
                case "subtraction":
                    answer = num1 - num2;
                    questionStr = num1 + " - " + num2 + " = ?";
                    break;
                case "multiplication":
                    answer = num1 * num2;
                    questionStr = num1 + " * " + num2 + " = ?";
                    break;
                case "division":
                    if (num2 != 0) {
                        answer = num1 / num2;
                        questionStr = num1 + " / " + num2 + " = ?";
                    } else {
                        i--; // 如果除数为0，重新生成题目
                        continue;
                    }
                    break;
                case "mixed":
                    // 混合加减法，这里简化为随机选择加法或减法
                    boolean isAddition = RANDOM.nextBoolean();
                    if (isAddition) {
                        answer = num1 + num2;
                        questionStr = num1 + " + " + num2 + " = ?";
                    } else {
                        answer = num1 - num2;
                        questionStr = num1 + " - " + num2 + " = ?";
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operation type: " + operationType);
            }

            question.put("question", questionStr);
            question.put("answer", answer);
            questions.add(question);
        }

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        result.put("questions", questions);
        return result;
    }

    private int generateNumber(String numberRange) {
        switch (numberRange) {
            case "singleDigit":
                return RANDOM.nextInt(10) + 1;
            case "doubleDigit":
                return RANDOM.nextInt(100) + 1;
            case "tripleDigit":
                return RANDOM.nextInt(1000) + 1;
            default:
                throw new IllegalArgumentException("Unsupported number range: " + numberRange);
        }
    }
}
