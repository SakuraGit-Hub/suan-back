package com.kousuan.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.PracticeHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface PracticeHistoryMapper extends BaseMapper<PracticeHistoryEntity> {

    /**
     * 统计用户练习记录行数
     */
    @Select("SELECT COUNT(*) FROM practice_history WHERE userId = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    /**
     * 统计用户正确题目数量和总题目数量
     */
    @Select("SELECT SUM(correctNums) AS totalCorrectNums, SUM(questionNums) AS totalQuestionNums " +
            "FROM practice_history WHERE userId = #{userId}")
    Map<String, Object> sumCorrectAndTotalNums(@Param("userId") Integer userId);
}
