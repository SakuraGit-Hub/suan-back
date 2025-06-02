package com.kousuan.account.entity.dto;

import lombok.Data;

import java.security.PrivateKey;

@Data
public class LearningStatsDTO {
    private Integer cumulativePractice;
    private Integer accuracyRate;
    private Integer continuousCheckins;
}