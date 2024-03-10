<template>
  <div class="dash-body">
    <el-scrollbar height="90vh">
      <WelcomeChart></WelcomeChart>

      <MainDataCard></MainDataCard>

      <Suspense>
        <template #default>
          <NumberShowChart></NumberShowChart>
        </template>
      </Suspense>

    </el-scrollbar>
  </div>
</template>

<script setup>
import NumberShowChart from "@/views/dashboard/com/NumberShowChart.vue";
import MainDataCard from "@/views/dashboard/com/MainDataCard.vue";
import WordCloudChart from "@/views/dashboard/com/TagWordCloudChart.vue";
import MyChartComp from "@/components/charts/MyChartComp.vue";
import WelcomeChart from "@/views/dashboard/com/WelcomeChart.vue";

</script>

<style lang="scss" scoped>
.dash-body {
  //height: 100vh;
}

</style>
