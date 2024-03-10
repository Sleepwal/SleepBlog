import request from "@/utils/request";

export function messageList() {
    return request({
        url: "/message/messageList",
        headers: {
            isToken: false
        },
        method: "get"
    })
}

export function addMessage(text, id) {
    return request({
        url: "/message/addMessage",
        method: "post",
        headers: {
            isToken: true
        },
        data: {
            'text': text,
            'userId': id
        }
    })
}