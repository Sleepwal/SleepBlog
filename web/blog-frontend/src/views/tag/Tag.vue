<template>
  <div class="tag-contain slide-in-container">

    <div class="tag-contain-text br-2">
      <h2><el-icon><PriceTag /></el-icon>文章标签</h2>
      <div class="tag-list">
        <WordCloudChart :echartsData="markRaw(tagList)"
                        v-if="isLoadTag"></WordCloudChart>
      </div>
    </div>

    <div class="tag-contain-pie br-2">
      <h2><el-icon><PieChart /></el-icon>标签饼图</h2>
      <div class="rowCC pie">
        <TagChart></TagChart>
      </div>
    </div>
    
  </div>
</template>

<script setup>
import {markRaw, ref} from "vue";
import {getTagList} from "@/api/tag";
import TagChart from "@/views/tag/com/TagChart.vue";
import WordCloudChart from "@/components/WordCloudChart.vue";

const isLoadTag = ref(false)
const tagList = ref()
const loadTagList = async () => {
  tagList.value = await getTagList()
  isLoadTag.value = true
}
loadTagList()

</script>

<style lang="scss">
.tag-contain {
  height: 100%;
  padding: 30px 0;
  border-radius: 30px;
  text-align: center;

  .tag-contain-text {
    background: var(--grey-1-a5);
    margin: 0 15%;

    .tag-list {
      //display: flex;
      //flex-wrap: wrap;
      margin: 5px 0;
      padding-top: 30px;
      padding-bottom: 50px;
    }
  }

  .tag-contain-pie {
    background: var(--grey-1);
    margin: 0 15%;
  }
}


</style>