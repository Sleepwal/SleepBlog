<template>
  <div class="my-md-preview-body rowBS">
    <!--  正文  -->
    <MdPreview :editorId="id" :modelValue="text" :theme="useSystemStore().getTheme()"
               class="my-md-preview" :style="{ 'width': myWidth + 'vw' }" />
    <!--  目录  -->
    <div class="width-transition" :style="tocStyle" v-if="isShowToc">
      <el-affix offset="100" target=".my-md-preview-body">
        <div class="my-md-cate-log p-1">
          <h2><el-icon><Expand /></el-icon>目录</h2>

          <MdCatalog :editorId="id" :scrollElement="scrollElement"/>
        </div>
      </el-affix>
    </div>

    <!--    <el-button class="show-toc-button"
                   @click="changeIsShowToc()">
        </el-button>-->
  </div>
</template>

<script setup>
import { MdCatalog, MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/preview.css"; // preview.css相比style.css少了编辑器那部分样式
import { useSystemStore } from "@/stores/system";
import { computed, reactive, ref } from "vue";

const props = defineProps({
  text: {
    type: String,
    default: "# Hello Editor"
  },
  myWidth: {
    type: String,
    default: "40"
  },
  isShowToc: {
    type: Boolean,
    default: true
  }
});

const id = "preview-only";
const scrollElement = document.documentElement;

const contentWidth = ref("40");
const tocStyle = reactive({
  width: "20vw",
  height: "100%"
});
const changeIsShowToc = () => {
  // isShowToc.value = !isShowToc.value
  // tocStyle.width =  tocStyle.width === '20vw' ? '0vw' : '20vw'
  props.myWidth = "80";
};

//双链
/*const parseDoubleLink = (markdown) => {
    if(markdown === undefined || markdown === null) return

    let reg = /\[\[(.*?)\]\]/g
    let regArray = reg.exec(markdown);
    if(regArray === undefined || regArray === null) return

    const replaceStr = '<p><a class=\'tooltip\' href="http://localhost:5173/ArticleDetail/9">' +
        '<span>$1</span>' +
        '<span class=\'tooltiptext\'><iframe src="http://localhost:5173/OnlyArticle/9" width="1000" height="450" style="background-color: white"></iframe></span>' +
        '</a></p>'
    markdown = markdown.replace(reg, replaceStr)

    return markdown
}*/

</script>

<style lang="scss" scoped>

.my-md-preview-body {

  .my-md-preview {
    flex: 1;
    //width: 70vw;
    border-radius: 30px;
    box-shadow: 3px 3px 10px var(--grey-2), -3px -3px 10px var(--grey-7);
    transition: all .5s linear;
  }

  .width-transition {
    transition: width .5s linear;
  }

  .my-md-cate-log {
    //width: 20vw;
    max-height: 60vh;
    overflow: auto;
    margin-left: 10px;
    background-color: var(--grey-1-a7);
    box-shadow: 3px 3px 5px var(--grey-7), -3px -5px 5px var(--grey-2);
    box-sizing: border-box;
    border-radius: 10px;
    //transition: width .5s linear;
  }

  .show-toc-button {
    position: fixed;
    top: 15%;
    right: 1%;
    //width: 40px;
    padding: 10px;
  }

}


</style>