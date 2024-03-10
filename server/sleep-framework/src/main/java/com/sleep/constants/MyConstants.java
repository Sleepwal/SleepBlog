package com.sleep.constants;

/**
 * 常量类
 *
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 12:19
 */

public class MyConstants {
    /**
     *
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章正常状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 默认当前页 1
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认页数 6
     */
    public static final int DEFAULT_PAGE_SIZE = 6;

    /**
     * 类型是正常状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";


    /**
     * 存入redis用户信息的key
     */
    public static final String LOGIN_USER_KEY = "blogLogin:";
    /**
     * 存入redis后台用户信息的key
     */
    public static final String LOGIN_ADMIN_USER_KEY = "AdminLogin:";
    /**
     * 文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 是首页置顶文章
     */
    public static final String IS_AUTO_ARTICLE = "1";
    /**
     * 菜单表正常状态
     */
    public static final String MENU_NORMAL_STATUS = "0";

    /**
     * 菜单表菜单类型
     */
    public static final String MENU_TYPE_MENU = "M";
    /**
     * 菜单表按钮类型
     */
    public static final String MENU_TYPE_BUTTON = "F";
    /**
     * 管理员类型
     */
    public static final String SUPER_USER_TYPE = "1";
    /**
     * 用户状态正常
     */
    public static final String USER_NORMAL_STATUS = "0";
}
