//查询所有分类
import request from "@/utils/request";

export function getAllKnowCate() {
    return request({
        url: "/admin/knowledge/cateList",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

//添加、修改分类
export function addOrUpdateKnowCategory(data) {
    return request({
        url: '/admin/knowledge/addOrUpdateKnowCategory',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

//删除分类
export function deleteKnowCategory(id) {
    return request({
        url: '/admin/knowledge/deleteKnowCategory/' + id,
        headers: {
            isToken: true
        },
        method: 'delete',
    })
}

//文档
/**
 * 获取所有文档
 * @returns {*}
 */
export function getDocumentList() {
    return request({
        url: "/admin/knowledge/documentList",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

export function documentList4Chart() {
    return request({
        url: "/admin/knowledge/documentList4Chart",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

//添加、修改文档
export function addOrUpdateKnowDocument(data) {
    return request({
        url: '/admin/knowledge/addOrUpdateKnowDocument',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

//删除文档
export function deleteKnowDocument(id) {
    return request({
        url: '/admin/knowledge/deleteKnowDocument/' + id,
        headers: {
            isToken: true
        },
        method: 'delete',
    })
}

//文章
/**
 * 获取文档对应的文章
 * @param documentId
 * @returns {*}
 */
export function getArticleList(documentId) {
    return request({
        url: "/admin/knowledge/articleList/" + documentId,
        method: "get",
        headers: {
            isToken: true
        },
    });
}


/**
 * 获取文章详情
 * @param articleId
 * @returns {*}
 */
export function knowArticleDetail(articleId) {
    return request({
        url: "/admin/knowledge/knowArticleDetail/" + articleId,
        headers: {
            isToken: true
        },
        method: "get"
    });
}


/**
 * 添加知识库文章
 * @param data
 * @returns {*}
 */
export function addKnowArticle(data) {
    return request({
        url: "/admin/knowledge/addKnowArticle",
        headers: {
            isToken: true
        },
        method: "post",
        data: data
    });
}

/**
 * 更新知识库文章
 * @param data
 * @returns {*}
 */
export function updateKnowArticle(data) {
    return request({
        url: "/admin/knowledge/updateKnowArticle",
        headers: {
            isToken: true
        },
        method: "PUT",
        data: data
    });
}

/**
 * 删除知识库文章
 * @param articleId
 * @returns {*}
 */
export function deleteKnowArticle(articleId) {
    return request({
        url: "/admin/knowledge/deleteKnowArticle/" + articleId,
        headers: {
            isToken: true
        },
        method: "DELETE"
    });
}

/**
 * 获取知识库文章总数
 * @returns {*}
 */
export function getTotalKnowArticleViewCount() {
    return request({
        url: "/admin/knowledge/totalArticleViewCount",
        headers: {
            isToken: true
        },
        method: "get"
    });
}
