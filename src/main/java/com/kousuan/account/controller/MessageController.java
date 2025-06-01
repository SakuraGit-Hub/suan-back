package com.kousuan.account.controller;

import com.kousuan.account.entity.MessageEntity;
import com.kousuan.account.service.IMessageService;
import com.kousuan.common.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户消息控制器
 */
@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {

    private final IMessageService messageService;

    /**
     * 获取当前用户的消息列表
     * @param request HTTP请求，用于获取当前用户ID
     * @return 消息列表结果
     */
    @GetMapping("/list")
    public Result<List<MessageEntity>> getMessageList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return messageService.getMessageList(userId);
    }
    // 系统通知列表（type=system）
    @GetMapping("/system")
    public Result<List<MessageEntity>> systemMessages(
            @RequestAttribute("userId") Integer userId // 假设用户ID已通过认证注入
    ) {
        return messageService.getSystemMessages(userId);
    }

    // 消息提醒列表（type=reminder）
    @GetMapping("/reminder")
    public Result<List<MessageEntity>> reminderMessages(
            @RequestAttribute("userId") Integer userId
    ) {
        return messageService.getReminderMessages(userId);
    }

    /**
     * 标记消息为已读
     * @param request HTTP请求，用于获取当前用户ID
     * @param messageId 消息ID
     * @return 操作结果
     */
    @PostMapping("/read/{messageId}")
    public Result<?> markMessageAsRead(
            HttpServletRequest request,
            @PathVariable Integer messageId
    ) {
        String userId = (String) request.getAttribute("userId");
        return messageService.markMessageAsRead(userId, messageId);
    }
    // 全部标记为已读接口
    @PostMapping("/all-read")
    public Result<?> markAllAsRead(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return messageService.markAllAsRead(Integer.valueOf(userId));
    }


    /**
     * 获取未读消息数量
     * @param request HTTP请求，用于获取当前用户ID
     * @return 未读消息数量结果
     */
    @GetMapping("/unread/count")
    public Result<Integer> getUnreadCount(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return messageService.getUnreadCount(userId);
    }

    /**
     * 获取未读消息数量
     * @param request HTTP请求，用于获取当前用户ID
     * @return type未读消息数量结果
     */
    // 系统通知未读数量
    @GetMapping("/system/unread-count")
    public Result<Integer> systemUnreadCount(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return messageService.getSystemUnreadCount(Integer.valueOf(userId));
    }

    // 消息提醒未读数量
    @GetMapping("/reminder/unread-count")
    public Result<Integer> reminderUnreadCount(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return messageService.getReminderUnreadCount(Integer.valueOf(userId));
    }

    /**
     * 单条消息删除（逻辑删除）
     */
    @DeleteMapping("/{messageId}")
    public Result<?> deleteMessage(@PathVariable Integer messageId,
                                   HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return messageService.deleteMessage(Integer.valueOf(userId), messageId);
    }

    /**
     * 获取消息详情
     */
    @GetMapping("/{messageId}")
    public Result<MessageEntity> getMessageDetail(@PathVariable Integer messageId,
                                                  HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return messageService.getMessageById(Integer.valueOf(userId), messageId);
    }
}