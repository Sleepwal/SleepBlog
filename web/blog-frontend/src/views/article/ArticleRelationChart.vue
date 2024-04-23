<template>
  <div>
<!--    <Suspense>-->
      <ArticleRelationGraphCom
          v-if="childReady"
          :graph-nodes="nodes"
          :graph-links="links"
          :categories="categories"
      />
<!--    </Suspense>-->

  </div>
</template>

<script setup>
import { articleRelationGraph, articleRelationGraphLinks } from "@/api/article";
import ArticleRelationGraphCom from "@/views/article/com/ArticleRelationGraphCom.vue";
import { markRaw, ref } from "vue";
import {getCategoryList} from "@/api/category";

const nodes = ref()
const links = ref()
const categories = ref()
const childReady = ref(false)
const getDataAndLinks = async () => {
  nodes.value = await articleRelationGraph()
  links.value = await articleRelationGraphLinks()
  categories.value = await getCategoryList()

  console.log(categories.value)
  // const names = nodes.value.map(node => node.name)
  // categories.value = [...new Set(names)]  //去重
  //
  childReady.value = true
}
getDataAndLinks()

</script>

<style lang="scss" scoped>

  .article-relation {
    height: 90vh;
    width: 90%;
    border: 1px solid var(--grey-9);
  }

</style>