package com.kousuan.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ljx
 */

@Mapper
@Repository // 添加此注解
public interface AccountMapper extends BaseMapper<AccountEntity> {
}
