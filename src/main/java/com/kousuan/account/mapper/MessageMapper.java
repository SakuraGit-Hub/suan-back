package com.kousuan.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.MessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MessageMapper extends BaseMapper<MessageEntity> {
}