package com.kousuan.account.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kousuan.account.entity.MistakeHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 崔光怀
 * @date 2023/05/07
 */

@Mapper
@Repository // 添加此注解
public interface MistakeHistoryMapper extends BaseMapper<MistakeHistoryEntity> {

    /**
     * 批量插入或更新错题记录
     */
    void batchInsertOrUpdate(@Param("list") List<MistakeHistoryEntity> mistakeHistoryEntities);
}
