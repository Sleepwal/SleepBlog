<template>
  <div class="out-box">
    <div class="frame-header">
        <Header></Header>
    </div>

    <div class="frameContent">
      <router-view />
    </div>

    <div class="my-footer">
      <Bottom></Bottom>
    </div>

    <ToolsBar/>

  </div>
</template>

<script setup>
import Header from "@/layout/sidebar/header.vue";
import Bottom from "@/layout/sidebar/Bottom.vue";
import ToolsBar from "@/layout/sidebar/ToolsBar.vue";

</script>

<style lang="scss">

.out-box {
  background: var(--body-bg);
  //margin-right: calc(100% - 100vw);

  .frameContent {

    padding-top: 3%;
    @include custom-border-bottom(100%);
  }

  .my-footer {

  }
}




</style>