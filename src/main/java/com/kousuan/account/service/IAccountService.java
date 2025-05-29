package com.kousuan.account.service;

import com.kousuan.account.entity.AccountEntity;
import com.kousuan.common.util.Result;

/**
 * @author ljx
 */

public interface IAccountService {

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return 注册结果
     */
    Result<String> register(String username, String password);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果(JWT令牌)
     */
    Result<String> login(String username, String password);

    Result<AccountEntity> getUserInfo(String userId);
}
