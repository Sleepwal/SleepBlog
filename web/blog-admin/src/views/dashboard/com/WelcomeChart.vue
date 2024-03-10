<template>
  <div>
    <MyChartComp
      :option="option"
      :height="'15vh'"/>
  </div>
</template>

<script setup>

import { useBasicStore } from "@/store/basic";

const basicStore = useBasicStore()
const username = basicStore.getUsername()

const option = {
  graphic: {
    elements: [
      {
        type: "text",
        left: "center",
        top: "center",
        style: {
          text: "Welcome, " + username + "!",
          fontSize: 80,
          fontWeight: "bold",
          lineDash: [0, 200],
          lineDashOffset: 0,
          fill: "transparent",
          stroke: "#000",
          lineWidth: 1,
        },
        keyframeAnimation: {
          duration: 8000,
          loop: false,
          keyframes: [
            {
              percent: 0.7,
              style: {
                fill: "transparent",
                lineDashOffset: 200,
                lineDash: [200, 0],
              },
            },
            {
              // Stop for a while.
              percent: 0.8,
              style: {
                fill: "transparent",
              },
            },
            {
              percent: 1,
              style: {
                fill: "black",
              },
            },
          ],
        },
      },
    ],
  },
};

</script>

<style lang="scss" scoped>

</style>