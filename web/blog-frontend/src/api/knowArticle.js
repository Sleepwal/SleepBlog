import request from "@/utils/request";

//获取文章详情
export function getKnowArticleDetail(articleId) {
    return request({
        url: "/knowledge/articleDetail/" + articleId,
        headers: {
            isToken: false
        },
        method: "get"
    });
}
