package com.sleep.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 知识库的文章(KnowArticle)表实体类
 *
 * @author SleepWalker
 * @since 2023-05-30 22:08:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("know_article")
public class KnowArticle  {
    @TableId
    private Long id;

    //标题
    private String title;
    //文章内容
    private String content;
    //所属文档id
    private Long documentId;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;
    //状态（0已发布，1草稿）
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}

