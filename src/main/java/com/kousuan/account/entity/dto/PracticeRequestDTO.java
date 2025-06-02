package com.kousuan.account.entity.Dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-06-01
 * @Description: 来封装练习记录
 * @Version: 1.0
 */
@Data
public class PracticeRequestDTO {
    private String label;
    private String dateTime;
    private Integer timeSpent;
    private Integer questionNums;
    private Integer correctNums;
    private String type;
    private String icon;
    private String difficulty;
    private List<DataItem> data;

    // Getters and Setters
    @Data
    public static class DataItem {
        private Integer userId;
        private String question;
        private String userAnswer;
        private String successAnswer;
        private String type;
        private String dateTime;
        private String analyse;
        private Integer errorNums;

        // Getters and Setters
    }

    // Getter and Setter methods for all fields
}