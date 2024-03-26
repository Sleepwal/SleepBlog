<!-- 头部公用 -->
<template>
  <!--  <div :class="['header', isShowHigher === true ? 'bg-normal ' : 'bg-bf']">-->
  <!--    <div :class="['topNav', 'rowBC', isShowHigher === true ? 'height-normal' : 'height-low']">-->
  <el-collapse-transition>
    <div class="header bg-normal" v-if="isShowHigher">
      <div class="topNav rowBC height-normal">
        <div class="indexNav rowCC">
          <div class="logo m-1">
            <router-link to="/">
              SleepWalker's Blog
              <!--            <img :src="logo" alt="SleepWalker's Blog"/>-->
            </router-link>
          </div>

          <router-link v-for="item in headerList"
                       :class="['topNavItem', item.link === activePath ? 'active' : '']"
                       :to="item.link">
            <ElSvgIcon :name="item.icon"/>
            {{ item.name }}
          </router-link>

          <el-dropdown>
          <span class="el-dropdown-link rowCC">
            其他
            <el-icon><arrow-down/></el-icon>
          </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item v-for="item in headerOtherList">
                  <router-link :to="item.link">
                    <ElSvgIcon :name="item.icon"/>
                    {{ item.name }}
                  </router-link>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>

          <span class="ml-3 fs-15px"><SearchItem/></span>
          <span class="ml-3 fs-15px"><ThemeButton/></span>
        </div>

        <div class="rowCC">
          <span v-if="token">
          <div class="user-avatar"
               @click="clickShowDropMenu">
            <el-avatar :src="userInfo.avatar"/>
          </div>

          <el-collapse-transition>
            <el-card class="drop-menu"
                     v-show="isShowDrop">
              <router-link to="/UserSetting">
                个人中心
              </router-link>

              <el-divider/>

              <div class=""
                   style="cursor: pointer"
                   @click="logout">
                  退出登录
              </div>
            </el-card>
          </el-collapse-transition>
        </span>

          <router-link v-else class="topNavItem" to="/Login">
            <el-icon>
              <Avatar/>
            </el-icon>
            登录
          </router-link>
        </div>

      </div>
    </div>
  </el-collapse-transition>
</template>


<script setup>
import {onBeforeUnmount, onMounted, ref, watch} from "vue";
import {useRouter} from "vue-router";
import logoUrl from "@/assets/logoPic.png";
import {userLogout} from "@/api/user";
import {ElMessage} from "element-plus";
import {useBasicStore} from "@/stores/basic";
import {storeToRefs} from "pinia";
import ElSvgIcon from "@/components/ElSvgIcon.vue";
import SearchItem from "@/layout/sidebar/SearchItem.vue";
import ThemeButton from "@/layout/sidebar/ThemeButton.vue";

const logo = ref(logoUrl);
const router = useRouter();

const headerList = [
  {
    name: "首页",
    icon: "HomeFilled",
    link: "/"
  }, {
    name: "分类",
    icon: "Filter",
    link: "/category"
  }, {
    name: "标签",
    icon: "PriceTag",
    link: "/tag"
  }, {
    name: "文章关系图",
    icon: "Histogram",
    link: "/ArticleRelationChart"
  }, {
    name: "知识库",
    icon: "Notebook",
    link: "/KnowledgeBase"
  }, {
    name: "留言板",
    icon: "ChatDotSquare",
    link: "/Message"
  }, {
    name: "聊天室",
    icon: "Notification",
    link: "/ChatRoom"
  }
];

const headerOtherList = [
  {
    name: "归档",
    icon: "TrendCharts",
    link: "/TimeLineArticle"
  }, {
    name: "工具箱",
    icon: "MagicStick",
    link: "/toolsIndex"
  }, {
    name: "关于",
    icon: "InfoFilled",
    link: "/about"
  }, {
    name: "友链",
    icon: "Link",
    link: "/friendLink"
  }
];

const activePath = ref(null);
watch(() => router, (newVal, oldVal) => {
  activePath.value = newVal.currentRoute.value.meta.activePath;
  // console.log(activePath.value)
}, {immediate: true, deep: true});

const basicStore = useBasicStore();

const {token, userInfo} = storeToRefs(basicStore);

const logout = async () => {
  await userLogout();
  basicStore.resetState();
  ElMessage.success("退出登录成功!");
};

//是否显示 高度更高,字体更大
const isShowHigher = ref(true);
//上一次滚动条位置
let preScroll = 0;

onMounted(() => {
  // 监听滚动条位置
  window.addEventListener("scroll", scrollTop, true);
});
// 实时滚动条高度
const scrollTop = () => {
  //往下滚变小，往上滚变大
  /*let scroll = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
  isShowHigher.value = scroll < preScroll
  preScroll = scroll*/

  //导航栏顶部正常尺寸，其他位置缩小
  let scroll = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
  isShowHigher.value = scroll === 0;
  preScroll = scroll;
};
onBeforeUnmount(() => {
  window.removeEventListener("scroll", () => {
  }); // 离开当前组件别忘记移除事件监听
});

const isShowDrop = ref(false);
const clickShowDropMenu = () => {
  isShowDrop.value = !isShowDrop.value;
};

</script>

<style lang="scss" scoped>
.header {
  width: 100%;
  position: fixed;
  top: 0;
  //不被遮挡
  z-index: 99;

  //顶部导航栏
  .topNav {

    //首页logo
    .indexNav {
      //flex: 1;

      .logo {
        font-size: 25px;
        font-weight: bold;
        color: var(--color-orange);

        img {
          //background-color: #daa3a3;
          width: 40px;
          height: 40px;
        }
      }
    }

    //导航栏的link
    .topNavItem {
      padding: 15px 10px;
      color: var(--grey-9);
      @include hover-broder-bottom;
    }

    //鼠标悬浮颜色
    .topNavItem:hover {
      color: var(--color-purple);
      font-weight: bold;
    }

    .el-dropdown-link {
      height: 60px;
      width: 60px;
      font-size: 18px;
      cursor: pointer;
      color: var(--grey-9);
    }

    .active {
      color: var(--color-purple);
      font-weight: bold;
    }

    .user-avatar {
      cursor: pointer;
      margin-right: 20px;
      transition: all 0.6s ease;
    }


    .drop-menu {
      font-size: 16px;
      position: absolute;
      top: 50px;
      right: 1vw;
      width: 7vw;
    }

  }
}

.height-normal {
  height: 60px;
  font-size: 18px;
  transition: 0.3s linear;
}

.height-low {
  height: 45px;
  font-size: 16px;
  transition: 0.3s linear;
}

.bg-normal {
  background: var(--header-bg);
  transition: 0.3s linear;
}

.bg-bf {
  background: var(--grey-1-a3);
  backdrop-filter: saturate(180%) blur(20px);
  transition: 0.3s linear;
}

</style>
