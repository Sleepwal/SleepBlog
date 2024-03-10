<template>
  <div class="tag-item">
      <div class="tag-info">
        <router-link :to="'/tagDetail/' + data.id + '/' + data.name">
          <span class="c-item-name hover-broder-bottom"> {{data.name}} </span>
        </router-link>
        <span class="c-item-count"> {{data.value}} </span>
      </div>
  </div>
</template>

<script setup>
  const props = defineProps({
    data: {
      type: Object
    }
  })
</script>

<style lang="scss" scoped>
  .tag-item {
    width: 30.7%;
    padding: 10px;
    .tag-info {
      border-radius: 20px;
      .c-item-name {
        margin: 0 5px;
        font-size: 18px;
      }
      .c-item-count {
        font-size: 18px;
        color: green;
        //跟分类名高度一致
        &::after {
          content: "";
          height: 2px;
          overflow: hidden;
          display: block;
        }
      }
    }
  }

</style>