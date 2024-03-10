import request from "@/utils/request";

// 知识库
export function getKnowledgeDetail(cateId) {
    return request({
        url: "/knowledge/detail/" + cateId,
        method: "get",
        headers: {
            isToken: false
        },
    });
}

//查询所有分类
export function getAllKnowCate() {
    return request({
        url: "/knowledge/cateList",
        method: "get",
        headers: {
            isToken: false
        },
    });
}