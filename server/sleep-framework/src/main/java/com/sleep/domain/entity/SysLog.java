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
 * 系统日志(SysLog)表实体类
 *
 * @author SleepWalker
 * @since 2023-12-05 12:08:17
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log")
public class SysLog  {
    //主键
    @TableId
    private Long id;

    //操作用户
    private String userType;
    //描述
    private String description;
    //路径
    private String requestType;
    
    private String url;
    //调用方法
    private String method;
    //ip地址
    private String ip;
    //参数
    private String parameter;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}

