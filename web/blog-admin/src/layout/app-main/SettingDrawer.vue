<template>
  <div>
    <!--  抽屉  -->
    <el-button @click="drawerVisible = true">
      设置
    </el-button>
    <el-drawer v-model="drawerVisible"
               :show-close="true">
      <template #header="{ close, titleId, titleClass }">
        <h2>设置</h2>
      </template>

      <div class="mt-10px mr-5">
        <div class="font-bold mb-20px">设置</div>
        <fieldset>
          <legend>主题设置</legend>
          <el-button @click="setTheme('base-theme')">base-theme(default)</el-button>
          <el-button @click="setTheme('lighting-theme')">lighting-theme</el-button>
          <el-button @click="setTheme('china-red')">china-red(default)</el-button>
          <el-button @click="setTheme('dark')">dark-theme</el-button>
        </fieldset>

        <div class="rowSS mt-10px mb-10px">
          <fieldset>
            <legend>字体大小</legend>
            <el-button @click="setSize('large')">large</el-button>
            <el-button @click="setSize('default')">default</el-button>
            <el-button @click="setSize('small')">small</el-button>
          </fieldset>
          <fieldset class="ml-5">
            <legend>设置语言</legend>
            <el-button @click="changeLanguage('en')">en</el-button>
            <el-button @click="changeLanguage('zh')">zh</el-button>
          </fieldset>
        </div>
      </div>

      <div class="mb-10px">
        <fieldset>
          <legend>主题</legend>

          <el-button @click="toggleDark()"
                     text>
          <span v-if="isDark">
            默认主题
            <el-icon size="20"><Sunny /></el-icon>
          </span>

            <span v-else>
            暗黑主题
            <el-icon size="20"><Moon /></el-icon>
          </span>
          </el-button>
        </fieldset>

        <fieldset>
          <legend>页面设置</legend>

          <div class="mt-20px">
            显示侧栏Logo：
            <el-switch v-model="settings.sidebarLogo" />
          </div>

          <div class="mt-30px">
            显示导航栏标题：
            <el-switch v-model="settings.showNavbarTitle" />
          </div>

          <div class="mt-30px">
            显示下拉框：
            <el-switch v-model="settings.ShowDropDown" />
          </div>

          <div class="mt-30px">
            显示面包屑：
            <el-switch v-model="settings.showHamburger" />
          </div>

          <div class="mt-30px">
            显示左侧菜单：
            <el-switch v-model="settings.showLeftMenu" />
          </div>

          <div class="mt-30px">
            显示页面标签：
            <el-switch v-model="settings.showTagsView" />
          </div>

          <div class="mt-30px">
            是否需要动画：
            <el-switch v-model="settings.isNeedNprogress" />
          </div>
        </fieldset>


      </div>

    </el-drawer>
  </div>
</template>

<script setup>
import { useDark, useToggle } from "@vueuse/core";

const { settings } = storeToRefs(useBasicStore())
// const { config } = storeToRefs(useConfigStore())
//由于使用了 AutoImport 插件 可以直接引入pinia里的api
//storeToRefs:引入的store属性具备响应式
import { useRoute } from 'vue-router'
import { useConfigStore } from '@/store/config'

const { setTheme, setSize } = useConfigStore()
const route = useRoute()
const changeLanguage = (langParam) => {
  // setLanguage(langParam, route.meta?.title)
}

const drawerVisible = ref(false)

const isDark = useDark()
const toggleDark = useToggle(isDark)


</script>

<style lang="scss" scoped>

.el-icon {
  vertical-align: -20%
}

</style>