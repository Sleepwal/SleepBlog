<template>
  <div class="right-title">
    <h2>
      <el-icon>
        <UserFilled />
      </el-icon>
      贡献成员
    </h2>
  </div>
  <div v-for="item in authorList" class="author-item rowBC">

    <div class="author-name">
      <el-avatar :src="item.avatar" />
      <div>{{ item.nickname }}</div>
    </div>

    <div class="author-info">
      <div class="info-career">性别: {{ item.sex === "1" ? "男" : "女" }}</div>
      <div class="info-article-count">发布文章: {{ item.count }}</div>
    </div>
  </div>
</template>

<script setup>
import { getAuthorList } from "@/api/user";
import { ref } from "vue";

const authorList = ref([]);
//获取用户（作者）列表
const loadUserList = async () => {
  authorList.value = await getAuthorList();
};
loadUserList();

</script>

<style lang="scss" scoped>
.author-item {
  .author-name {
    font-size: 16px;
    padding-top: 10px;
    display: block;
    white-space: nowrap;
    word-break: break-all;
    overflow: hidden; // 超出的文本隐藏
    text-overflow: ellipsis; // 溢出用省略号显示
  }

  .author-info {
    padding-top: 10px;
    font-size: 15px;

    .info-career {
    }

    .info-article-count {
    }
  }
}

</style>