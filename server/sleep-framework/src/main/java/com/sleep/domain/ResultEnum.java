package com.sleep.domain;

import lombok.Getter;

/**
 * 返回结果类枚举
 *
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 12:26
 */
@Getter
public enum ResultEnum {
    SUCCESS(200,"操作成功"),
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONE_NUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_IS_NULL(506, "评论内容为空"),
    FILE_TYPE_ERROR(507, "文件类型错误"),
    FILE_UPLOAD_ERROR(508, "文件上传失败"),
    FILE_IS_NULL(508,"请选择上传文件"),
    USERNAME_IS_NULL(509, "用户名为空，注册失败"),
    PASSWORD_IS_NULL(510, "密码为空, 注册失败"),
    EMAIL_IS_NULL(511, "邮箱为空, 注册失败"),
    NICKNAME_IS_NULL(512, "用户昵称为空, 注册失败"),
    TAG_NAME_EXIST(513,"标签名已存在"),
    SAVE_DATABASE_ERROR(600, "保存到数据库失败"),
    UPDATE_DATABASE_ERROR(600, "数据库更新数据失败"),
    DELETE_DATABASE_ERROR(600, "数据库删除数据失败"),
    SELECT_DATABASE_EMPTY(600, "数据库查询数据为空"),
    ADD_ARTICLE_ERROR(500, "添加新文章失败"),
    UPDATE_ARTICLE_ERROR(500, "修改文章失败"),
    DELETE_ARTICLE_ERROR(500, "删除新文章失败");
    final int code;
    final String msg;

    ResultEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

}
