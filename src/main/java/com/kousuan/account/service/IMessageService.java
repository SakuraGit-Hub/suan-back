package com.kousuan.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kousuan.account.entity.MessageEntity;
import com.kousuan.common.util.Result;

import java.util.List;

/**
 * 消息服务接口
 */
public interface IMessageService extends IService<MessageEntity> {

    /**
     * 获取用户消息列表
     * @param userId 用户ID
     * @return 消息列表结果
     */
    Result<List<MessageEntity>> getMessageList(String userId);
    // 获取系统通知（type=system）
    Result<List<MessageEntity>> getSystemMessages(Integer userId);
    // 获取消息提醒（type=reminder）
    Result<List<MessageEntity>> getReminderMessages(Integer userId);

    /**
     * 标记消息为已读
     * @param userId 用户ID
     * @param messageId 消息ID
     * @return 操作结果
     */
    Result<?> markMessageAsRead(String userId, Integer messageId);
    //未读消息全部已读
    Result<?> markAllAsRead(Integer userId);

    /**
     * 获取用户未读消息数量
     * @param userId 用户ID
     * @return 未读消息数量
     */
    Result<Integer> getUnreadCount(String userId);

    /**
     * 获取指定类型的未读消息数量
     * @param userId 用户ID
     * 消息类型（如 {"system", "reminder"}）
     * @return 未读数量
     */
    Result<Integer> getSystemUnreadCount(Integer userId);
    Result<Integer> getReminderUnreadCount(Integer userId);

    /**
     * 删除消息
     * @param userId 用户ID
     */
    Result<?> deleteMessage(Integer userId, Integer messageId);

    /**
     * 显示消息详情
     * @param userId 用户ID
     * @return 消息详情
     */
    Result<MessageEntity> getMessageById(Integer userId, Integer messageId);
}