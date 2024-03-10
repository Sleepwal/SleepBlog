<template>
  <div>
    <!--    <div class="only-article-title rowCC"> {{articleInfo.title}} </div>-->
    <!--  正文  -->
    <!--    <div>{{ articleInfo.content }}</div>-->
    <MdPreview :editorId="articleInfo.id" :theme="theme"
               :modelValue="articleInfo.content"
               class="only-article-preview"
               :style="{ 'width': 100 + 'vw' }"
    />
    <!--    <MdEditorV3Preview :text="articleInfo.content"></MdEditorV3Preview>-->
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { computed, ref } from "vue";
import { getArticle } from "@/api/article";
import MdEditorV3Preview from "@/components/MdEditorV3Preview.vue";
import { MdPreview, MdCatalog } from "md-editor-v3";
// preview.css相比style.css少了编辑器那部分样式
import "md-editor-v3/lib/preview.css";
import { useSystemStore } from "@/stores/system";

const route = useRoute();
const theme = computed(() => useSystemStore().getTheme());

const articleId = ref(route.params.articleId);
const articleInfo = ref({});
const content = ref();

//获取文章详情
const getBlogDetail = async () => {
  articleInfo.value = await getArticle(articleId.value);
};
getBlogDetail();

</script>

<style lang="scss" scoped>
.only-article-title {
  font-weight: bold;
  font-size: 34px;
  text-align: center;
}

.only-article-preview {
  //background-color: var(--grey-1);
}

</style>