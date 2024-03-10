<template>
  <div class="frame-body">

    <TopPart></TopPart>
    <div class="frame-header">
        <Header></Header>
    </div>

    <div class="frame-body-content">
        <router-view :key="route.fullPath"/>
    </div>

    <div class="my-footer">
      <Bottom></Bottom>
    </div>

    <ToolsBar/>

  </div>
</template>

<script setup>
import { useRoute } from "vue-router";

const route = useRoute()
import Header from "@/layout/sidebar/header.vue";
import Bottom from "@/layout/sidebar/Bottom.vue";
import TopPart from "@/layout/sidebar/TopPart.vue";
import ToolsBar from "@/layout/sidebar/ToolsBar.vue";

</script>

<style lang="scss">

.frame-body {
  background: var(--body-bg);

  .frame-body-content {
    padding-top: 3%;
    @include custom-border-bottom(100%);
  }

  .my-footer {

  }
}


</style>