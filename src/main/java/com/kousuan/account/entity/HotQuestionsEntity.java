package com.kousuan.account.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("hot_questions")
@Accessors(chain = true)
public class HotQuestionsEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("content")
    private String content;

    @TableField("viewCount")
    private Integer viewCount;

    @TableField("createTime")
    private String createTime;

}
