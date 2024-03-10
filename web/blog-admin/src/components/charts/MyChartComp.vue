<!--  -->
<template>
  <div class="rowCC">
    <div
      ref="chartRef"
      class="my-chart white-shadow p-1"
      :style="style" ></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts';
import { onMounted, ref } from 'vue'

const chartRef = ref()

const { option, width, height } = defineProps({
  width: {
    type: String,
    default: '100%'
  },
  height: {
    type: String,
    default: '50vh'
  },
  option: {
    type: Object,
    default:{
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'line'
        }
      ]
    }
  },

})

const style = {
  width,
  height
}

onMounted( () => {
  const myChart = echarts.init(chartRef.value);
  myChart.setOption(option);
  window.addEventListener("resize", myChart.resize);
})

</script>
<style lang='scss' scoped>

.my-chart {
  //margin-top: 50px;
  border-radius: 10px;
}

</style>