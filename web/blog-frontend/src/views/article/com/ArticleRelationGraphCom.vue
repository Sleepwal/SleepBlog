<template>
  <div class="rowCC">
    <div ref="chartRef" :style="style"
         class="article-relation m-1"
    ></div>
  </div>
</template>

<script setup>
import * as echarts from "echarts";
import { computed, onMounted, reactive, ref, watch } from "vue";
import { useRouter } from "vue-router";
import { useSystemStore } from "@/stores/system";

const { graphNodes, graphLinks, categories, style } = defineProps({
  graphNodes: {
    type: Array,
    default: []
  },
  graphLinks: {
    type: Array,
    default: []
  },
  categories: {
    type: Array,
    default: []
  },
  style: {
    type: Object,
    default: {
      height: "90vh",
      width: "90%"
    }
  }
});

const router = useRouter();
const chartRef = ref();
let myChart;
const theme = ref(useSystemStore().getTheme());

const option = {
  darkMode: theme.value === "dark", //暗黑模式
  backgroundColor: theme.value === "dark" ? "#000" : "#fff", //背景
  title: {
    text: "文章关系图"
  },
  textStyle: {
    fontSize: 14,
  },
  legend: {
    show: true,
    orient: "vertical",
    left: "right",
    data: categories,
  },
  tooltip: {},
  animationDurationUpdate: 1500,
  animationEasingUpdate: "quinticInOut",
  series: [
    {
      type: "graph",
      layout: "force", //力引导布局
      draggable: true, //可拖拽
      symbolSize: 30,
      roam: true,
      label: {
        position: "top",
        show: true,
        formatter: "{c}"
      },
      edgeSymbol: ["circle", "arrow"],
      edgeSymbolSize: [4, 10],
      edgeLabel: {
        fontSize: 20
      },
      emphasis: {
        focus: "adjacency",
        label: {
          position: "top",
          show: true
        }
      },
      lineStyle: {
        opacity: 0.8,
        width: 2,
        curveness: 0.1
      },
      data: graphNodes,
      links: graphLinks,
      categories: categories,
    }
  ]
};

const initChart = () => {
  console.log(option)
  myChart = echarts.init(chartRef.value);
  myChart.setOption(option);

  // 点击事件
  myChart.on("click", (params) => {
    router.push("/ArticleDetail/" + params.data.id);
  });
};

// 使用 watch 监听主题变化
watch(() => useSystemStore().getTheme(), (newTheme) => {
  theme.value = newTheme;
  option.darkMode = (newTheme === "dark");
  option.backgroundColor = (newTheme === "dark" ? "#000" : "#fff");
  myChart.setOption(option);
});

onMounted(() => {
  initChart();
  window.addEventListener("resize", chartRef.value.resize);
});

</script>

<style lang="scss" scoped>

.article-relation {
  //height: 90vh;
  //width: 90%;
  //border: 1px solid var(--grey-9);
}

</style>