package com.kousuan.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.servlet.tags.EditorAwareTag;

/**
 * @Author: 崔光怀
 * @CreateTime: 2025-05-30
 * @Description: 错题记录
 * @Version: 1.0
 */

@Data
@TableName("mistakes_history")
@Accessors(chain = true)
public class MistakeHistoryEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("question")
    private String question;

    @TableField("userAnswer")
    private String userAnswer;

    @TableField("dateTime")
    private String dateTime;

    @TableField("successAnswer")
    private String successAnswer;

    @TableField("analyse")
    private String analyse;

    @TableField("type")
    private String type;

    @TableField("errorNums")
    private Integer errorNums;

}
