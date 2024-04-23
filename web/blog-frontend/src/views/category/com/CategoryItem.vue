<template>
  <div class="category-item ">
      <div class="category-info rowCC">
        <div>
          <router-link :to="'/category/' + data.id + '/' + data.name">
            <div class="c-item-name"> {{data.name}} </div>
          </router-link>
          <div class="c-item-count mt-1"> 文章数: {{data.value}} </div>
        </div>

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
  .category-item {
    //width: 30.7%;
    padding: 10px;
    display: flex;
    flex-wrap: wrap;

    .category-info {
      width: 15vw;
      height: 20vh;
      background: var(--grey-1);
      border-radius: 100%;
      transition: all 0.2s ease-out;
      box-shadow: 18px 18px 10px var(--grey-9-a2),
      -10px -10px 10px var(--grey-1);

      &:hover {
        animation: author-shake 1s;
        cursor: pointer;

        box-shadow: 0 0 0 var(--grey-9-a2),
        0 0 0 var(--grey-1),
        inset 18px 18px 30px var(--grey-9-a1),
        inset -18px -18px 30px var(--grey-1);
      }

      .c-item-name {
        margin: 0 5px;
        font-size: 40px;
        transition: all 0.2s ease-out;

        &:hover {
          font-weight: bold;
          transform: scale(1.2);
        }
      }

      .c-item-count {
        font-size: 25px;
      }
    }
  }

</style>