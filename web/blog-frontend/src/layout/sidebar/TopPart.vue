<template>
  <div class="top-part-body">
    <div class="top-part-bg">
      <div class="m-5">
        <el-breadcrumb :separator-icon="ArrowRightBold" class="fs-2">
          <el-breadcrumb-item :to="{ path: '/' }">
            <span>首页</span></el-breadcrumb-item>

          <el-breadcrumb-item>
            <span>{{route.meta.title}}</span>
          </el-breadcrumb-item>
        </el-breadcrumb>

<!--        <h1>{{route.meta.title}}</h1>-->
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

<style lang="sass" scoped>

.top-part-body
  position: relative

  .top-part-bg
    padding-top: 5%
    height: 45vh
    background: var(--top-bg)

    .top-part-bread
      background: var(--grey-1-a5)
      border-radius: 10px
      backdrop-filter: saturate(180%) blur(20px)


</style>