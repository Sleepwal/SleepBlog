<template>
  <div class="type-body rowCC">
    <img alt="Vue logo" src="@/assets/logo.png" class="center-20" />
    <div class="type-text">
      <Typed :options="options">
        <h1 class="typing"></h1>
      </Typed>
    </div>

  </div>
</template>

<script setup lang="ts">
import type { TypedOptions } from "@duskmoon/vue3-typed-js";
import { Typed } from "@duskmoon/vue3-typed-js";

const options: TypedOptions = {
  strings: [
    "Outside the rain's falling down",
    "There's not a drop that hits me",
    "Scream at the sky but no sound is leaving my lips",
    "It's like I can't even feel",
    "After the way you touched me",
    "Let me out of this dream !"
  ],
  loop: false,
  typeSpeed: 60
};
</script>

<style lang="scss">
.type-body {
  height: 50vh;

  .type-text {
    color: white;
    text-shadow: 0 0 10px black;
    //background: rgba(115, 178, 134, 0.40);
    height: 20vh;
  }

  img {
    width: 600px;
    height: 150px;
  }
}
</style>