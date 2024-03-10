import request from "@/utils/request";

// 查询文章列表，模糊搜索
export function getArticleSearchList(query) {
    return request({
        url: "/admin/article/articleSearchList",
        method: "get",
        headers: {
            isToken: true
        },
        data: query
    });
}

//新增文章
export function addArticle(data) {
    return request({
        url: "/admin/article/addArticle",
        method: "post",
        headers: {
            isToken: true
        },
        data: data
    });
}

//新增文章
export function updateArticle(data) {
    return request({
        url: "/admin/article/updateArticle",
        method: "put",
        headers: {
            isToken: true
        },
        data: data
    });
}

//获取编辑文章详情
export function editArticleDetail(articleId) {
    return request({
        url: "/admin/article/editArticleDetail/" + articleId,
        headers: {
            isToken: true
        },
        method: "get"
    });
}

//自动保存
export function articleAutoSave(data) {
    return request({
        url: "/admin/article/articleAutoSave",
        method: "post",
        headers: {
            isToken: true
        },
        data: data
    });
}

//删除文章
export function deleteArticle(articleId) {
    return request({
        url: "/admin/article/deleteArticle/" + articleId,
        headers: {
            isToken: true
        },
        method: "delete"
    });
}

// 查询所有文章列表
export function getArticleList() {
    return request({
        url: "/admin/article/articleList",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

//查询文章数量
export function getArticleCount() {
    return request({
        url: "/admin/article/articleCount",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

//查询文章总访问数
export function getArticleTotalViewCount() {
    return request({
        url: "/admin/article/articleTotalViewCount",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

export function getArticleRelationGraphData() {
    return request({
        url: "/admin/article/relationGraphData",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

export function getArticleRelationGraphLinks() {
    return request({
        url: "/admin/article/relationGraphLinks",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

/**
 * 文章贡献活跃度
 * @returns {*}
 */
export function getArticleActivity() {
    return request({
        url: "/admin/article/articleActivity",
        method: "get",
        headers: {
            isToken: true
        },
    });
}


