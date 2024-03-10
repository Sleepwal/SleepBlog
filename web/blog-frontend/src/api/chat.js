import request from "@/utils/request";

export function getChatList() {
  return request({
    url: "/chat/getChatList",
    headers: {
      isToken: true
    },
    method: "get"
  });
}

export function addChatMessage(data) {
  return request({
    url: "/chat/addChatMessage",
    headers: {
      isToken: true
    },
    method: "POST",
    data: data
  });
}
