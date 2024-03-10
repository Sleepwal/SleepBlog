<template>
  <div>
    <el-card>
      <template #header>
        <h2>数据概览</h2>
      </template>

      <el-row gutter="10">
        <el-col
          span="4" class="flex-1"
          v-for="(item, index) in dataList.name">
<!--          :style="{background: color[index]}>"-->
          <div class="data-item br-2 p-5 rowBC">
            <ElSvgIcon
              :name="dataList.icon[index]"
              size="60"
              :color="dataList.color[index]"/>

            <div>
              <div class="data-title">
                {{item}}
              </div>

              <div class="data-count">
                <count-up :end-val="dataList.data[index]"></count-up>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import CountUp from 'vue-countup-v3'
import {getArticleCount, getArticleTotalViewCount } from "@/api/article";
import {getCategoryTotalCount} from "@/api/category";
import {getUserTotalCount} from "@/api/system/user";
import {getTotalKnowArticleViewCount} from "@/api/know";
import {getTotalMessageCount} from "@/api/message";
import { getCommentCount } from "@/api/comment";

const dataList = ref({
  name: ['文章总数', '总浏览量', '用户数量', '评论数', '留言数'],
  data: [],
  icon: ['EditPen', 'TrendCharts', 'UserFilled', 'Message', 'ChatLineSquare'],
  color: ['#85d333', '#ced543', '#a3a3e1', '#527cc2', '#eea5a5'],
})

const loadDataList = async () => {
  dataList.value.data[0] = await getArticleCount()
  dataList.value.data[1] = await getArticleTotalViewCount()
  // dataList.value.data[2] = await getCategoryTotalCount()
  dataList.value.data[3] = await getUserTotalCount()
  dataList.value.data[4] = await getCommentCount()
  dataList.value.data[5] = await getTotalMessageCount()
}
loadDataList()

</script>

<style lang="scss" scoped>
.el-card {
  margin: 10px;
  border-radius: 20px;
  //width: 12vw;
}

.data-item {
  background: linear-gradient(to right, #fdfbfb, #FFEFBA); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */


  .data-title {

  }
  .data-count {
    font-size: 1.5em;
    font-weight: bold;
  }
}

</style>