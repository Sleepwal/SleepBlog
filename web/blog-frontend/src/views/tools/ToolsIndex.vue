<template>
  <div class="tools-index-body slide-in-container">
    <!--    <ToolsNav></ToolsNav>-->
    <!--    <ToolsUse></ToolsUse>-->

    <el-tabs
      class="tools-tabs"
      v-model="activeName"
      tab-position="left">
      <el-tab-pane label="IP扫描" name="IP扫描">
        <IpScan />
      </el-tab-pane>

      <el-tab-pane label="端口扫描">
        <PortScan />
      </el-tab-pane>

      <el-tab-pane label="Ping测试" name="Ping测试">
        <PingTest />
      </el-tab-pane>

      <el-tab-pane label="Base64编码/解码">
        <Base64 />
      </el-tab-pane>

<!--      <el-tab-pane label="电子木鱼">-->
<!--        <MuYu />-->
<!--      </el-tab-pane>-->
    </el-tabs>

  </div>
</template>

<script setup>

import IpScan from "@/views/tools/com/IpScan.vue";
import PingTest from "@/views/tools/com/PingTest.vue";
import PortScan from "@/views/tools/com/PortScan.vue";
import Base64 from "@/views/tools/com/Base64.vue";
import MuYu from "@/views/tools/com/MuYu.vue";
import { ref } from "vue";

const activeName = ref("IP扫描");

</script>

<style lang="scss">
.tools-index-body {
  min-height: 60vh;

  .tools-tabs {
    padding: 0 10%;
  }

  .el-tabs__item {
    font-size: 25px;
  }
}
</style>