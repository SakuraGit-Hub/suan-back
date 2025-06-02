package com.kousuan.account.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kousuan.account.entity.AccountEntity;
import com.kousuan.account.mapper.AccountMapper;
import com.kousuan.common.util.JwtUtil;
import com.kousuan.common.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ljx
 */

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService{
    private final AccountMapper accountMapper;
    private final JwtUtil jwtUtil;

    @Override
    public Result<String> register(String username, String password) {

        //参数校验
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.fail(400, "用户名和密码不能为空");
        }
        if (username.length() > 20) {
            return Result.fail(400, "用户长度要在20个字符内");
        }
        if (password.length() < 6) {
            return Result.fail(400, "密码长度不能少于6位");
        }

        //创建用户实体
        AccountEntity account = new AccountEntity();
        account.setName(username.trim());
        account.setPassword(password);

        //保持用户
        try {
            int result = accountMapper.insert(account);
            if (result > 0) {
                return Result.success("注册成功");
            }
            return Result.fail(500, "注册失败");
        } catch (DuplicateKeyException e) {
            return Result.fail(400, "用户名已存在");
        } catch (Exception e) {
            return Result.fail(500, "系统异常，请稍后再试");
        }
    }

    @Override
    public Result<String> login(String username, String password) {

        //参数校验
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.fail(400, "用户名和密码不能为空");
        }

        //查询用户
        QueryWrapper<AccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username.trim());
        AccountEntity account = accountMapper.selectOne(queryWrapper);
        if (account == null) {
            return Result.fail(401, "用户名或密码错误");
        }

        //验证密码 (实际项目应该使用加密验证)
        if (!account.getPassword().equals(password)) {
            return Result.fail(401, "用户名或密码错误");
        }

        // 生成JWT令牌
        String token = jwtUtil.generateToken(account.getId().toString());
        return Result.success(token);
    }

    public Result<AccountEntity> getUserInfo(String userId) {
        AccountEntity account = accountMapper.selectById(userId);
        if (account == null) {
            return Result.fail(404, "用户不存在");
        }
        return Result.success(account);
    }

}
