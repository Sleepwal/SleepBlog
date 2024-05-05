<template>
<!--  <span v-html="marked(article.content)"></span>-->
  <MdPreview :modelValue="article.content" />
</template>

<script setup>
import {useRoute} from "vue-router";
import {ref} from "vue";
import {getOnlyArticle} from "@/api/article";
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/preview.css"; // preview.css相比style.css少了编辑器那部分样式

const route = useRoute();
const articleId = ref(route.params.articleId);
const article = ref({});

//获取文章详情
const getBlogDetail = async () => {
  article.value = await getOnlyArticle(articleId.value);
}
getBlogDetail()

</script>