<template>
    <div class="article-item">
      <!--  图片  -->
      <div class="thumbnail" v-if="index % 2 === 0">
        <el-image :src="thumbnail" v-if="thumbnail"/>
        <el-image v-else :src="imgList[id % imgList.length]"/>
      </div>

      <!--  文章列表内容  -->
      <div class="article-item-content">
        <span class="time">
            <el-icon class="icon-align-text"><Clock /></el-icon>
            {{createTime}}
        </span>

        <div class="title">
          <router-link :to="'/ArticleDetail/' + articleId" class="a-link">
            {{title}}
          </router-link>
        </div>
        <div class="summary">{{summary}}</div>
        <div class="info">
          <span class="categoryName">
            <el-icon class="icon-align-text"><Operation /></el-icon>
            类别:
            <router-link to="/" class="a-link">
              {{categoryName}}
            </router-link></span> |

          <span class="viewCount">
            <el-icon class="icon-align-text"><View /></el-icon>
            浏览量: {{viewCount}}
          </span>
        </div>
        <div class="tag-name-list rowSC">
            <el-tag v-for="name in tagNames"
                class="tag-name-item rowCC" type="success" >
              #{{name}}</el-tag>
        </div>

        <!--    点击查看更多    -->
        <div class="more">
          <router-link :to="'/ArticleDetail/' + articleId" class="a-link">
            More
            <ElSvgIcon name="MoreFilled" size="30"></ElSvgIcon>
          </router-link>
        </div>

      </div>

      <!--  图片  -->
      <div class="thumbnail" v-if="index % 2 === 1">
        <el-image :src="thumbnail" v-if="thumbnail"/>
        <el-image v-else :src="imgList[id % imgList.length]"/>
      </div>

    </div>
</template>

<script setup>
import {Clock, Operation, View} from "@element-plus/icons-vue";
import {onBeforeUnmount, onMounted, ref} from "vue";
import cover1 from "@/assets/articleCover/cover1.png"
import cover2 from "@/assets/articleCover/c2.png"
import cover3 from "@/assets/articleCover/c3.png"
import cover4 from "@/assets/articleCover/c4.png"
import cover5 from "@/assets/articleCover/c5.png"
import cover6 from "@/assets/articleCover/c6.png"
import cover7 from "@/assets/articleCover/c7.png"
import cover8 from "@/assets/articleCover/c8.png"
import cover9 from "@/assets/articleCover/c9.png"
import cover10 from "@/assets/articleCover/c10.png"
import cover11 from "@/assets/articleCover/c11.png"
import cover12 from "@/assets/articleCover/c12.png"
import cover13 from "@/assets/articleCover/c13.png"
import cover14 from "@/assets/articleCover/c14.png"
import cover15 from "@/assets/articleCover/c15.png"
import cover16 from "@/assets/articleCover/c16.png"
import cover17 from "@/assets/articleCover/c17.png"
import cover18 from "@/assets/articleCover/c18.png"
import cover19 from "@/assets/articleCover/c19.png"
import ElSvgIcon from "@/components/ElSvgIcon.vue";

const prop = defineProps({
  id: {
    type: Number,
    default: Math.floor(Math.random() * 10)
  },
  index: {
    type: Number,
    default: 0
  },
   title: {
     type: String
   },
  thumbnail: {
     type: String
  },
  summary: {
     type: String
  },
  createTime: {
     type: String
  },
  categoryName: {
     type: String
  },
  tagNames: {
    type: Array
  },
  viewCount: {
     type: String
  },
  articleId: {
     type: String
  },
})

const imgList = [
  cover1, cover2, cover3, cover4, cover5, cover6, cover7, cover8, cover9, cover10,
  cover11, cover12, cover13, cover14, cover15, cover16, cover17, cover18, cover19
]

</script>

<style lang="scss" scoped>
.article-item {
  display: flex;
  border-radius: 20px;
  background-color: var(--grey-1-a7);
  box-shadow: 10px 10px 10px var(--grey-4),-10px -10px 10px var(--color-purple);
  margin: 40px 20px;
  overflow: hidden;
  transition: all 0.2s ease-out;

  .thumbnail {
    height: 260px;
    display: flex;
    overflow: hidden;
    //border-radius: 10px;
    transition: width .3s linear;

    .el-image {
      transition: all .3s linear;

      &:hover {
        transform: scale(1.1);
      }
    }
  }

  .article-item-content {
    flex: 1;
    margin-left: 10px;
    padding: 15px;
    position: relative;

    .time {
      font-size: 14px;
      margin-bottom: 10px;
    }

    .title {
      font-size: 27px;
      font-weight: bold;
    }

    .summary {
      word-break: break-all;
      margin-top: 10px;
      font-size: 17px;
      line-height: 22px;
      white-space: normal;
      display: block;
      overflow: hidden; // 超出的文本隐藏
      text-overflow: ellipsis; // 溢出用省略号显示
      display: -webkit-box; // 将对象作为弹性伸缩盒子模型显示。
      -webkit-line-clamp: 4; //最多显示4行
      -webkit-box-orient: vertical; // 从上到下垂直排列子元素（设置伸缩盒子的子元素排列方式）
    }

    .info {
      margin-top: 10px;
      font-size: 14px;

      .categoryName .a-link {
        font-size: 16px;
      }
      .viewCount {
        font-size: 14px;
      }
    }

    .tag-name-list {
      margin-top: 10px;
      .tag-name-item {
        margin-right: 5px;
      }
    }

    .more {
      position: absolute;
      bottom: 5px;
      right: 10px;
      font-size: 16px;


      &:hover {
        a {
          color: green;
        }

        .el-icon {
          color: green;
        }
      }

      a {
        color: grey;
      }

      .el-icon {
        vertical-align: -30%;
        color: grey;
      }
    }

  }
}


</style>