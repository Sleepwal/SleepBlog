import request from "@/utils/request";

export function uploadImg(img) {
    const formData = new FormData()
    formData.append('img', img)
    return request({
        url: '/github/upload',
        headers: {
            'Content-Type': 'multipart/form-data',
            isToken: true
        },
        method: 'post',
        data: formData
    })
}