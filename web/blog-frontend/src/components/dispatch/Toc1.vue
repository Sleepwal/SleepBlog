<template>
  <div>
    <el-affix offset="70">
      <div class="toc-card-header-toc br-2 p-1">
        <div>
          <span v-if="!isShowToc"><el-icon><ArrowUpBold /></el-icon></span>
          <span v-if="isShowToc"><el-icon><ArrowDownBold /></el-icon></span>
          <span>目录</span>
        </div>

        <el-collapse-transition>
          <div  class="toc-list p-1">
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
</template>

<script setup>

import { reactive, ref } from "vue";

const titles = reactive({});
const currentTitle = reactive({});
const progress = ref(0);
const isShowToc = ref(true)

const tocArray = ref([])
const anchorId = ref(null)

const createToc = () => {
  const tocTags = ["H1", "H2", "H3", "H4", "H5", "H6"]
  let contentDom = document.querySelector("div.base-contain-content article.md-editor-preview") //获取正文
  const childNodes = contentDom.childNodes //子节点

  tocArray.value = [] //清空上一次的目录
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

const updateToc = () => {
  setTimeout(createToc, 500)
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

let timer

onMounted(() => {
  window.addEventListener("scroll", listenScroll, false)

  // timer = setInterval(() => {
  //   createToc()
  // }, 1000)
})

onUnmounted(() => {
  window.removeEventListener("scroll", listenScroll, false)

  // 清理定时器要处理 timer 的类型
  // clearInterval(Number(timer))
})

</script>

<style lang="scss" scoped>
/*.base-contain-toc {
    background: rgba(255, 255, 255, 0.90);
    box-shadow: 0 3px 8px 6px rgba(7, 17, 27, 0.05);
    padding: 20px 24px;
    margin-top: 30px;
    box-sizing: border-box;
    width: 20vw;

    .toc-card-header {
      text-align: left !important;
      margin-bottom: 15px;
      .header-ic {
        &:hover {
          cursor: pointer;
        }
      }
      .progress {
        color: #a9a9a9;
        font-style: italic;
        font-size: 140%;
      }
    }

    .toc-content {
      max-height: calc(100vh - 120px);
      overflow: auto;
      margin-right: -24px;
      padding-right: 20px;

      .toc-item {
        color: #666261;
        margin: 5px 0;
        line-height: 28px;
        cursor: pointer;
        transition: all 0.2s ease-in-out;
        font-size: 14px;
        padding: 2px 6px;
        display: -webkit-box;
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;

        &:hover {
          color: #1892ff;
        }
      }
    }
    .active {
      background-color: rgba(122, 187, 211, 0.62);

      &:hover {
        background-color: rgba(122, 187, 211, 0.62);
      }
    }
  }



.toc-card-header-toc {
  //background: whitesmoke;
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
      //color: #262323;
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
}*/
</style>