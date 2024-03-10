<template>
  <div>
    <el-card>
      <template #header>
        <h2>数据分析</h2>
      </template>

      <el-row :gutter="10">
        <el-col class="flex-1">
          <MyChartComp
            :option="userActiveOption"
            :height="'60vh'"
          />
        </el-col>
      </el-row>

      <el-row :gutter="10" class="chart-row">
        <el-col span="8" class="flex-1">
          <div class="dash-chart">
            <MyChartComp :option="option" :width="'26vw'"></MyChartComp>
          </div>
        </el-col>

        <el-col span="8" class="flex-1">
          <el-table
            class="white-shadow br-2 p-1 view-count-list"
            :data="articleViewCountList"
            height="50vh"
            stripe>
            <el-table-column
              prop="title"
              label="文章标题"
            />

            <el-table-column
              prop="viewCount"
              label="浏览量"
              width="80"
            />
          </el-table>
        </el-col>

        <el-col span="8" class="flex-1">
          <div class="dash-chart">
            <WordCloudChart :width="'26vw'"/>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10" class="chart-row">
        <el-col class="flex-1">
          <MyChartComp
            :option="hotMapOption"
            :height="'65vh'"/>
        </el-col>
      </el-row>
    </el-card>

<!--    <div>
      <div class="dash-chart2 m-2">
        <MyChartComp :option="barOption"></MyChartComp>
      </div>
    </div>-->
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {
  getArticleActivity,
  getArticleList,
  getArticleRelationGraphData,
  getArticleRelationGraphLinks
} from "@/api/article";
import {getCategoryList} from "@/api/category";
import { documentList4Chart } from "@/api/know";
import WordCloudChart from "@/views/dashboard/com/TagWordCloudChart.vue";
import { getHourlyActivity } from "@/api/systemLog";

const articleList = await getArticleList()
const categoryList = await getCategoryList()
const knowDocumentList = await documentList4Chart()
const hourlyActivityList = await getHourlyActivity()
const nodes = await getArticleRelationGraphData()
const links = await getArticleRelationGraphLinks()

const option = reactive({
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
    left: 'left',
  },
  series: [
    {
      name: 'Category',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      roseType: 'area',
      data: categoryList,
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

const months = [
  '1月', '2月', '3月', '4月', '5月', '6月',
  '7月', '8月', '9月', '10月', '11月',
  '12月'
];
const days = [
  'Sunday', 'Saturday', 'Friday', 'Thursday',
  'Wednesday', 'Tuesday', 'Monday'
];
const data = await getArticleActivity()

const hotMapOption = reactive({
  title: {
    text: '文章贡献图',
  },
  tooltip: {
    position: "top",
  },
  grid: {
    height: "50%",
    top: "10%",
  },
  xAxis: {
    type: "category",
    data: months,
    splitArea: {
      show: true,
    },
  },
  yAxis: {
    type: "category",
    data: days,
    splitArea: {
      show: true,
    },
  },
  visualMap: {
    min: 0,
    max: 10,
    calculable: true,
    orient: "horizontal",
    left: "center",
    bottom: "15%",
  },
  series: [
    {
      name: "Punch Card",
      type: "heatmap",
      data: data.map(item => {
        return [parseInt(item[1]), parseInt(item[0]), parseInt(item[2]) || '-'];
      }),
      label: {
        show: true,
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowColor: "rgba(0, 0, 0, 0.5)",
        },
      },
    },
  ],
})

const articleViewCountList = articleList.map(a => {
  return {
    title: a.title,
    viewCount:  a.viewCount
  }
})

function generateTimeSeries() {
  const timeSeries = [];
  for (let i = 0; i <= 24; i++) {
    const hours = i < 10 ? '0' + i : '' + i;
    const time = hours + ':00';
    timeSeries.push(time);
  }

  return timeSeries;
}

const userActiveOption = reactive({
  title: {
    text: '用户活跃时间统计',
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross'
    }
  },
  toolbox: {
    show: true,
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: generateTimeSeries(),
  },
  yAxis: {
    type: 'value',
    axisLabel: {
      formatter: '{value} 人'
    },
    axisPointer: {
      snap: true
    }
  },
  series: [
    {
      name: '活跃人数',
      type: 'line',
      smooth: true,
      data: hourlyActivityList,
    }
  ]
})

const knowCateOption = reactive({
  title: {
    text: '知识库文档统计',
    left: 'center',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
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
        borderWidth: 2,
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold',
        }
      },
      labelLine: {
        show: false
      },
      data: knowDocumentList,
    }
  ],
});
const barOption = reactive({
  title: {
    text: '文章浏览量'
  },
  tooltip: {},
  xAxis: {
    data: articleList.map(a => a.title)
  },
  yAxis: {
  },
  series: [
    {
      name: [],
      type: 'bar',
      data: articleList.map(a => a.viewCount)
    }
  ]
})
</script>

<style lang="scss" scoped>
.el-card {
  margin: 10px;
  border-radius: 20px;

  .chart-row {
    margin-top: 20px;
    //padding: 10px
  }
}

.dash-chart {
  //width: 50vw;
  //flex: 1;
  //height: 50vh;
}

.dash-chart2 {
  //width: 100%;
  //height: 50vh;
}

.view-count-list {
  width: 26vw;

  .view-count-item {

  }

}

</style>