package com.kousuan.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.PracticeHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PracticeHistoryMapper extends BaseMapper<PracticeHistoryEntity> {
   Integer getIdByUserIdAndDateTime(@Param("userId") Integer userId, @Param("dateTime") String dateTime);

}
