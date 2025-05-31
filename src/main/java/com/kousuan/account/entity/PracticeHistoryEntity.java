package com.kousuan.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("practice_history")
@Accessors(chain = true)
public class PracticeHistoryEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("label")
    private String label;

    @TableField("dateTime")
    private String dateTime;

    @TableField("timeSpent")
    private Integer timeSpent;

    @TableField("questionNums")
    private Integer questionNums;

    @TableField("correctNums")
    private Integer correctNums;

    @TableField("type")
    private String type;

    @TableField("icon")
    private String icon;

    @TableField("difficulty")
    private String difficulty;

}
