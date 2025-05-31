package com.kousuan.account.entity.dto;


import lombok.Data;

import java.util.List;

@Data
public class AccountHomeDto {

    private String name;

    private String headShot;

    private String emailNum;

    private String progress;

    private List<HistoryItem> historyList;

    @Data
    public static class HistoryItem {
        private String title;
        private String iconPath;
        private String date;
        private String time;
        private Integer scores;
    }

}
