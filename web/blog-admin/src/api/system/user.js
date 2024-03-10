import request from "@/utils/request";

// 查询用户信息
export function getUserInfo() {
    return request({
        url: '/admin/user/info',
        method: 'get',
        headers: {
            isToken: true
        },
    })
}

// 修改用户
export function updateUser(data) {
    return request({
        url: '/admin/user/updateUserInfo',
        method: 'put',
        headers: {
            isToken: true
        },
        data: data,
    })
}

// 新增用户
export function addUser(data) {
    return request({
        url: '/admin/user/addUser',
        method: 'post',
        data: data
    })
}

//修改密码
export function updatePassword(data) {
    return request({
        url: '/admin/user/updatePassword',
        method: 'put',
        headers: {
            isToken: true
        },
        data: data,
    })
}

// 查询用户列表
export function userList() {
    return request({
        url: '/admin/user/userList',
        headers: {
            isToken: true
        },
        method: 'get',
    })
}

//删除用户
export function delUser(userId) {
    return request({
        url: '/admin/user/deleteUser/' + userId,
        headers: {
            isToken: true
        },
        method: 'delete'
    })
}

// 用户总数
export function getUserTotalCount() {
    return request({
        url: '/admin/user/userTotalCount',
        headers: {
            isToken: true
        },
        method: 'get',
    })
}


// 用户密码重置
export function resetUserPwd(userId, password) {
    const data = {
        userId,
        password
    }
    return request({
        url: '/system/user/resetPwd',
        method: 'put',
        data: data
    })
}

// 用户状态修改
export function changeUserStatus(userId, status) {
    const data = {
        userId,
        status
    }
    return request({
        url: '/system/user/changeStatus',
        method: 'put',
        data: data
    })
}
