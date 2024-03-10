<template>
  <div>
    <Table :columns="columns" :show-pagination="true"
           :data-source="tableData" :fetch="loadData"
           :options="tableOptions" :page-info="pageInfo">
      <template #user="{index, row}">
        <el-tag
          :type="row.userType === '管理员' ? 'danger' : ''"
          size="large">
          {{row.userType}}
        </el-tag>
      </template>

      <template #request="{index, row}">
        <el-tag v-if="row.requestType === 'GET'">
          {{row.requestType}}
        </el-tag>

        <el-tag type="success" v-if="row.requestType === 'POST'">
          {{row.requestType}}
        </el-tag>

        <el-tag type="warning" v-if="row.requestType === 'PUT'">
          {{row.requestType}}
        </el-tag>

        <el-tag type="danger" v-if="row.requestType === 'DELETE'">
          {{row.requestType}}
        </el-tag>
      </template>

      <template #handle="{index, row}">
        <div class="rowCC">
          <el-popconfirm title="是否要删除该日志?" @confirm="del(row.id)">
            <template #reference>
              <el-button type="danger" text
                         :icon="Delete">删除</el-button>
            </template>
          </el-popconfirm>
        </div>
      </template>
    </Table>
  </div>
</template>

<script setup>
import {
  Delete,
  Edit,
} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import {delUser, updateUser, userList} from "@/api/system/user";
import {addOrUpdateMessage, deleteMessage, messageList} from "@/api/message";
import {useBasicStore} from "@/store/basic";
import { deleteSystemLog, getSystemLogList } from "@/api/systemLog";

const basicStore = useBasicStore()

const columns = [
  {
    label: '用户类型',
    prop: 'user',
    width: 98,
    scopedSlots: "user",
  },{
    label: '描述',
    prop: 'description',
    width: 150,
  },{
    label: '请求类型',
    prop: 'request',
    scopedSlots: "request",
    width: 70,
  },{
    label: 'IP地址',
    prop: 'ip',
    width: 100,
  },{
    label: 'URL',
    prop: 'url',
  },{
    label: '调用方法',
    prop: 'method',
    width: 140,
  },{
    label: '参数',
    prop: 'parameter',
    width: 80,
  },{
    label: '时间',
    prop: 'createTime',
    width: 160,
  },{
    label: '操作',
    prop: 'handle',
    width: 100,
    scopedSlots: "handle",
  },
]

const tableData = reactive({})
const tableOptions = {
  extHeight: 50,
}

const pageInfo = reactive({
  currentPage: 1,
  pageSize: 10
})

const loadData = async () => {
  //从内存拿出当前页数
  let c = basicStore.getCurrentPage()
  if(c !== undefined) {
    pageInfo.currentPage = c
    basicStore.setCurrentPage(undefined) //重置
  }

  let resp = await getSystemLogList({
    pageNum: pageInfo.currentPage,
    size: pageInfo.pageSize,
  })

  tableData.list = resp.rows
  tableData.total = resp.total
}

//删除日志
const del = async (id) => {
  let resp = await deleteSystemLog(id)
  ElMessage.success("删除成功!")
  await loadData() //重新加载数据
}

</script>

<style lang="scss" scoped>

</style>