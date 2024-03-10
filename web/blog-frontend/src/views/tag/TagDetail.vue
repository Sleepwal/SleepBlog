<!-- 分类详情页面 -->
<template>
  <div class="tag-detail-body slide-in-container">
    <div class="tag-detail-title">
      <h1>
        <el-icon class="icon-align-text"><PriceTag /></el-icon>
        文章标签 - {{ tagName }}
      </h1>
    </div>
    <div class="tag-detail-container">
      <div class="tag-detail-left">
        <div v-for="(item, index) in articles">
          <ArticleItem :articleId="item.id"
                       :id="item.id"
                       :index="index"
                       :categoryName="item.categoryName"
                       :createTime="item.createTime"
                       :summary="item.summary" :thumbnail="item.thumbnail"
                       :title="item.title" :tagNames="item.tagNames"
                       :viewCount="item.viewCount"></ArticleItem>
        </div>
      </div>

      <div class="pagination">
        <el-pagination style="text-align: center"
                       v-if="pageTotal"
                       :page-size="pageSize"
                       :total="pageTotal"
                       background
                       :current-page.sync="currentPage"
                       @current-change="pageChange"
                       layout="total, prev, pager, next"/>
      </div>

      <div class="tag-detail-right">
      </div>
    </div>
  </div>
</template>

<script setup>
import ArticleItem from "@/views/home/com/ArticleItem.vue";
import {ref} from "vue";
import {articleListByTag} from "@/api/article";
import {useRoute} from "vue-router";

const route = useRoute()
//分类id
const tagId = ref(route.params.tagId)
const tagName = ref(route.params.tagName)

const articles = ref()

//分页
const currentPage = ref(1)
const pageSize = ref(10)
const pageTotal = ref(2)

//查询文章列表
const loadArticleList = async () => {
   let response = await articleListByTag({
    pageNum: 1,
    size: pageSize.value,
    tagId: tagId.value
  })
  articles.value = response.rows
  pageTotal.value = response.total
}
loadArticleList()


//换页时请求
const pageChange = async current => {
  let response = await articleListByTag({
    pageNum: current,
    size: pageSize.value,
    tagId: tagId.value
  })
  articles.value = response.rows
  pageTotal.value = response.total
  currentPage.value = current
}

</script>

<style lang="scss">

.tag-detail-body {
  margin: 0 10%;
}
  .tag-detail-title {
    background: var(--grey-3);
    margin-top: 20px;
    padding: 20px;
    width: 100%;
    border-radius: 30px;
    text-align: center;
  }

  .tag-detail-container {
    .tag-detail-left {
    }
  }
</style>









