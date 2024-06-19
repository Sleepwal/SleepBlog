<template>
  <div class="cover rowCC">
    <img :src="cover" v-if="cover" alt="1"/>
    <span v-else>default</span>
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
  border-radius: 10px;
  img {
    width: 100%;
  }
}
</style>