package com.kousuan.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ljx
 */

@Data
@TableName("account")
@Accessors(chain = true)
public class AccountEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    @TableField("headShot")
    private String headShot;

    @TableField("classroom")
    private String classroom;

    @TableField("phoneNumber")
    private String phoneNumber;

    @TableField("isHidden")
    private Integer isHidden;

}
