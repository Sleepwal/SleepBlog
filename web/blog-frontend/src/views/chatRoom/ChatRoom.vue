<template>
  <div class="chat-body">
    <!--  头部  -->
    <ChatHeader />

    <!--  聊天消息  -->
    <ChatMsgPanel />
  </div>
</template>

<script setup>
import ChatHeader from "@/views/chatRoom/comp/ChatHeader.vue";
import ChatMsgPanel from "@/views/chatRoom/comp/ChatMsgPanel.vue";
</script>

<style lang="scss" scoped>
.chat-body {
  background-color: var(--grey-1);
  max-width: 79vw;
  margin: 50px auto;
  box-shadow: 0 0 15px var(--grey-9);
  border-radius: 10px;
  box-sizing: border-box;
}


</style>
