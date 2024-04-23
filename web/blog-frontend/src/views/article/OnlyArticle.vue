<template>
  <div class="fs-2">{{ article.summary }}</div>
</template>

<script setup>
import {useRoute} from "vue-router";
import {ref} from "vue";
import {getArticle} from "@/api/article";

const route = useRoute();
const articleId = ref(route.params.articleId);
const article = ref({});

//获取文章详情
const getBlogDetail = async () => {
  article.value = await getArticle(articleId.value);
}
getBlogDetail()

</script>

<style lang="sass" scoped>

</style>