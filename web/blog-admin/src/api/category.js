import request from '@/utils/request'

// 查询所有分类列表
export function getAllCategoryList() {
    return request({
        url: '/admin/allCategoryList',
        headers: {
            isToken: true
        },
        method: 'get'
    })
}

// 查询有文章的分类
export function getCategoryList() {
    return request({
        url: '/admin/categoryList',
        headers: {
            isToken: true
        },
        method: 'get'
    })
}

//添加分类
export function addOrUpdateCategory(data) {
    return request({
        url: '/admin/addOrUpdateCategory',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

//删除分类
export function deleteCategory(id) {
    return request({
        url: '/admin/deleteCategory/' + id,
        headers: {
            isToken: true
        },
        method: 'delete',
    })
}

//分类总数
export function getCategoryTotalCount() {
    return request({
        url: '/admin/categoryTotalCount',
        headers: {
            isToken: true
        },
        method: 'get',
    })
}