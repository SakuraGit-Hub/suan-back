package com.kousuan.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.CheckedDaysEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginDayMapper extends BaseMapper<CheckedDaysEntity> {

    /**
     * 根据用户ID和年月查询打卡记录
     */
    @Select("SELECT * FROM checked_days WHERE userId = #{userId} AND month = #{month}")
    CheckedDaysEntity getByUserIdAndMonth(@Param("userId") Integer userId, @Param("month") String month);
}
