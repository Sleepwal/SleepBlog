<template>
  <div class="frame2-body">
    <div class="frame-header">
      <Header></Header>
    </div>
    <div class="frame2-content">
      <router-view></router-view>
    </div>

  </div>
</template>

<script setup>
import Header from "@/layout/sidebar/header.vue";

</script>

<style lang="scss">

.frame2-body {
  .frame2-content {
  }
}

</style>