import request from "@/utils/request";

// 查询分类列表
export function getCommentListPage(params) {
    return request({
        url: '/admin/comment/commentListByPage',
        headers: {
            isToken: true
        },
        method: 'get',
        data: params
    })
}

export function getLinkCommentListPage(params) {
    return request({
        url: '/admin/comment/linkCommentListPage',
        headers: {
            isToken: true
        },
        method: 'get',
        data: params
    })
}

/**
 * 评论总数
 * @param params
 * @returns {*}
 */
export function getCommentCount() {
    return request({
        url: '/admin/comment/count',
        headers: {
            isToken: true
        },
        method: 'get',
    })
}

export function updateComment(params) {
    return request({
        url: '/admin/comment/updateComment',
        headers: {
            isToken: true
        },
        method: 'post',
        data: params
    })
}

export function deleteComment(id) {
    return request({
        url: '/admin/comment/deleteComment/' + id,
        headers: {
            isToken: true
        },
        method: 'delete',
    })
}
