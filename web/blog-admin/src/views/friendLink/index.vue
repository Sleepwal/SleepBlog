<template>
  <div>
    <el-button type="primary" @click="showEdit('add')" class="m-2" icon="plus">友链</el-button>

    <Table
      :columns="columns"
      :show-pagination="true"
      :data-source="tableData"
      :fetch="loadData"
      :options="tableOptions"
      :page-info="pageInfo"
    >
      <!-- 封面 -->
      <template #logo="{ index, row }">
        <Cover :cover="row.logo"></Cover>
      </template>

      <!--    状态    -->
      <template #statusName="{ index, row }">
        <span v-if="row.status === '0'"><el-tag type="success">审核通过</el-tag></span>
        <span v-if="row.status === '1'"><el-tag type="danger">审核未通过</el-tag></span>
        <span v-if="row.status === '2'"><el-tag type="warning">未审核</el-tag></span>
      </template>

      <!--    时间    -->
      <template #time="{ index, row }">
        <div>创建时间：{{ row.createTime }}</div>
        <div v-if="row.updateTime">更新时间：{{ row.updateTime }}</div>
      </template>

      <template #handle="{ index, row }">
        <div class="handle">
          <div>
            <el-button @click="showEdit('update', row)" type="primary" text :icon="Edit">编辑</el-button>
          </div>

          <div>
            <el-popconfirm title="是否要删除该友链?" @confirm="del(row.id)">
              <template #reference>
                <el-button type="danger" text :icon="Delete">删除</el-button>
              </template>
            </el-popconfirm>
          </div>
        </div>
      </template>
    </Table>

    <Dialog
      :show="dialogConfig.show"
      :title="dialogConfig.title"
      :buttons="dialogConfig.buttons"
      @close="dialogConfig.show = false"
    >
      <el-form :model="formData" :rules="rules" ref="formDataRef" :label-width="80">
        <el-form-item prop="logo" label="logo">
          <el-input placeholder="请输入外链" v-model="formData.logo"></el-input>
        </el-form-item>

        <el-form-item prop="name" label="网站名">
          <el-input placeholder="请输入网站名" v-model="formData.name"></el-input>
        </el-form-item>

        <el-form-item prop="description" label="描述">
          <el-input placeholder="请输入网站描述" v-model="formData.description"></el-input>
        </el-form-item>

        <el-form-item prop="address" label="链接">
          <el-input placeholder="请输入链接" v-model="formData.address"></el-input>
        </el-form-item>

        <el-form-item prop="status" label="状态">
          <el-select v-model="formData.status" clearable placeholder="请选择友链状态">
            <el-option value="0" label="正常"></el-option>
            <el-option value="1" label="审核未通过"></el-option>
            <el-option value="2" label="未审核"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup>
import { Delete, Edit } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { useBasicStore } from '@/store/basic'
import { addOrUpdateLink, deleteLink, getLinkListByPage } from '@/api/link'

const basicStore = useBasicStore()

const columns = [
  {
    label: 'logo',
    prop: 'logo',
    width: 200,
    scopedSlots: 'logo'
  },
  {
    label: '网站名',
    prop: 'name',
    width: 130
  },
  {
    label: '描述',
    prop: 'description',
    width: 200
  },
  {
    label: '链接',
    prop: 'address'
  },
  {
    label: '状态',
    prop: 'statusName',
    width: 105,
    scopedSlots: 'statusName'
  },
  {
    label: '时间',
    prop: 'time',
    width: 230,
    scopedSlots: 'time'
  },
  {
    label: '操作',
    prop: 'handle',
    width: 130,
    scopedSlots: 'handle'
  }
]

const tableData = reactive({})
const tableOptions = {
  extHeight: 50
}

const pageInfo = reactive({
  currentPage: 1,
  pageSize: 10
})

const loadData = async () => {
  //从内存拿出当前页数
  let c = basicStore.getCurrentPage()
  if (c !== undefined) {
    pageInfo.currentPage = c
    basicStore.setCurrentPage(undefined) //重置
  }

  let resp = await getLinkListByPage({
    pageNum: pageInfo.currentPage,
    size: pageInfo.pageSize
  })

  tableData.list = resp.rows
  tableData.total = resp.total
}

//新增、修改
const dialogConfig = reactive({
  show: false,
  title: '友链管理',
  buttons: [
    {
      type: 'danger',
      text: '确定',
      click: (e) => {
        submitForm()
      }
    }
  ]
})

const formData = reactive({})
const rules = {
  logo: [{ required: true, message: '请输入' + 'logo' }],
  name: [{ required: true, message: '请输入' + '网站名' }],
  address: [{ required: true, message: '请输入' + '链接' }],
  status: [{ required: true, message: '请输入' + '状态' }]
}
const formDataRef = ref()

const empty = {
  id: '',
  logo: '',
  name: '',
  description: '',
  address: '',
  status: ''
}
const showEdit = (type, data) => {
  dialogConfig.show = true
  //清空formData
  Object.keys(formData).map((key) => {
    delete formData[key]
  })

  if (type === 'add') {
    dialogConfig.title = '添加友链'
    formDataRef.value.resetFields()
    Object.assign(formData, empty)
  } else if (type === 'update') {
    dialogConfig.title = '编辑友链'
    Object.assign(formData, data)
  }
}

const submitForm = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) return

    let params = {}
    Object.assign(params, formData)
    await addOrUpdateLink(params)
    ElMessage.success('修改成功！')

    dialogConfig.show = false //对话框关闭
    await loadData() //重新加载数据
  })
}

//删除友链
const del = async (id) => {
  let resp = await deleteLink(id)
  ElMessage.success('删除成功!')
  await loadData() //重新加载数据
}
</script>

<style lang="scss" scoped></style>
