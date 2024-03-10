import request from "@/utils/request";


/**
 * 系统日志列表
 * @param params
 * @returns {*}
 */
export function getSystemLogList(params) {
    return request({
        url: '/admin/sysLog/logListByPage',
        headers: {
            isToken: true
        },
        method: 'get',
        data: params
    })
}

/**
 * 删除系统日志
 * @param id
 * @returns {*}
 */
export function deleteSystemLog(id) {
    return request({
        url: '/admin/sysLog/deleteSystemLog/' + id,
        headers: {
            isToken: true
        },
        method: 'delete',
    })
}

/**
 * 用户活跃度统计
 * @param id
 * @returns {*}
 */
export function getHourlyActivity() {
    return request({
        url: '/admin/sysLog/getHourlyActivity',
        headers: {
            isToken: true
        },
        method: 'get',
    })
}

