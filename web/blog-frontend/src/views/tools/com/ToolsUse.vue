<template>
  <div class="tools-use-body br-2 mt-1">
    <div class="" >
      <transition name="el-zoom-in-center">
        <router-view/>
      </transition>
    </div>
  </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.tools-use-body {
  height: 100%;
  //background-color: #e0d6d6;
  //padding: 20px;
  //margin: 2% 15%;
}


</style>