<template>
  <div class="time-line-body slide-in-container">
    <el-timeline>
      <el-timeline-item v-for="(item,index) in timeArticles"
                        :key="index"
                        :timestamp="item.createTime"
                        placement="top"
                        center
      >
        <el-card>
          <router-link :to="'ArticleDetail/' + item.id">
            <h2>
              <ElSvgIcon name="Document"/>{{item.title}}
            </h2>
            {{item.summary}}
          </router-link>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script setup>

import { getTimeLineArticleList } from "@/api/article";
import { ref } from "vue";
import ElSvgIcon from "@/components/ElSvgIcon.vue";

const timeArticles = ref()
const loadTimeLineArticle = async () => {
  timeArticles.value = await getTimeLineArticleList()
}
loadTimeLineArticle()

</script>

<style lang="scss" scoped>
.time-line-body {
  padding: 5%;
}

</style>