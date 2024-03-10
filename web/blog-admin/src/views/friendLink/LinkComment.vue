<template>
  <div>
    <Table
      :columns="columns"
      :show-pagination="true"
      :data-source="tableData"
      :fetch="loadData"
      :options="tableOptions"
      :page-info="pageInfo"
    >
      <!-- 头像 -->
      <template #avatar="{ index, row }">
        <Cover :cover="row.avatar"></Cover>
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
            <el-popconfirm title="是否要删除该评论?" @confirm="del(row.id)">
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
        <el-form-item prop="nickname" label="评论者">
          <el-input placeholder="请输入评论者" v-model="formData.nickname" disabled></el-input>
        </el-form-item>

        <el-form-item prop="content" label="评论">
          <el-input placeholder="请输入评论" v-model="formData.content"></el-input>
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
import { addOrUpdateTag, deleteTag, getTagListPage } from '@/api/tag'
import { deleteComment, getCommentListPage, getLinkCommentListPage, updateComment } from "@/api/comment";

const basicStore = useBasicStore()

const columns = [
  {
    label: '头像',
    prop: 'avatar',
    width: 100,
    scopedSlots: 'avatar'
  },
  {
    label: '评论者',
    prop: 'nickname',
    width: 150
  },
  {
    label: '评论',
    prop: 'content'
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
  extHeight: 0
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

  let resp = await getLinkCommentListPage({
    pageNum: pageInfo.currentPage,
    size: pageInfo.pageSize
  })

  tableData.list = resp.rows
  tableData.total = resp.total
}

//新增、修改
const dialogConfig = reactive({
  show: false,
  title: '评论管理',
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
  name: '',
  remark: ''
}
const showEdit = (type, data) => {
  dialogConfig.show = true

  if (type === 'add') {
    dialogConfig.title = '添加评论'
    formDataRef.value.resetFields()
    Object.assign(formData, empty)
  } else if (type === 'update') {
    dialogConfig.title = '编辑评论'
    Object.assign(formData, data)
  }
}

const submitForm = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) return

    let params = {}
    Object.assign(params, formData)
    await updateComment(params)
    ElMessage.success('修改成功！')

    dialogConfig.show = false //对话框关闭
    await loadData() //重新加载数据
  })
}

//删除评论
const del = async (id) => {
  let resp = await deleteComment(id)
  ElMessage.success('删除成功!')
  await loadData() //重新加载数据
}
</script>

<style lang="scss" scoped></style>
