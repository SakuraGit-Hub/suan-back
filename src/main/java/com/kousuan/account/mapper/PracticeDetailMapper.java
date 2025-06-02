package com.kousuan.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.MistakeHistoryEntity;
import com.kousuan.account.entity.PracticeDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeDetailMapper extends BaseMapper<PracticeDetailEntity> {
    /**
     * 批量插入或更新做题记录
     */
    void batchInsertOrUpdate(@Param("list") List<PracticeDetailEntity> practiceDetailEntities);
}
