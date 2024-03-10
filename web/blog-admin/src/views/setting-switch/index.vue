<template>
  <div class="scroll-y">
    <h3 class="mb-20px">props operate demo of settings.js</h3>
    <div class="rowSS">
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

          <fieldset class="ml-5">
            <legend>侧栏设置</legend>
            <el-button @click="sidebar.opened = !sidebar.opened">侧栏开关</el-button>
          </fieldset>
        </div>
      </div>
      <div class="mb-10px">
        <div class="font-bold text-20px">page layout related</div>
        <div class="mt-20px">
          sidebarLogo：
          <el-switch v-model="settings.sidebarLogo" />
        </div>
        <div class="mt-30px">
          showNavbarTitle：
          <el-switch v-model="settings.showNavbarTitle" />
        </div>
        <div class="mt-30px">
          ShowDropDown：
          <el-switch v-model="settings.ShowDropDown" />
        </div>
        <div class="mt-30px">
          showHamburger：
          <el-switch v-model="settings.showHamburger" />
        </div>
        <div class="mt-30px">
          showLeftMenu：
          <el-switch v-model="settings.showLeftMenu" />
        </div>
        <div class="mt-30px">
          showTagsView：
          <el-switch v-model="settings.showTagsView" />
        </div>
        <div class="mt-30px">
          showTopNavbar：
          <el-switch v-model="settings.showTopNavbar" />
        </div>
      </div>
      <div class="mb-10px ml-60px">
        <div class="font-bold text-20px">page animation related</div>
        <div class="mt-20px">mainNeedAnimation：places to "settings file" for setting</div>
        <div class="mt-30px">
          isNeedNprogress：
          <el-switch v-model="settings.isNeedNprogress" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const { settings } = storeToRefs(useBasicStore())
//由于使用了 AutoImport 插件 可以直接引入pinia里的api
//storeToRefs:引入的store属性具备响应式
const { sidebar } = storeToRefs(useBasicStore())

//
import { useRoute } from 'vue-router'
import { useConfigStore } from '@/store/config'

const { setTheme, theme, setSize, size, setLanguage } = useConfigStore()
const route = useRoute()
const changeLanguage = (langParam) => {
  setLanguage(langParam, route.meta?.title)
}
</script>
