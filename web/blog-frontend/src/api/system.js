import request from "@/utils/request";

/**
 * 文章总浏览量
 * @returns {*}
 */
export function getTotalViewCount() {
    return request({
        url: '/system/totalViewCount',
        headers: {
            isToken: false
        },
        method: 'get'
    })
}

/**
 * 用户访问量+1
 * @returns {*}
 */
export function increaseAccessCount() {
    return request({
        url: '/system/increaseAccessCount',
        headers: {
            isToken: false
        },
        method: 'get'
    })
}

/**
 * 获取用户访问量
 * @returns {*}
 */
export function getAccessCount() {
    return request({
        url: '/system/getAccessCount',
        headers: {
            isToken: false
        },
        method: 'get'
    })
}



export function getBlogInfoCount() {
    return request({
        url: '/system/blogInfoCount',
        headers: {
            isToken: false
        },
        method: 'get'
    })
}