import request from "@/utils/request";

// 登录
export function userLogin(username,password,code) {
    return request({
        url: '/auth/login',
        method: 'post',
        headers: {
            isToken: false
        },
        data: {'username':username,'password':password,'code':code}
    })
}

//退出登录
export function userLogout() {
    return request({
        url: '/auth/logout',
        method: 'post',
        headers: {
            isToken: true
        }
    })
}

//注册
export function userRegister(data) {
    return request({
        url: '/user/register',
        method: 'post',
        headers: {
            isToken :false
        },
        data: data
    })
}

//获取用户信息
export function getUserInfo() {
    return request ({
        url: '/user/userInfo',
        method: 'get',
    })
}

//保存用户信息
export function savaUserInfo(userinfo) {
    return request({
        url: '/user/userInfo',
        method: 'put',
        data: userinfo
    })
}

//用户列表
export function getAuthorList() {
    return request({
        url: '/user/authorList',
        method: 'get',
        headers: {
            isToken :false
        },
    })
}