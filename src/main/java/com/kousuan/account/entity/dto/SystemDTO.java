package com.kousuan.account.entity.dto;

import lombok.Data;

@Data
public class SystemDTO {
    private Boolean soundEffectChecked;
    private Boolean vibrationFeedbackChecked;
    private Boolean dailyReminderChecked;
    private Integer answerTime;
    private Integer questionNum;
    private String defaultDifficulty;
}
