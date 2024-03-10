import request from '@/utils/request'

// 查询分类列表
export function getAllLink() {
    return request({
        url: '/link/getAllLink',
        headers: {
            isToken: false
        },
        method: 'get'
    })
}

export function applyLink(data) {
    return request({
        url: '/link/applyLink',
        headers: {
            isToken: false
        },
        method: 'post',
        data: data
    })
}
