<!--  -->
<template>
  <div class="rowCC">
    <div ref="wordRef" class="word white-shadow"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts';
import 'echarts-wordcloud';
import { TooltipComponent } from 'echarts/components';
import { getTagList } from "@/api/tag";
echarts.use([TooltipComponent]);
import { onMounted, ref } from 'vue'

const wordRef = ref()
//标签
const tagList = await getTagList()

const option = reactive({
  title: {
    text: '文章标签统计',
    x: "center"
  },
  // backgroundColor: "#fff",
  tooltip: {
    pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>"
  },
  series: [
    {
      type: "wordCloud",
      //用来调整词之间的距离
      gridSize: 10,
      //用来调整字的大小范围
      // Text size range which the value in data will be mapped to.
      // Default to have minimum 12px and maximum 60px size.
      sizeRange: [14, 40],
      // Text rotation range and step in degree. Text will be rotated randomly in range [-90,                                                                             90] by rotationStep 45
      //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
      // rotationRange: [-45, 0, 45, 90],
      // rotationRange: [ 0,90],
      rotationRange: [0, 0],
      //随机生成字体颜色
      // maskImage: maskImage,
      textStyle: {
        color: function () {
          return (
            "rgb(" +
            Math.round(Math.random() * 255) +
            ", " +
            Math.round(Math.random() * 255) +
            ", " +
            Math.round(Math.random() * 255) +
            ")"
          );
        }
      },
      //位置相关设置
      // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
      // Default to be put in the center and has 75% x 80% size.
      left: "center",
      top: "center",
      right: null,
      bottom: null,
      width: "100%",
      height: "100%",
      //数据
      data: tagList
    }
  ]
})

onMounted( () => {
  const myChart = echarts.init(wordRef.value);
  myChart.setOption(option);
})

</script>
<style lang='scss' scoped>

.word {
  //margin-top: 50px;
  height: 50vh;
  width: 100%;
  border-radius: 10px;
}

</style>