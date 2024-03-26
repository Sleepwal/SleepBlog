<template>
  <div class="search-item-body">
    <span @click="showSearch" class="search-button">
      搜索<el-icon><Search /></el-icon>
    </span>

    <el-dialog v-model="dialogConfig.dialogVisible"
               :title="dialogConfig.title"
               class="br-2">
      <el-input v-model.trim="searchText"
                @keyup="searchSub"
                clearable
                prefix-icon="Search"
                placeholder="搜索标题或内容, 按下enter搜索">
      </el-input>

      <div class="search-result mt-1">
        <h3 class="result-count" v-if="result.count">
          共 {{result.count}} 条记录
        </h3>

        <div v-for="article in result.articles"
             class="search-result-item br-1 p-1 white-dark-shadow-border">

          <router-link :to="'/ArticleDetail/' + article.id">
            <h2 class="rowSC"><el-icon><Document /></el-icon>
              <span v-html="article.title"></span>
            </h2>

            <div v-for="text in article.content">
              <span v-html="text"></span>
            </div>
          </router-link>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>

import { reactive, ref } from "vue";
import { articleSearch } from "@/api/article";

const dialogConfig = reactive({
  dialogVisible: false,
  title: "搜索",
})
const showSearch = () => {
  dialogConfig.dialogVisible = true
}

const searchText = ref()
const result = reactive({
  articles: [],
  count: '',
})

const searchSub = async () => {
  if(!searchText.value) return

  let res = await articleSearch(searchText.value)

  res.map(r => {
    let re = new RegExp(`.{0,50}\\b${searchText.value}\\b.{0,70}`, 'g'); //前50和后70都要
    let reColor = new RegExp(`\\b${searchText.value}\\b`, 'g'); //高亮匹配
    let contentHighLight = `<span style="background: yellow;">${searchText.value}</span>` //正文高亮

    //去除换行、高亮替换、匹配
    let match = r.content.replace(/\n/g,"")
      .replace(reColor, contentHighLight)
      .match(re);
    r.content = match ? match : "";

    let titleHighLight = `<span style="background: orange;">${searchText.value}</span>` //标题高亮
    r.title = r.title.replace(reColor, titleHighLight) //高亮
  })

  result.articles = res
  result.count = result.articles.length
}

</script>

<style lang="sass" scoped>
@import "@/styles/mixin.scss"

.search-item-body
  .search-button
    @include my-btn-border
  .search-result
    .result-count
      color: var(--color-red)
    .search-result-item
      margin-top: 20px
      a
        color: var(--grey-7)
</style>