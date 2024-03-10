<template>
  <div class="chat-head-body rowBC p-1">
    <div>
      <span class="chat-head-id">ID:999</span>
      <span>聊天室官方大厅</span>
    </div>
    <div>
      <span>在线人数: {{ Math.floor(Math.random() * 10) }}</span>
    </div>
  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.chat-head-body {
  padding-bottom: 5px;
  border-bottom: 1px solid #c7c1c1;

  .chat-head-id {
    background-color: var(--grey-9);
    color: var(--grey-1);
  }

  span {
    border-radius: 5px;
    padding: 5px;
  }
}


</style>