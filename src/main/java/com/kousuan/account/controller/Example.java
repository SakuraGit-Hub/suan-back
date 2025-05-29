package com.kousuan.account.controller;


import com.kousuan.account.entity.AccountEntity;
import com.kousuan.account.service.IAccountService;
import com.kousuan.common.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务逻辑举例
 */

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class Example {

    private final IAccountService accountService;

    @GetMapping("/info")
    public Result<AccountEntity> getAccountInfo(HttpServletRequest request) {
        // 从请求属性中获取用户ID
        String userId = (String) request.getAttribute("userId");
        // 使用userId查询用户信息
        return  accountService.getUserInfo(userId);
    }

}
