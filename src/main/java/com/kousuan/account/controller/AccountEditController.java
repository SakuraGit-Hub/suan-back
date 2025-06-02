package com.kousuan.account.controller;

import com.kousuan.account.entity.dto.AccountEditDto;
import com.kousuan.account.mapper.AccountMapper;
import com.kousuan.account.service.IAccountEditService;
import com.kousuan.common.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountEditController {

    private final IAccountEditService accountService;
    private final AccountMapper accountMapper;

    /**
     * 用户资料编辑接口
     *
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public Result<Boolean> editAccountInfo(@RequestBody AccountEditDto editDto,
                                           HttpServletRequest request) {
        String currentUserId = (String) request.getAttribute("userId");
        if (currentUserId == null) {
            return Result.fail(401, "未获取到用户ID");
        }

        return accountService.editAccountInfo(currentUserId, editDto);
    }

    /**
     * 用户头像更换接口
     *
     * @return 编辑结果
     */
    @PostMapping("/upload-avatar")
    public Result<?> uploadAvatar(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return accountService.updateAvatar(userId, file);
    }
}
