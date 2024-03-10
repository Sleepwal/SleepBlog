import request from "@/utils/request";

export function getCommentList(params) {
    return request({
        url: "/comment/commentList",
        headers: {
            isToken: false
        },
        method: "get",
        params: params
    })
}

//查询友链评论
export function getLinkComment(params) {
    return request({
        url: '/comment/linkCommentList',
        headers: {
            isToken: false
        },
        method: 'get',
        params: params
    })
}


export function addComment(data) {
    return request({
        url: "/comment/addComment",
        headers: {
            isToken: true
        },
        method: "post",
        data: data
    })
}

