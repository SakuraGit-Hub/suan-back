package com.kousuan.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.PracticeHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
=======
import org.apache.ibatis.annotations.Select;

import java.util.Map;
>>>>>>> bdb231c527421bd17f768ef8ea6ff27cf00c9f2a

@Mapper
public interface PracticeHistoryMapper extends BaseMapper<PracticeHistoryEntity> {
<<<<<<< HEAD
   Integer getIdByUserIdAndDateTime(@Param("userId") Integer userId, @Param("dateTime") String dateTime);

=======

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
>>>>>>> bdb231c527421bd17f768ef8ea6ff27cf00c9f2a
}
