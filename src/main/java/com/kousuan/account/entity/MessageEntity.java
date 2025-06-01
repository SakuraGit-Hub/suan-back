package com.kousuan.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("account_news")
@Accessors(chain = true)
public class MessageEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("sendTime")
    private Date sendTime;

    @TableField("isRead")
    private Integer isRead;

    @TableField("type")
    private String type;

    @TableField("isHidden")
    private Integer isHidden;
}
