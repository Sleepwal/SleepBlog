<template>
  <div class="cover rowCC">
    <img :src="cover" v-if="cover" alt="1"/>
    <img v-else src="@/assets/default_cover.png" alt="1"/>
  </div>
</template>

<script setup>

const props = defineProps({
  cover: {
    type: String,
    default: null
  }
})

</script>

<style lang="scss">
.cover {
  overflow: hidden;
  border-radius: 20px;
  img {
    width: 100%;
  }
}
</style>