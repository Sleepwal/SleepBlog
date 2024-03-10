<template>
  <div>
    <el-table ref="dataTable" border
              :data="dataSource.list || []"
              :height="tableHeight"
              header-row-class-name="table-header-row"
              highlight-current-row
              @row-click="handleRowClick"
              @selection-change="handleSelectionChange">
      <!--selection选择框-->
      <el-table-column v-if="options.selectType && options.selectType === 'checkbox'"
                       type="selection"
                       width="50"
                       align="center"></el-table-column>
      <!---序号-->
      <el-table-column v-if="options.showIndex"
                       label="序号"
                       type="index"
                       width="60"
                       align="center"></el-table-column>
      <!--数据列-->
      <template v-for="(column, index) in columns">
      <template v-if="column.scopedSlots">
        <el-table-column :key="index"
                         :prop="column.prop"
                         :label="column.label"
                         :align="column.align || 'left'"
                         :width="column.width">
          <template #default="scope">
            <slot :name="column.scopedSlots"
                  :index="scope.$index"
                  :row="scope.row">
            </slot>
          </template>
        </el-table-column>
      </template>
        <template v-else>
          <el-table-column :key="index"
                           :prop="column.prop"
                           :label="column.label"
                           :align="column.align || 'left'"
                           :width="column.width"
                           :fixed="column.fixed">
          </el-table-column>
        </template>
      </template>
     </el-table>
    {{dataSource.rows}}
    <!-- 分页 -->
    <div class="pagination"
         v-if="showPagination">
      <el-pagination v-if="dataSource.total"
                     background
                     :total="dataSource.total"
                     :page-sizes="[10, 15, 30, 50, 100]"
                     :page-size="pageInfo.pageSize"
                     :current-page.sync="pageInfo.currentPage"
                     layout="total, sizes, prev, pager, next, jumper"
                     @size-change="handlePageSizeChange"
                     @current-change="handlePageNoChange"
                     style="text-align: center"></el-pagination>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["rowSelected","rowClick"]);
const props = defineProps({
  dataSource: Object,
  showPagination: {
    type: Boolean,
    default: false,
  },
  options: {
    type: Object,
    default: {
      extHeight: 0,
      showIndex: false,
    },
  },
  columns: Array,
  fetch: Function,//获取数据的函数
  initFetch: {
    type: Boolean,
    default: true
  },
  pageInfo: {
    type: Object
  },
})


const radioSelectRowIndex = ref(null);

//顶部60，内容区域距高顶部20，内容上下内间距15*2分页区域高度46
const topHeight = 60 + 20 + 30 + 46;

const tableHeight = ref(
    props.options.tableHeight
        ? props.options.tableHeight
        : window.innerHeight - topHeight - props.options.extHeight
);

const dataTable = ref();

//行点击
const handleRowClick = row => {
  emit("rowClick", row);
}

//多选
  const handleSelectionChange = row => {
    emit("rowSelected", row);
  }

//切换每页大小
const handlePageSizeChange = size => {
  props.pageInfo.pageSize = size;
  props.pageInfo.currentPage = 1;
  props.fetch()
}

//切换页码
const handlePageNoChange = current => {
  props.pageInfo.currentPage = current
  props.fetch()
}

//设置行选中
const setCurrentRow =(rowKey,rowValue)=> {
  let row = props.dataSource.list.find(item => {
    return item[rowKey] === rowValue
  })

  dataTable.value.setCurrentRow(row);
}

//将子组件暴露出去，否则父组件无法调用
defineExpose({
    setCurrentRow,
})

//初始化
const init = () => {
  if (props.initFetch && props.fetch) {
    props.fetch();
  }
}
init();

</script>

<style lang="scss">
  .pagination {
    padding-top: 10px;
  }
  .el-pagination {
    justify-content: right;
  }

 /* .el-table {
    .warning-row {
      --el-table-tr-bg-color: var(--el-color-warning-light-9);
    }

    .success-row {
      --el-table-tr-bg-color: var(--el-color-success-light-9);
    }
  }*/

  //.el-table__body tr.current-row > td.el-table__cell {
  //  background-color: #e6f0f9;
  //}
  //.el-table {
  //  --el-table-tr-bg-color: var(--el-table-tr-bg-color)
  //}

  //.el-table__body tr:hover > td.el-table__cell {
  //  background-color: var(--el-table-tr-hover-bg-color) !important;
  //}

</style>