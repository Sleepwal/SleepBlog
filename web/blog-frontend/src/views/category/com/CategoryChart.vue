<template>
    <div class="category-chart-body m-2 br-2">
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
import {getCategoryList} from "@/api/category";
import {useBasicStore} from "@/stores/basic";
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

const categoryList = ref([])
const loadCategoryList = async () => {
  categoryList.value = await getCategoryList()
}

const theme = computed(() => useSystemStore().getTheme())

provide(THEME_KEY, theme);

onMounted(async () => {
  await loadCategoryList()
  option.value.series[0].data = categoryList.value
  option.value.legend.data = categoryList.value.map(c => c.name)
})

const option = ref({
  title: {
    text: '文章分类统计',
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
      name: 'Category',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [],
    },
  ],
});
</script>

<style lang="scss" scoped>
.category-chart-body {
  width: 50vw;
  height: 50vh;
  box-shadow: inset 0 -3em 3em rgba(0,0,0,0.1),
  0 0  0 2px rgb(255,255,255),
  0.3em 0.3em 1em rgba(0,0,0,0.3);
  overflow: hidden;
}
</style>