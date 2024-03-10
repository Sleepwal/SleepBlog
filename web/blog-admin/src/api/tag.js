import request from "@/utils/request";

// 查询分类列表
export function getTagListPage(params) {
    return request({
        url: '/admin/tag/tagListPage',
        headers: {
            isToken: true
        },
        method: 'get',
        data: params
    })
}

export function addOrUpdateTag(params) {
    return request({
        url: '/admin/tag/addOrUpdateTag',
        headers: {
            isToken: true
        },
        method: 'post',
        data: params
    })
}

export function deleteTag(id) {
    return request({
        url: '/admin/tag/deleteTag/' + id,
        headers: {
            isToken: true
        },
        method: 'delete',
    })
}

export function getTagList() {
    return request({
        url: '/admin/tag/getTagList',
        headers: {
            isToken: true
        },
        method: 'get'
    })
}

