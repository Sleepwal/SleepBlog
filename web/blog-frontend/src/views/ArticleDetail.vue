<template>
  <div>
    <div class="container article-detail rowBS">
      <div class="c-left" >
        <div class="detail-image">
          <el-image :src="articleInfo.thumbnail" v-if="articleInfo.thumbnail"/>
          <el-image v-else src="https://tse1-mm.cn.bing.net/th/id/OIP-C.NwSO9j7rpzFkOor_nsUoRgHaEo?pid=ImgDet&rs=1"/>
        </div>
        <div class="content">
          <div class="title"> {{articleInfo.title}} </div>
          <v-md-editor :model-value="articleInfo.content" mode="preview" ref="preview"></v-md-editor>
<!--          <MdEditorV3Preview :text="articleInfo.content"></MdEditorV3Preview>-->
        </div>
        <div class="right-bottom">
        </div>
      </div>

      <div class="toc">
          <el-affix offset="100">
            <div class="toc-card-header-toc br-2 p-1">
              <div>
                <span v-if="!showToc"><el-icon><ArrowUpBold /></el-icon></span>
                <span v-if="showToc"><el-icon><ArrowDownBold /></el-icon></span>
                <span>目录</span>
              </div>

              <el-collapse-transition>
                <div v-show="showToc" class="toc-list p-1">
                  <el-scrollbar height="500">
                  <div v-for="item in tocArray">
                    <div>
                      <div :class="['toc-item', item.id === anchorId ? 'active' : '']"
                           :style="{'padding-left': (item.level - 1) * 15 + 'px'}">
                        <a :href="'#' + item.id" @click.prevent="scrollTo(item.id)">{{ item.title }}</a>
                      </div>
                    </div>
                  </div>
                  </el-scrollbar>
                </div>
              </el-collapse-transition>
            </div>
          </el-affix>
      </div>

    </div>
  </div>
</template>

<script setup>
import {useRoute} from "vue-router"
import {nextTick, onMounted, onUnmounted, ref} from "vue";
import {getArticle} from "@/api/article";

const route = useRoute()
const articleId = ref(route.params.articleId)
const articleInfo = ref({})

//获取文章详情
const getBlogDetail = () => {
  getArticle(articleId.value).then(response => {
    articleInfo.value = response

    nextTick(() => {
      //获取目录
      createToc()
    })
  })
}
//调用
getBlogDetail()

//目录
const showToc = ref(true)
const tocArray = ref([])
const anchorId = ref(null)

const createToc = () => {
  const tocTags = ["H1", "H2", "H3", "H4", "H5", "H6"]
  let contentDom = document.querySelector(".vuepress-markdown-body") //获取正文
  const  childNodes = contentDom.childNodes //子节点

  let index = 0;
  childNodes.forEach(item => {
    let tagName = item.tagName //标签名
    // console.log(tagName) //测试标签名获取

    if(tagName === undefined || !tocTags.includes(tagName.toUpperCase())) { //空或不包含在H1-H6
      return true;
    }

    index++ //id不能重复，+1
    let id = "toc" + index
    item.setAttribute("id", id) //节点设置id
    tocArray.value.push({
      id: id,
      title: item.innerText,
      level: Number.parseInt(tagName.substring(1)),
      offsetTop: item.offsetTop
    })
  })
}

const scrollTo = (id) => {
  //根据设置的id跳转
  const element = document.getElementById(id)
  if (element)
    element.scrollIntoView({ behavior: 'smooth' })
}

//滚动条目录更新
const listenScroll = () => {
  let currentScrollTop = getScrollTop()
  tocArray.value.some((item, index) => {
    if(index < tocArray.value.length - 1 &&
        currentScrollTop >= tocArray.value[index].offsetTop &&
        currentScrollTop < tocArray.value[index + 1].offsetTop
        || index === tocArray.value.length - 1 &&
        currentScrollTop < tocArray.value[index].offsetTop) {
      anchorId.value = item.id
      return true
    }
  })
}

const getScrollTop = () => {
  return document.documentElement.scrollTop ||
      window.pageYOffset ||
      document.body.scrollTop
}

onMounted(() => {
  window.addEventListener("scroll", listenScroll, false)
})

onUnmounted(() => {
  window.removeEventListener("scroll", listenScroll, false)
})

</script>

<style lang="scss" scoped>
.article-detail {
  .toc {
    z-index: 10;
    width: calc(18%);
    margin-left: 10px;
    .toc-card-header-toc {
      background: whitesmoke;
      box-shadow: 3px 5px 5px 5px rgba(4, 21, 35, 0.49);
      box-sizing: border-box;
      width: 20vw;
    }
    .toc-list {
      .toc-item {
        font-size: 16px;
        line-height: 25px;
        cursor: pointer;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        border-radius: 2px;
        a {
          color: #262323;
          text-decoration: none;
        }
      }
      .active {
        color: #b68282;
        background-color: #dddddd;
        border-left: 5px solid #c4782c;
      }
      .toc-item a:hover {
        color: #b68282;
      }
    }
  }
  .c-left {
    flex: 1;
    .detail-image {
      text-align: center;
      .el-image {
        width: 500px;
        height: auto;
        border-radius: 50px;
      }
    }

    .content {
      background-color: rgba(218, 210, 210, 0.85);
      border-radius: 30px;
      .title {
        font-weight: bold;
        font-size: 34px;
        text-align: center;
      }
      .img {
        width: 100%;
      }
    }
  }
}
</style>