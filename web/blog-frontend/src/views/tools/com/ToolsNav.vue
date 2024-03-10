<template>
  <div class="tools-nav-body">
    <el-menu
        :default-active="activeIndex"
        class="rowCC"
        mode="horizontal"
        @select="handleSelect"
        background-color="var(--grey-3)"
        text-color="var(--grey-9)"
    >
      <el-menu-item v-for="tool in toolList" :index="tool.index">
        <router-link :to="tool.link">
          {{tool.name}}
        </router-link>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>

import {ref} from "vue";

const toolList = [
  {
    index: 1,
    name: 'IP扫描',
    link: '/toolsIndex/IpScan'
  },{
    index: 2,
    name: '端口扫描',
    link: '/toolsIndex/PortScan'
  },{
    index: 3,
    name: 'Ping测试',
    link: '/toolsIndex/PingTest'
  },{
    index: 4,
    name: 'Base64编码/解码',
    link: '/toolsIndex/Base64'
  },{
    index: 5,
    name: '电子木鱼',
    link: '/toolsIndex/MuYun'
  },
]

const activeIndex = ref(0)

const handleSelect = (key, keyPath) => {
  console.log(key, keyPath)
  activeIndex.value = key
}

</script>

<style lang="scss">
.tools-nav-body {
  padding-top: 2vh;
}

</style>