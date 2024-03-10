import request from '@/utils/request'

// 查询分类列表
export function getTagList() {
    return request({
        url: '/tag/getTagList',
        headers: {
            isToken: false
        },
        method: 'get'
    })
}
