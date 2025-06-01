package com.kousuan.account.controller;

import com.kousuan.account.service.IAccountService;
import com.kousuan.common.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ljx
 */

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;


    /**
     * 用户注册接口
     * @param params 包含username和password的Map
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String,String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return accountService.register(username,password);
    }

    /**
     * 用户登录接口
     * @param params 包含username和password的Map
     * @return 登录结果(JWT令牌)
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return accountService.login(username,password);
    }
}
