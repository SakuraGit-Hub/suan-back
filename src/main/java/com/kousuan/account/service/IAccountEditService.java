package com.kousuan.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kousuan.account.entity.AccountEntity;
import com.kousuan.account.entity.dto.AccountEditDto;
import com.kousuan.common.util.Result;
import org.springframework.web.multipart.MultipartFile;

public interface IAccountEditService extends IService<AccountEntity> {
    // 编辑资料方法定义
    Result<Boolean> editAccountInfo(String userId, AccountEditDto editDto);

    /**
     * 更新用户头像
     * @param userId 用户ID
     * @param file 头像文件
     * @return 统一响应结果（包含头像URL）
     */
    Result<String> updateAvatar(String userId, MultipartFile file);
}
