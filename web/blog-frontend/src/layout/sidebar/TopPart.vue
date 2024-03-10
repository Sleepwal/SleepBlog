<template>
  <div class="top-part-body">
    <div class="top-part-bg">
      <div class="m-5">
        <el-breadcrumb :separator-icon="ArrowRightBold" class="fs-2">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>

          <el-breadcrumb-item>{{route.meta.title}}</el-breadcrumb-item>
        </el-breadcrumb>

        <div class="h0">{{route.meta.title}}</div>
      </div>
    </div>

    <Wave></Wave>
  </div>
</template>

<script setup>
import Wave from "@/components/Wave.vue";
import {useRoute} from "vue-router";
import {ArrowRightBold} from "@element-plus/icons-vue";

const route = useRoute()

</script>

<style lang="scss" scoped>


.top-part-body {
  position: relative;

  .top-part-bg {
    padding-top: 5%;
    height: 45vh;
    background: var(--top-bg);
  }
}


</style>