<template>
  <div class="article-detail-body slide-in-container">
    <!--  图片  -->
    <!--    <div class="detail-image rowCC">
          <el-image :src="articleInfo.thumbnail" v-if="articleInfo.thumbnail"/>
          <el-image v-else src="https://tse1-mm.cn.bing.net/th/id/OIP-C.NwSO9j7rpzFkOor_nsUoRgHaEo?pid=ImgDet&rs=1"/>
        </div>-->

    <!--  文章信息  -->
    <div class="article-detail-info p-2 center-20 br-2">
      <div class="rowCC fs-3 fw-b">{{ articleInfo.title }}</div>

      <div class="article-detail-info-more rowCC mt-1">
        <div class="info-more-item">
          <el-icon>
            <Timer />
          </el-icon>
          发表时间: {{ articleInfo.createTime }} /
        </div>
        <div class="info-more-item">
          <el-icon>
            <Filter />
          </el-icon>
          分类:
          <router-link :to="'/category/' + articleInfo.categoryId + '/' + articleInfo.categoryName">
            {{ articleInfo.categoryName }}
          </router-link>
          /
        </div>
        <div class="info-more-item">
          <div class="rowCC">
            <el-icon>
              <PriceTag />
            </el-icon>
            标签:
            <el-tag v-for="tag in articleInfo.tagNames"
                    type="success" class="rowCC ml-5px">
              #{{ tag }}
            </el-tag>
          </div>

        </div>
      </div>

      <div class="rowCC mt-1">
        <div class="info-more-item">
          <el-icon>
            <EditPen />
          </el-icon>
          字数统计: {{ wordTotal.cn }} /
        </div>
        <div class="info-more-item">
          <el-icon>
            <Clock />
          </el-icon>
          预计阅读时长: {{ ((wordTotal.cn + wordTotal.en) / 300).toFixed(2) }} 分钟 /
        </div>
        <div class="info-more-item">
          <el-icon>
            <Reading />
          </el-icon>
          阅读量: {{ articleInfo.viewCount }} /
        </div>
        <div class="info-more-item">
          <el-icon>
            <ChatLineRound />
          </el-icon>
          评论数: {{ Math.floor(Math.random() * 10) }}
        </div>
      </div>
    </div>

    <!--  正文  -->
    <div class="detail-content">
      <MdEditorV3Preview :text="articleInfo.content"></MdEditorV3Preview>
    </div>

    <!--  出链  -->
    <div class="article-double-link br-2 p-2">
      <h2>当前文章出链</h2>
      <a v-for="item in articleInfo.outDoubleLink"
         :href="'http://127.0.0.1:5173/ArticleDetail/' + item.id">
        《{{ item.data }}》
      </a>

      <!--      <el-button v-for="item in articleInfo.outDoubleLink"
                       @click="changeArticle(item.id)"
                       >
             《{{item.data}}》
            </el-button>-->
    </div>

    <ArticleRelationGraphCom
      v-if="childReady"
      :graph-nodes="graphData.nodes"
      :graph-links="graphData.links"
    />

    <div class="article-comment">
      <Comment :article-id="articleInfo.id"
               ref="commentRef"></Comment>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { markRaw, nextTick, onMounted, onUnmounted, reactive, ref } from "vue";
import {
  articleRelationGraph,
  articleRelationGraphLinks,
  getArticle,
  singleArticleGraphById,
  updateViewCount
} from "@/api/article";
import MdEditorV3Preview from "@/components/MdEditorV3Preview.vue";
import Comment from "@/components/comment/Comment.vue";
import ArticleRelationGraphCom from "@/views/article/com/ArticleRelationGraphCom.vue";

const route = useRoute();
const articleId = ref(route.params.articleId);
const articleInfo = ref({});
const commentRef = ref();

const wordTotal = reactive({
  cn: "0",
  en: "0"
});

/**
 * 获取文章详情
 * @returns {Promise<void>}
 */
const getBlogDetail = async () => {
  articleInfo.value = await getArticle(articleId.value);
  updateViewCount(articleId.value);

  await commentRef.value.loadCommentList(articleInfo.value.id);

  graphData.value = await singleArticleGraphById(articleId.value);
  childReady.value = true;

  let words = articleInfo.value.content;

  // 正则表达式匹配中文和英文字符
  const regex = /[\u4e00-\u9fa5]/g; // 中文字符
  const regexEnglish = /[a-zA-Z]/g; // 英文字符

  // 统计匹配到的中文和英文字符数量
  const countChinese = (words.match(regex)).length;
  const countEnglish = (words.match(regexEnglish)).length;
  console.log(countChinese);
  console.log(countEnglish);

  wordTotal.en = countEnglish;
  wordTotal.cn = countChinese;
};
getBlogDetail();

const changeArticle = (id) => {
  articleId.value = id;
  getBlogDetail();

  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });
};

const graphData = ref();
const childReady = ref(false);
const style = {
  width: "50vw",
  height: "50vh"
};
const getDataAndLinks = async () => {
  graphData.value = await singleArticleGraphById(articleId.value);

  // const names = graphData.value.nodes.map(node => node.name)
  // categories.value = [...new Set(names)]  //去重

  childReady.value = true;
};
// getDataAndLinks();

</script>

<style lang="scss" scoped>
.article-detail-body {
  margin-bottom: 30px;

  .detail-image {
    text-align: center;
    //width: 100vw;
    height: 40vh;
    overflow: hidden;
    filter: blur(3px);

    /*.el-image {
      width: 100vw;
      height: auto;
    }*/
  }

  .article-detail-info {
    background-color: var(--grey-1);

    .article-detail-info-more {
      //display: flex;
      //flex-wrap: wrap;
      //padding: 0 20%;

      .info-more-item {
        margin-right: 10px;
        //width: 10vw;
      }
    }
  }

  .detail-content {
    padding-left: 5%;
    padding-right: 1%;
  }

  .article-double-link {
    margin: 3% 5% 0 5%;
    background-color: var(--grey-2);
  }

  .article-comment {
    padding: 3% 5% 0;
  }
}

.ml-5px {
  margin-left: 5px;
}


</style>