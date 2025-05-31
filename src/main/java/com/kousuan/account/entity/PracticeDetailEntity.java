package com.kousuan.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("practice_detail")
@Accessors(chain = true)
public class PracticeDetailEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("historyId")
    private Integer historyId;

    @TableField("question")
    private String question;

    @TableField("userAnswer")
    private String userAnswer;

    @TableField("successAnswer")
    private String successAnswer;

    @TableField("answerStatus")
    private Integer answerStatus;

    @TableField("type")
    private String type;


}
