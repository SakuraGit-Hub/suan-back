package com.kousuan.account.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("account_award")
@Accessors(chain = true)
public class AccountAwardEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("progress")
    private Integer progress;

    @TableField("coin")
    private Integer coin;

    @TableField("star")
    private Integer star;

}
