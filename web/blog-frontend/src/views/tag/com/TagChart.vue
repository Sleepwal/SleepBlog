<template>
    <div class="tag-chart-body m-2 br-2">
      <v-chart class="chart" :option="option" autoresize />
    </div>
</template>

<script setup>
import { CanvasRenderer } from 'echarts/renderers';
import {BarChart, PieChart} from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent, GridComponent, DatasetComponent, TransformComponent,
} from 'echarts/components';
import VChart, { THEME_KEY } from 'vue-echarts';
import {LabelLayout, UniversalTransition} from "echarts/features";
import {computed, onMounted, provide, ref} from "vue";
import {use} from "echarts";
import {getTagList} from "@/api/tag";
import {useSystemStore} from "@/stores/system";

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  BarChart,
  LabelLayout,
  UniversalTransition,
]);


const tagList = ref()
const loadTagList = async () => {
  tagList.value = await getTagList()
}

const theme = computed(() => useSystemStore().getTheme())
provide(THEME_KEY, theme);

onMounted(async () => {
  await loadTagList()
  option.value.series[0].data = tagList.value
  option.value.legend.data = tagList.value.map(c => c.name)
})

const option = ref({
  title: {
    text: '文章标签统计',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    left: 'right',
    data: [],
  },
  series: [
    {
      name: 'Tag',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      roseType: 'area',
      data: [],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    },
  ],
});
</script>

<style lang="scss" scoped>
.tag-chart-body {
  width: 50vw;
  height: 50vh;
  box-shadow: inset 0 -3em 3em rgba(0,0,0,0.1),
  0 0  0 2px rgb(255,255,255),
  0.3em 0.3em 1em rgba(0,0,0,0.3);
  overflow: hidden;
}
</style>