<template>
  <div class="hot-item br-2 p-1">
    <router-link :to="'/ArticleDetail/' + id">
      <div class="hot-title hover-broder-bottom">{{title}}</div>
    </router-link>
    <div class="hot-info rowBS">
      <span class="info-count">围观人数: {{viewCount}}</span>
      <span class="info-time">发布日期: {{createTime.substring(0, 10)}}</span>
    </div>
  </div>
</template>

<script setup>
  const props = defineProps({
    title: {
      type: String,
    },
    viewCount: {
      type: String,
    },
    createTime: {
      type: String,
    },
    id: {
      type: String,
    }
  })
</script>

<style lang="scss" scoped>
  .hot-item {
    border: 1px solid var(--color-blue);
    border-radius: 16px;

    .hot-title {
      font-size: 16px;
      text-align: center;
      padding-top: 10px;
      display: block;
      white-space: nowrap;
      word-break: break-all;
      overflow: hidden; // 超出的文本隐藏
      text-overflow: ellipsis; // 溢出用省略号显示

      //@include hover-broder-bottom;
    }

    .hot-info {
      padding-top: 10px;
      font-size: 15px;
    }
  }
</style>