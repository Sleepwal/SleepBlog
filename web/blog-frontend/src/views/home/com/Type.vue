<template>
  <div class="type-body rowCC">
<!--    <h1 class="center-20 type-title p-1 br-2 bf">双链博客系统</h1>-->
<!--    <img alt="Vue logo" src="@/assets/logo.png" class="center-20" />-->
    <div class="type-text">
      <Typed :options="options">
        <h1 class="typing fs-5"></h1>
      </Typed>
    </div>

  </div>
</template>

<script setup lang="ts">
import type { TypedOptions } from "@duskmoon/vue3-typed-js";
import { Typed } from "@duskmoon/vue3-typed-js";

const options: TypedOptions = {
  strings: [
    "双链博客系统",
  ],
  loop: false,
  typeSpeed: 60
};
</script>

<style lang="sass">
.type-body
  height: 50vh

  .type-title
    color: var(--grey-0)
    background: var(--grey-9-a9)

  .type-text
    color: white
    text-shadow: 0 0 10px black
    //background: rgba(115, 178, 134, 0.40);
    height: 20vh

  img
    width: 600px
    height: 150px

</style>