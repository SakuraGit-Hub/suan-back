package com.kousuan.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("system_settings")
@Accessors(chain = true)
public class SystemSettingsEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("soundSwitch")
    private Boolean soundSwitch;

    @TableField("shockSwitch")
    private Boolean shockSwitch;

    @TableField("warnSwitch")
    private Boolean warnSwitch;

    @TableField("answerTime")
    private Integer answerTime;

    @TableField("questionNums")
    private Integer questionNums;

    @TableField("level")
    private String level;
}
