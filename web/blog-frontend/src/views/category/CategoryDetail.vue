<!-- 分类详情页面 -->
<template>
  <div class="category-detail-body slide-in-container">
    <div class="category-detail-title">
      <h1>
        <el-icon class="icon-align-text"><Filter /></el-icon>
        文章分类 - {{ categoryName }}
      </h1>
    </div>
    <div class="category-detail-container">
      <div class="category-detail-left">
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

      <div class="category-detail-right">
      </div>
    </div>
  </div>
</template>

<script setup>
import ArticleItem from "@/views/home/com/ArticleItem.vue";
import {ref} from "vue";
import {articleList} from "@/api/article";
import {useRoute} from "vue-router";

const route = useRoute()
//分类id
const categoryId = ref(route.params.categoryId)
const categoryName = ref(route.params.categoryName)

const articles = ref([]);

//分页
const currentPage = ref(1)
const pageSize = ref(10)
const pageTotal = ref()

//查询文章列表
const getArticleList = () => {
  articleList({
    pageNum: 1,
    size: pageSize.value,
    categoryId: categoryId.value
  }).then((response) => {
    articles.value = response.rows
    pageTotal.value = response.total
  })
}
//调用查询文章列表方法
getArticleList()

//换页时请求
const pageChange = async current => {
  let response = await articleList({
    pageNum: current,
    size: pageSize.value,
    categoryId: categoryId.value
  })

  articles.value = response.rows
  pageTotal.value = response.total
  currentPage.value = current
}

</script>

<style lang="scss">

.category-detail-body {
  margin: 0 10%;
}
  .category-detail-title {
    background: var(--grey-3);
    margin-top: 20px;
    padding: 10px;
    width: 100%;
    border-radius: 30px;
    text-align: center;
    //color: wheat;
  }

  .category-detail-container {
    .category-detail-left {
    }
  }
</style>









