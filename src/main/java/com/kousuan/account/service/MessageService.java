package com.kousuan.account.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kousuan.account.entity.MessageEntity;
import com.kousuan.account.mapper.MessageMapper;
import com.kousuan.common.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息服务实现类
 */
@Service
public class MessageService extends ServiceImpl<MessageMapper, MessageEntity> implements IMessageService {

    @Override
    public Result<List<MessageEntity>> getMessageList(String userId) {
        try {
            LambdaQueryWrapper<MessageEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(MessageEntity::getUserId, userId)
                    .eq(MessageEntity::getIsHidden, 0) // 使用 getIsHidden() 方法
                    .orderByDesc(MessageEntity::getSendTime);

            List<MessageEntity> messages = list(queryWrapper);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.fail(500, "获取消息列表失败");
        }
    }

    @Override
    public Result<List<MessageEntity>> getSystemMessages(Integer userId) {
        LambdaQueryWrapper<MessageEntity> query = new LambdaQueryWrapper<>();
        query
                .eq(MessageEntity::getUserId, userId)
                .eq(MessageEntity::getType, "system") // 系统通知类型
                .eq(MessageEntity::getIsHidden, 0) // 未隐藏
                .orderByDesc(MessageEntity::getSendTime); // 按时间倒序
        return Result.success(list(query));
    }

    @Override
    public Result<List<MessageEntity>> getReminderMessages(Integer userId) {
        LambdaQueryWrapper<MessageEntity> query = new LambdaQueryWrapper<>();
        query
                .eq(MessageEntity::getUserId, userId)
                .eq(MessageEntity::getType, "reminder") // 消息提醒类型
                .eq(MessageEntity::getIsHidden, 0)
                .orderByDesc(MessageEntity::getSendTime);
        return Result.success(list(query));
    }

    @Override
    public Result<?> markMessageAsRead(String userId, Integer messageId) {
        try {
            MessageEntity message = getOne(
                    new LambdaQueryWrapper<MessageEntity>()
                            .eq(MessageEntity::getId, messageId)
                            .eq(MessageEntity::getUserId, userId)
            );
            if (message == null) {
                return Result.fail(404, "消息不存在");
            }
            message.setIsRead(1); // 使用 setIsRead() 方法
            boolean success = updateById(message);
            return success ? Result.success() : Result.fail(500, "标记失败");
        } catch (Exception e) {
            return Result.fail(500, "系统错误");
        }
    }

    @Override
    @Transactional
    public Result<?> markAllAsRead(Integer userId) {
        try {
            // 构建查询条件：用户ID、未读、未隐藏
            LambdaQueryWrapper<MessageEntity> query = new LambdaQueryWrapper<>();
            query
                    .eq(MessageEntity::getUserId, userId)
                    .eq(MessageEntity::getIsRead, 0) // 未读（0）
                    .eq(MessageEntity::getIsHidden, 0); // 未隐藏（0）

            // 批量更新为已读（1）
            boolean success = update(
                    new MessageEntity().setIsRead(1), // 更新内容
                    query // 更新条件
            );

            return success ? Result.success() : Result.fail(500, "标记失败");
        } catch (Exception e) {
            return Result.fail(500, "系统异常，标记失败");
        }
    }

    @Override
    public Result<Integer> getUnreadCount(String userId) {
        try {
            LambdaQueryWrapper<MessageEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(MessageEntity::getUserId, userId)
                    .eq(MessageEntity::getIsRead, 0) // 使用 getIsRead() 方法
                    .eq(MessageEntity::getIsHidden, 0);

            int count = (int) count(queryWrapper);
            return Result.success(count);
        } catch (Exception e) {
            return Result.fail(500, "获取未读消息数量失败");
        }
    }

    @Override
    public Result<Integer> getSystemUnreadCount(Integer userId) {
        try {
            LambdaQueryWrapper<MessageEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(MessageEntity::getUserId, userId)
                    .eq(MessageEntity::getIsRead, 0) // 使用 getIsRead() 方法
                    .eq(MessageEntity::getIsHidden, 0)
                    .eq(MessageEntity::getType, "system");

            int count = (int) count(queryWrapper);
            return Result.success(count);
        } catch (Exception e) {
            return Result.fail(500, "获取system未读消息数量失败");
        }
    }

    @Override
    public Result<Integer> getReminderUnreadCount(Integer userId) {
        try {
            LambdaQueryWrapper<MessageEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(MessageEntity::getUserId, userId)
                    .eq(MessageEntity::getIsRead, 0) // 使用 getIsRead() 方法
                    .eq(MessageEntity::getIsHidden, 0)
                    .eq(MessageEntity::getType, "reminder");

            int count = (int) count(queryWrapper);
            return Result.success(count);
        } catch (Exception e) {
            return Result.fail(500, "获取reminder未读消息数量失败");
        }
    }

    @Override
    @Transactional
    public Result<?> deleteMessage(Integer userId, Integer messageId) {
        try {
            // 验证消息存在且属于当前用户
            MessageEntity message = getOne(new LambdaQueryWrapper<MessageEntity>()
                    .eq(MessageEntity::getId, messageId)
                    .eq(MessageEntity::getUserId, userId)
                    .eq(MessageEntity::getIsHidden, 0)); // 仅处理未隐藏的消息

            if (message == null) {
                return Result.fail(404, "消息不存在或已删除");
            }

            // 逻辑删除：更新 isHidden 为 1（隐藏）
            message.setIsHidden(1);
            boolean success = updateById(message);

            return success ? Result.success() : Result.fail(500, "删除失败");
        } catch (Exception e) {
            return Result.fail(500, "系统异常，删除失败");
        }
    }

    @Override
    public Result<MessageEntity> getMessageById(Integer userId, Integer messageId) {
        try {
            // 查询用户的指定消息（未隐藏）
            MessageEntity message = getOne(
                    new LambdaQueryWrapper<MessageEntity>()
                            .eq(MessageEntity::getId, messageId)
                            .eq(MessageEntity::getUserId, userId)
                            .eq(MessageEntity::getIsHidden, 0) // 仅显示未隐藏的消息
            );

            if (message == null) {
                return Result.fail(404, "消息不存在或已删除");
            }

            // 可选：标记为已读（打开详情页即视为已读）
            message.setIsRead(1);
            updateById(message);

            return Result.success(message);
        } catch (Exception e) {
            return Result.fail(500, "系统异常，获取消息失败");
        }
    }

}