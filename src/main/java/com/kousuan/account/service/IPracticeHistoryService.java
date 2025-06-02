package com.kousuan.account.service;

public interface IPracticeHistoryService {
    /**
     * 通过用户userId和dateTime获取练习历史的id
     */
    Integer getIdByUserIdAndDateTime(Integer userId, String dateTime);
}
