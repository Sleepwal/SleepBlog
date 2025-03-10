import request from "@/utils/request";

/**
 * 查询分类列表
 * @returns {*}
 */
export function getCategoryList() {
    return request({
        url: '/category/getCategoryList',
        headers: {
            isToken: false
        },
        method: 'get'
    })
}
