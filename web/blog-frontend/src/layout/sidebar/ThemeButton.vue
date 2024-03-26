<!--   切换主题   -->
<template>

  <span @click="changeTheme()" class="theme-button"
        v-if="theme !== 'dark'" type="info">
          深色<el-icon><Moon/></el-icon>
  </span>
  <span @click="changeTheme()" class="theme-button"
        v-else type="warning">
          浅色<el-icon><Sunny/></el-icon>
  </span>

</template>

<script setup>
import {ref} from "vue";
import {useSystemStore} from "@/stores/system";

const theme = ref(useSystemStore().getTheme())
document.documentElement.setAttribute('class', theme.value) //初始化

const changeTheme = () => {
  let tmp = theme.value === 'dark' ? 'light' : 'dark'
  document.documentElement.setAttribute('class', tmp)
  theme.value = tmp
  useSystemStore().setTheme(tmp)
}

</script>

<style scoped lang="sass">
@import "@/styles/mixin.scss"

.theme-button
  @include my-btn-border
</style>