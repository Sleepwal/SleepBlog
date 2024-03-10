//查询文章总访问数
import request from "@/utils/request";

export function getTotalMessageCount() {
    return request({
        url: "/admin/message/totalMessageCount",
        method: "get",
        headers: {
            isToken: true
        },
    });
}

export function messageList(param) {
    return request({
        url: "/admin/message/listPage",
        headers: {
            isToken: true
        },
        method: "get",
        data: param
    })
}

export function addOrUpdateMessage(params) {
    return request({
        url: "/admin/message/addOrUpdateMessage",
        method: "post",
        headers: {
            isToken: true
        },
        data: params
    })
}

export function deleteMessage(id) {
    return request({
        url: "/admin/message/deleteMessage/" + id,
        method: "delete",
        headers: {
            isToken: true
        },
    })
}