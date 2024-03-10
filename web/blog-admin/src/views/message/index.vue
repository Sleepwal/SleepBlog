<template>
  <div>
    <el-button type="primary" @click="showEdit('add')"
               class="m-2" icon="plus">留言</el-button>

    <Table :columns="columns" :show-pagination="true"
           :data-source="tableData" :fetch="loadData"
           :options="tableOptions" :page-info="pageInfo">

      <!--    时间    -->
      <template #time="{index,row}">
        <div>创建时间：{{row.createTime}}</div>
        <div v-if="row.updateTime">更新时间：{{row.updateTime}}</div>
      </template>

      <template #handle="{index, row}">
        <div class="handle ">
          <div>
            <el-button @click="showEdit('update', row)"
                       type="primary" text
                       :icon="Edit" >
              编辑</el-button>
          </div>

          <div>
            <el-popconfirm title="是否要删除该留言?" @confirm="del(row.id)">
              <template #reference>
                <el-button type="danger" text
                           :icon="Delete">删除</el-button>
              </template>
            </el-popconfirm>
          </div>
        </div>
      </template>
    </Table>

    <Dialog :show="dialogConfig.show" :title="dialogConfig.title"
            :buttons="dialogConfig.buttons"
            @close="dialogConfig.show=false">
      <el-form :model="formData" :rules="rules"
               ref="formDataRef" :label-width="80">
        <el-form-item prop="text" label="留言">
          <el-input placeholder="请输入留言"
                    v-model="formData.text">
          </el-input>
        </el-form-item>
      </el-form>
    </Dialog>
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

const basicStore = useBasicStore()

const columns = [
  {
    label: '昵称',
    prop: 'nickname',
    width: 200,
  },{
    label: '留言',
    prop: 'text',
  },{
    label: '时间',
    prop: 'time',
    width: 230,
    scopedSlots: "time",
  },{
    label: '操作',
    prop: 'handle',
    width: 150,
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

  let resp = await messageList({
    pageNum: pageInfo.currentPage,
    size: pageInfo.pageSize,
  })

  tableData.list = resp.rows
  tableData.total = resp.total
}

//新增、修改
const dialogConfig = reactive({
  show: false,
  title: "留言管理",
  buttons: [
    {
      type: "danger",
      text: "确定",
      click: e => {
        submitForm()
      }
    },
  ]
})

const formData = reactive({})
const rules = {
  text: [{required: true, message: '请输入留言'}],
}
const formDataRef = ref()

const showEdit = (type, data) => {
  dialogConfig.show = true
  //清空formData
  Object.keys(formData).map(key => {
    delete formData[key]
  })

  if(type === 'add') {
    dialogConfig.title = "添加留言"
    formDataRef.value.resetFields()
    Object.assign(formData, {
      text: ''
    })

  } else if(type === 'update') {
    dialogConfig.title = "编辑留言"
    Object.assign(formData, data)
  }
}

const submitForm = () => {
  formDataRef.value.validate(async valid => {
    if(!valid) return

    let params = {}
    Object.assign(params, formData) //设置参数
    await addOrUpdateMessage(params)
    ElMessage.success("修改成功！")

    dialogConfig.show = false //对话框关闭
    await loadData() //重新加载数据
  })
}

//删除留言
const del = async (id) => {
  let resp = await deleteMessage(id)
  ElMessage.success("删除成功!")
  await loadData() //重新加载数据
}

</script>

<style lang="scss" scoped>

</style>