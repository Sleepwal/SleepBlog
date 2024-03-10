<template>
  <div>
    <div class="base-contain slide-in-container rowCS">
      <el-affix offset="120">
        <!--     切换文档     -->
        <div class="base-contain-change br-2">
          <!--     按钮     -->
          <div class="contain-change-btn rowCC p-1">
            <el-button round type="warning" @click="showAllCate">
              切换文档类别
            </el-button>
          </div>

          <!--     当前类别     -->
          <!--          {{knowDetail !== undefined ? knowDetail.title : 1}}-->
          <div class="change-cur-cate p-1 br-2" v-if="isShowOnLoad">
            <h2>
              <el-icon>
                <Collection />
              </el-icon>
              {{ knowDetail.title }}
            </h2>
            <div class="cur-cate-desc">
              {{ knowDetail.description }}
            </div>
            <!--      切换类型,显示所有文档类型      -->
            <div class="change-cate-list">
              <el-collapse-transition>
                <div v-show="isShowAllCate">
                  <div v-for="cate in knowCateList"
                       class="change-cate-list-item br-1"
                       :key="cate.id" @click="changeCate(cate)">
                    <el-icon>
                      <Collection />
                    </el-icon>
                    {{ cate.title }}
                  </div>
                </div>
              </el-collapse-transition>
            </div>
          </div>

          <!--     文档的文章导航     -->
          <!--          {{knowDetail.title}}-->
          <el-scrollbar height="65vh">
            <template v-if="isShowOnLoad">
              <Document v-for="doc in knowDetail.children"
                        :doc="doc"></Document>
            </template>
          </el-scrollbar>
        </div>
      </el-affix>

      <div class="base-contain-article">
        <h1 class="base-contain-title fs-3 rowCC br-2 p-1">{{ article.title }}</h1>
        <div class="base-contain-content br-2">
          <MdEditorV3Preview :text="article.content" :is-show-toc="false"></MdEditorV3Preview>
        </div>
      </div>
      <div>

        <!--    目录    -->
        <div class="toc">
          <el-affix offset="70">
            <Navigation ref="navigationRef"
                        my-selector="div.base-contain-content article.md-editor-preview" />
          </el-affix>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { nextTick, onMounted, onUnmounted, provide, reactive, ref } from "vue";
import { ArrowDownBold, ArrowUpBold, Collection } from "@element-plus/icons-vue";
import { getAllKnowCate, getKnowledgeDetail } from "@/api/knowledge";
import { getKnowArticleDetail } from "@/api/knowArticle";
import Document from "@/views/KnowledgeBase/com/TreeDocument.vue";
import MdEditorV3Preview from "@/components/MdEditorV3Preview.vue";
import { useKnowStore } from "@/stores/knowledge";
import { ElMessage, ElNotification } from "element-plus";
import Navigation from "@/components/Navigation.vue";

const knowStore = useKnowStore();

//目录
const navigationRef = ref();

//文章
const article = ref({});

//当前类型、文章、文档
const currentCate = reactive({
  id: knowStore.knowledgeInfo.selectCateIndex
});
const currentArt = reactive({
  id: knowStore.knowledgeInfo.selectIndex
});
provide("currentArt", currentArt);
const currentDoc = reactive({
  id: knowStore.knowledgeInfo.selectDocumentIndex
});
provide("currentDoc", currentDoc);

const knowDetail = ref();
const knowCateList = ref();
const isShowAllCate = ref(false);
const isShowOnLoad = ref(false);

const showAllCate = () => {
  isShowAllCate.value = !isShowAllCate.value;
};

/**
 * 加载知识库类型、文档、文章
 * @returns {Promise<void>}
 */
const loadBase = async () => {
  knowDetail.value = await getKnowledgeDetail(currentCate.id);
  article.value = await getKnowArticleDetail(currentArt.id);
  knowCateList.value = await getAllKnowCate();

  await nextTick(() => {
    isShowOnLoad.value = true;
  });
  navigationRef.value.updateTitles(); //更新目录

  ElNotification.success("已加载上次观看文章!");
};
loadBase();

/**
 * 切换文章
 * @param data
 * @returns {Promise<void>}
 */
const changeArt = async (data) => {
  article.value = "";
  currentArt.id = data.id;
  knowStore.knowledgeInfo.selectIndex = currentArt.id;

  scrollToView(0); //回到顶部
  article.value = await getKnowArticleDetail(data.id); //加载文章
  navigationRef.value.updateTitles(); //更新目录
};


const changeCate = async (data) => {
  knowDetail.value = await getKnowledgeDetail(data.id);
  currentCate.id = data.id;
  knowStore.knowledgeInfo.selectCateIndex = data.id;

  //加载该类型下的 第一个文档下的 第一篇文章
  let art = knowDetail.value.children[0].children[0];
  console.log(art);
  await changeArt(art);

  //加载该类型下的 第一个文档
  currentDoc.id = knowDetail.value.children[0].id;
  knowStore.knowledgeInfo.selectDocumentIndex = currentDoc.id;
};

provide("changeArt", changeArt);


const scrollToView = (scrollTop) => {
  window.scrollTo({ top: scrollTop, behavior: "smooth" });
};

</script>

<style lang="scss" scoped>
.base-contain {

  .base-contain-change {
    width: 20vw;
    background-color: var(--grey-2);

    .contain-change-btn {
    }

    .change-cur-cate {
      background-color: var(--grey-3);

      .cur-cate-desc {
        font-style: italic;
      }

      .change-cate-list {
        .change-cate-list-item {
          padding: 5px 10px;
          margin-bottom: 5px;
          cursor: pointer;
          background-color: var(--grey-1-a7);
        }

        margin-top: 5px;
        //padding-left: 10px;
      }
    }

  }

  .base-contain-article {
    flex: 1;
    margin-left: 10px;
    animation: titleScale 1s;

    .contain-article-img {
      width: 700px;
      height: 400px;
      overflow: hidden;
    }

    .base-contain-title {
      background-color: var(--grey-1);
      box-shadow: 0 8px 8px 6px var(--box-bg-shadow);
    }

    .base-contain-content {
      box-shadow: 0 8px 8px 6px var(--box-bg-shadow);
      overflow: hidden;
    }
  }

  .toc {
    //z-index: 10;
    width: 20vw;
    margin-left: 10px;
  }

}

</style>