<template>
  <div class="category-contain slide-in-container">
    <div class="category-contain-text br-2">
      <h2><el-icon><Filter /></el-icon>文章分类</h2>
      <div class="category-list rowCC">
        <CategoryItem v-for="item in categoryList" :data="item"></CategoryItem>
      </div>
    </div>

    <div class="category-contain-pie br-2">
       <h2><el-icon><PieChart /></el-icon>分类饼图</h2>
      <div class="rowCC pie">
        <CategoryChart></CategoryChart>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import CategoryItem from "@/views/category/com/CategoryItem.vue";
import {getCategoryList} from "@/api/category";
//饼图
import CategoryChart from "@/views/category/com/CategoryChart.vue";
import {getTagList} from "@/api/tag";

const categoryList = ref([])

const loadCategoryList = async () => {
  categoryList.value = await getCategoryList()
}
loadCategoryList()

//标签
const tagList = ref()
const loadTagList = async () => {
  tagList.value = await getTagList()
}
loadTagList()

</script>

<style lang="scss">
.category-contain {
  height: 100%;
  padding: 30px 0;
  border-radius: 30px;
  text-align: center;

  .category-contain-text {
    background: var(--grey-2);
    margin: 0 15%;

    .category-list {
      display: flex;
      flex-wrap: wrap;
      margin: 10px 0;
      padding-top: 30px;
      padding-bottom: 50px;

    }
  }

  .category-contain-pie {
    //background: var(--grey-1-a5);
    margin: 0 15%;
  }
}

</style>