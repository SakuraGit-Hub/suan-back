package com.kousuan.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kousuan.account.entity.AccountEntity;
import com.kousuan.account.entity.dto.AccountEditDto;
import com.kousuan.account.mapper.AccountMapper;
import com.kousuan.common.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountEditService extends ServiceImpl<AccountMapper, AccountEntity> implements IAccountEditService {

    private final AccountMapper accountMapper;

    //编辑用户信息
    @Override
    public Result<Boolean> editAccountInfo(String userId, AccountEditDto editDto) {
        try {
            LambdaUpdateWrapper<AccountEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AccountEntity::getId, userId);

            // 设置需要更新的字段（忽略null值）
            if (editDto.getName() != null) {
                updateWrapper.set(AccountEntity::getName, editDto.getName());

            }

            if (editDto.getEmail() != null) {
                updateWrapper.set(AccountEntity::getEmail, editDto.getEmail());

            }

            if (editDto.getClassroom() != null) {
                updateWrapper.set(AccountEntity::getClassroom, editDto.getClassroom());
            }

            if (editDto.getPhoneNumber() != null) {
                updateWrapper.set(AccountEntity::getPhoneNumber, editDto.getPhoneNumber());
            }

            int rows = accountMapper.update(null, updateWrapper);

            return rows > 0 ? Result.success(true) : Result.fail(400, "编辑资料失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "编辑资料时发生异常");
        }
    }

    @Value("${avatar.save.path}")
    private String savePath;

    @Value("${avatar.access.url}")
    private String accessUrlPrefix;

    @Override
    public Result<String> updateAvatar(String userId, MultipartFile file) {
        // 1. 校验用户（示例：根据userId查询数据库）
        AccountEntity account = this.lambdaQuery()
                .eq(AccountEntity::getId, Integer.valueOf(userId)) // 假设userId为数字ID
                .one();
        if (account == null) {
            return Result.fail(404, "用户不存在");
        }

        // 2. 校验文件
        if (file == null || file.isEmpty()) {
            return Result.fail(400, "文件为空");
        }

        // 3. 生成唯一文件名（避免覆盖前端已有文件）
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        File destFile = new File(savePath, fileName);

        // 4. 保存文件到前端 bar 目录
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            return Result.fail(500, "文件保存失败");
        }

        // 5. 更新数据库头像路径（前端可直接访问的相对路径）
        String avatarUrl = accessUrlPrefix + fileName;
        account.setHeadShot(avatarUrl);
        this.updateById(account);

        return Result.success(avatarUrl);
    }
}
