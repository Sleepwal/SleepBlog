import request from "@/utils/request";

// 分页查询友链列表
export function getLinkListByPage(params) {
    return request({
        url: '/admin/link/linkListByPage',
        headers: {
            isToken: true
        },
        method: 'get',
        data: params
    })
}

export function addOrUpdateLink(params) {
    return request({
        url: '/admin/link/addOrUpdateLink',
        headers: {
            isToken: true
        },
        method: 'post',
        data: params
    })
}

export function deleteLink(id) {
    return request({
        url: '/admin/link/deleteLink/' + id,
        headers: {
            isToken: true
        },
        method: 'delete',
    })
}

