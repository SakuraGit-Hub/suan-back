package com.kousuan.account.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("checked_days")
@Accessors(chain = true)
public class CheckedDaysEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("month")
    private String month;

    @TableField("checkedDays")
    private String checkedDays;

}
