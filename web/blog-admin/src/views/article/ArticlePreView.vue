<template>
  <div>
    <div class="rowBS">
      <router-link to="/article/manage">
        <el-icon><Back /></el-icon> 返回
      </router-link>
      <div class="pre-title">
        {{article.title}}</div>
    </div>

    <el-scrollbar :height="editorHeight">
      <v-md-preview :text="article.content"></v-md-preview>
    </el-scrollbar>

  </div>
</template>

<script setup>
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

// highlightjs
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
  Hljs: hljs,
});

import {editArticleDetail} from "@/api/article";
import {useRoute} from "vue-router"
import {knowArticleDetail} from "@/api/know";
const route = useRoute()
const editorHeight = window.innerHeight - 150

const articleId = ref(route.params.articleId)
const type = ref(route.params.type)

const article = ref({})

const loadArticleDetail = async () => {
  if(type.value === '0')
    article.value = await editArticleDetail(articleId.value)
  else
    article.value = await knowArticleDetail(articleId.value)
}
loadArticleDetail()
</script>

<style lang="scss">
.pre-title {
  font-size: 18px;
  font-weight: bold;
}
</style>