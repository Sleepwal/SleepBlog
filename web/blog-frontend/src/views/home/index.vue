<template>
  <div class="home-index-body slide-in-container">
    <div class="index-body-top rowCC">
      <Type></Type>

      <Wave></Wave>

      <div class="arrow-down" @click="scrollDown()">
        <el-icon size="70" color="white">
          <ArrowDownBold />
        </el-icon>
      </div>
    </div>

    <div class="container">
      <div class="left">
        <div class="article-list">
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
                         layout="total, prev, pager, next" />
        </div>
      </div>

      <div class="right">
        <IntroductionCard />

        <el-card class="br-2">
          <div class="right-title">
            <h2>
              <el-icon>
                <Postcard />
              </el-icon>
              热门文章
            </h2>
          </div>
          <div v-for="item in hotArticles" class="mt-1">
            <HotArticleItem :create-time="item.createTime"
                            :title="item.title"
                            :view-count="item.viewCount"
                            :id="item.id">
            </HotArticleItem>
          </div>
        </el-card>

        <el-card class="br-2 mt-1">
          <div class="right-title">
            <h2>
              <el-icon>
                <PriceTag />
              </el-icon>
              标签云
            </h2>
          </div>
          <WordCloudChart :echartsData="markRaw(tagList)"
                          v-if="isLoadTag"></WordCloudChart>
        </el-card>

        <el-card class="br-2 mt-1">
          <AuthorItem />
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import ArticleItem from "@/views/home/com/ArticleItem.vue";
import { markRaw, ref } from "vue";
import { articleList, hotArticleList, topAuthorList } from "@/api/article";
import HotArticleItem from "@/views/home/com/HotArticleItem.vue";
import AuthorItem from "@/views/home/com/AuthorItem.vue";
import WordCloudChart from "@/components/WordCloudChart.vue";
import Type from "@/views/home/com/Type.vue";
import { getAuthorList } from "@/api/user";
import { getTagList } from "@/api/tag";
import Wave from "@/components/Wave.vue";
import IntroductionCard from "@/views/home/com/IntroductionCard.vue";
import { increaseAccessCount } from "@/api/system";

const articles = ref([]);
//分页
const currentPage = ref(1);
const pageSize = ref(10);
const pageTotal = ref();
//换页时请求
const pageChange = async current => {
  let response = await articleList({
    pageNum: current,
    size: pageSize.value
  });

  articles.value = response.rows;
  pageTotal.value = response.total;
  currentPage.value = current; //设置为当前页

  scrollDown();
};

/**
 * 滚动到文章列表
 */
const scrollDown = () => {
  window.scrollTo({
    behavior: "smooth",
    top: document.documentElement.clientHeight
  });
};

/**
 * 查询文章列表
 */
const getArticleList = () => {
  articleList({
    pageNum: 1,
    size: pageSize.value
  }).then((response) => {
    articles.value = response.rows;
    pageTotal.value = response.total;
  });
};
getArticleList();

//热门文章查询
const hotArticles = ref([]);
const getHotArticleList = () => {
  hotArticleList().then(response => {
    hotArticles.value = response;
  });
};
getHotArticleList(); //调用热门文章查询方法

//标签
const tagList = ref();
const isLoadTag = ref(false);
const loadTagList = async () => {
  tagList.value = await getTagList();
  isLoadTag.value = true;
};
loadTagList();

increaseAccessCount(); //用户访问量+1

//顶部轮播文章
const topArticles = ref([]);
//后端请求顶部轮播文章列表
const getTopAuthorList = () => {
  topAuthorList().then(resp => {
    topArticles.value = resp;
  });
};
// getTopAuthorList()

</script>

<style lang="scss">
.home-index-body {
  padding-bottom: 20px;

  .index-body-top {
    position: relative;
    //width: 100%;
    height: 95vh;
    background-attachment: fixed;
    //background: var(--top-bg);
    background-image: url("@/assets/background/bg1.png");
    background-size: cover;
    background-repeat: no-repeat;
  }

  .arrow-down {
    position: absolute;
    bottom: 130px;
    -webkit-animation: arrow-shake 1.5s ease-out infinite;
    animation: arrow-shake 1.5s ease-out infinite;
    cursor: pointer;
    z-index: 8;
  }
  
}

.container {
  display: flex;
  animation: slideUpIn 1s;
  background-attachment: fixed;
  padding: 0 5%;

  .left {
    flex: 1;

    &:hover {
      ~ .right {
        filter: blur(5px);
      }
    }
  }

  .right {
    margin-top: 20px;;
    margin-left: 10px;
    width: 20vw;

    /*.right-card {
      background: white ;
      border-radius: 20px;
      padding-bottom: 1px;
      box-shadow: 5px 5px 10px black,-5px -5px 10px white;
    }*/
    .right-title {
      font-size: 17px;
      font-weight: bold;

      @include custom-border-bottom(20%);
    }
  }
}

@keyframes arrow-shake {
  0% {
    opacity: 1;
    transform: translateY(0);
  }

  30% {
    opacity: 0.5;
    transform: translateY(25px);
  }

  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

</style>









