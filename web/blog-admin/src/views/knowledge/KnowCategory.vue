<template>
  <div>
    <el-button type="primary" @click="showEdit('add')" class="m-2" icon="plus">分类</el-button>
    <Table :columns="columns" :show-pagination="false"
           :data-source="tableData" :fetch="loadData"
           :options="tableOptions">
      <template #handle="{index, row}">
        <div class="handle ">
          <div>
            <el-button @click="showEdit('update', row)" type="primary" :icon="Edit" text >编辑</el-button>
          </div>

          <div>
            <el-popconfirm title="是否要删除该分类?" @confirm="del(row.id)">
              <template #reference>
                <el-button type="danger" :icon="Delete" text>删除</el-button>
              </template>
            </el-popconfirm>
          </div>

<!--          <a href="javascript:void(0)"
             :class="[index===0?'not-allow':'']"
             @click="changeSort(index,'up')"><el-icon><ArrowUpBold /></el-icon></a>
          <el-divider direction="vertical" />
          <a href="javascript:void(0)"
             :class="[index===tableData.list.length - 1?'not-allow':'']"
             @click="changeSort(index,'down')"><el-icon><ArrowDownBold /></el-icon></a>-->
        </div>
      </template>
    </Table>

    <Dialog :show="dialogConfig.show" :title="dialogConfig.title"
            :buttons="dialogConfig.buttons"
            @close="dialogConfig.show=false">
      <el-form :model="formData" :rules="rules"
               ref="formDataRef">
        <el-form-item prop="title" label="分类名称">
          <el-input placeholder="请输入分类名称"
                    v-model="formData.title">
          </el-input>
        </el-form-item>
        <el-form-item prop="description" label="分类简介">
          <el-input type="textarea" :autosize="{minRows: 2, maxRows: 5}"
          placeholder="请输入分类简介"
          v-model="formData.description">
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
import {addOrUpdateKnowCategory, deleteKnowCategory, getAllKnowCate} from "@/api/know";

const columns = [
  {
    label: '名称',
    prop: 'title',
    width: 200,
  },{
    label: '描述',
    prop: 'description',
  },{
    label: '文章数量',
    prop: 'value',
    width: 150,
  },{
    label: '操作',
    prop: 'handle',
    width: 150,
    scopedSlots: "handle",
  },
]

const tableData = reactive({})
const tableOptions = {
  extHeight: 10,
}

const loadData = async () => {
  tableData.list = await getAllKnowCate()
}

//新增、修改
const dialogConfig = reactive({
  show: false,
  title: "分类管理对话框",
  buttons: [
    {
      type: "danger",
      text: "确定",
      click: e => {
        console.log("确定")
        submitForm()
      }
    },
  ]
})

const formData = reactive({})
const rules = {
  title: [{required: true, message: '请输入分类名'}],
}
const formDataRef = ref()

const showEdit = (type, data) => {
  dialogConfig.show = true
  if(type === 'add') {
    dialogConfig.title = "添加分类"
    formDataRef.value.resetFields()
  } else if(type === 'update') {
    dialogConfig.title = "编辑分类"
    Object.assign(formData, data)
  }
}

const submitForm = () => {
  formDataRef.value.validate(async valid => {
    if(!valid) return

    let params = {}
    Object.assign(params, formData) //设置参数
    let resp = await addOrUpdateKnowCategory(params)
    ElMessage.success(resp)
    dialogConfig.show = false //对话框关闭
    await loadData() //重新加载数据
  })

}

//删除分类
const del = async (id) => {
  let resp = await deleteKnowCategory(id)
  ElMessage.success(resp)
  await loadData() //重新加载数据
}

//修改排序
const changeSort = (index, type) => {
  let categoryList = tableData.list
  if(type === 'down' && index === categoryList.length - 1 || type === 'up' && index === 0)
    return

  let num = type === 'down' ? 1 : -1
  let tmp = categoryList[index]
  categoryList.splice(index, 1)
  categoryList.splice(index + num, 0, tmp)

  console.log(JSON.stringify(categoryList))
}

</script>

<style lang="scss">

.not-allow {
  color: wheat;
}
.not-allow:hover {
  cursor: not-allowed;
  color: wheat;
}
</style>