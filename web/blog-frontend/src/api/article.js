import request from "@/utils/request";

// 查询文章列表
export function articleList(query) {
    return request({
        url: "/article/articleListByPage",
        method: "get",
        headers: {
            isToken: false
        },
        params: query
    });
}

/**
 * 查询归档文章列表
 * @param query
 * @returns {*}
 */
export function getTimeLineArticleList() {
    return request({
        url: "/article/timeLineArticleList",
        method: "get",
        headers: {
            isToken: false
        },
    });
}

/**
 * 根据标签id查询文章列表
 * @param query
 * @returns {*}
 */
export function articleListByTag(query) {
    return request({
        url: "/article/articleListByTag",
        method: "get",
        headers: {
            isToken: false
        },
        params: query
    });
}

//查询最热文章
export function hotArticleList() {
    return request({
        url: "/article/hotArticleList",
        headers: {
            isToken: false
        },
        method: "get"
    });
}

//根据标题或内容查询文章
export function articleSearch(param) {
    return request({
        url: "/article/articleSearch",
        headers: {
            isToken: false
        },
        method: "get",
        params: {
            searchText: param
        }
    });
}

//获取文章详情
export function getArticle(articleId) {
    return request({
        url: "/article/articleDetail/" + articleId,
        headers: {
            isToken: false
        },
        method: "get"
    });
}

export function updateViewCount(articleId) {
    return request({
        url: "/article/updateViewCount/" + articleId,
        headers: {
            isToken: false
        },
        method: "put"
    });
}

export function topAuthorList() {
    return request({
        url: "/article/articleAutoList",
        headers: {
            isToken: false
        },
        method: "get"
    })
}

/**
 * 知识图谱数据
 * @returns {*}
 */
export function articleRelationGraph() {
    return request({
        url: "/article/articleRelationGraph",
        headers: {
            isToken: false
        },
        method: "get"
    })
}

/**
 * 知识图谱链接，links
 * @returns {*}
 */
export function articleRelationGraphLinks() {
    return request({
        url: "/article/articleRelationGraphLinks",
        headers: {
            isToken: false
        },
        method: "get"
    })
}

/**
 * 单篇文章知识图谱结点和链接
 * @returns {*}
 */
export function singleArticleGraphById(id) {
    return request({
        url: "/article/singleArticleGraphById/" + id,
        headers: {
            isToken: false
        },
        method: "get"
    })
}