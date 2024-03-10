<template>
  <div class="option-button" v-if="isShowOption">
    <div v-if="isShowTools" class="button-tool-list ta-c">
      <SearchItem></SearchItem>

      <!-- 跳转到评论区 -->
      <div class="mt-1" v-if="route.meta.title === '浏览文章' || route.meta.title === '友链'">
        <span @click="jumpToComment()" class="option-button-item">
          <el-icon><ChatSquare /></el-icon>
        </span>
      </div>

      <!--   切换主题   -->
      <div class="mt-1">
        <span @click="changeTheme()" class="option-button-item"
             v-if="theme !== 'dark'" type="info">
          <el-icon><Moon /></el-icon></span>
        <span @click="changeTheme()" class="option-button-item"
             v-else type="warning">
          <el-icon><Sunny /></el-icon></span>
      </div>

      <!--   回到顶部   -->
      <div @click="scrollToTop()">
        <span class="option-button-item mt-1">
          <el-icon><Top /></el-icon>
        </span>
      </div>
    </div>

    <!--   回到顶部   -->
    <div @click="scrollToTop()" v-if="!isShowTools" class="ta-c mt-1">
        <span class="option-button-item">
          <el-icon><Top /></el-icon>
        </span>
    </div>

    <div @click="showTools"
         class="custom-btn mt-1 show-tool-setting">
      <span class="show-tool-button">
        <ElSvgIcon name="Setting"></ElSvgIcon>
      </span>

      <span class="process-count">
        {{ process }}%
      </span>
    </div>
  </div>

  <!--   切换主题   -->
  <div class="mt-1 login-reg-show" v-if="route.meta.title === '登录' || route.meta.title === '注册'">
        <span @click="changeTheme()" class="login-reg-show-btn"
              v-if="theme !== 'dark'" type="info">
          <el-icon><Moon /></el-icon></span>
    <span @click="changeTheme()" class="login-reg-show-btn"
          v-else type="warning">
          <el-icon><Sunny /></el-icon></span>
  </div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref } from "vue";
import {useSystemStore} from "@/stores/system";
import {useEventListener} from "@vueuse/core";
import SearchItem from "@/layout/sidebar/SearchItem.vue";
import ElSvgIcon from "@/components/ElSvgIcon.vue";
import { useRoute } from "vue-router";

const route = useRoute()

//主题
const theme = ref(useSystemStore().getTheme())
document.documentElement.setAttribute('class', theme.value) //初始化

const changeTheme = () => {
  let tmp = theme.value === 'dark' ? 'light' : 'dark'
  document.documentElement.setAttribute('class', tmp)
  theme.value = tmp
  useSystemStore().setTheme(tmp)
}

//显示设置按钮
const process = ref(0);
const isShowTools = ref(false)
const showTools = () => {
  isShowTools.value = !isShowTools.value
}

/**
 * 跳转到评论区
 */
const jumpToComment = () => {
  const element = document.getElementById('c1')
  if (element)
    element.scrollIntoView({ behavior: 'smooth' })
}

onMounted(() => {
  useEventListener(document, "scroll", () => {
    process.value = parseInt(
        (window.scrollY / (document.documentElement.scrollHeight - window.innerHeight))
        * 100
    ).toFixed(0)
  });
})

/**
 * 滚动到顶部
 */
const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: "smooth" });
}

//到顶部不显示设置
const isShowOption = ref(false)
onMounted(() => {
  // 监听滚动条位置
  window.addEventListener('scroll', scrollTop, true)
})

/**
 * 实时滚动条高度
 */
const scrollTop = () => {
  let scroll = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
  isShowOption.value = scroll > 100;
}
onBeforeUnmount(() => {
  window.removeEventListener('scroll', () => {}); // 离开当前组件别忘记移除事件监听
});

</script>

<style lang="scss" scoped>

.option-button {
  position: fixed;
  bottom: 2%;
  right: .5%;
  z-index: 10;
  animation: sidebarItem .5s ease-in-out;

  .button-tool-list {
    animation: sidebarItem .3s linear;
  }

  .option-button-item {
    //width: 40px;
    @include my-btn-border;
  }

  .show-tool-setting {

    &:hover {
      .process-count {
        display: none;
      }

      .show-tool-button {
        display: block;
      }
    }

    .show-tool-button {
      display: none;
    }

  }

}

.login-reg-show {
  position: fixed;
  bottom: 5%;
  right: 2%;
  z-index: 10;

  .login-reg-show-btn {
    @include my-btn-border;
  }
}

</style>