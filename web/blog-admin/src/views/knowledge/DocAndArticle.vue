<template>
  <div class="doc-art-body">
    <!-- 添加按钮 -->
    <div class="rowBC">
      <el-button type="primary" @click="showEdit('add')" class="m-2" icon="plus">文档</el-button>
      <el-button type="primary" @click="editArticle('add')" class="m-2" icon="plus">文章</el-button>
    </div>

    <el-row :gutter="5">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-head">
              <span>文档</span>
            </div>
          </template>
          <Table
            :columns="columns"
            :show-pagination="false"
            :data-source="tableData"
            :fetch="loadData"
            :options="tableOptions"
            @rowClick="rowClick"
          >
            <template #handle="{ index, row }">
              <div class="handle">
                <div>
                  <el-button @click="showEdit('update', row)" type="primary" :icon="Edit" text>编辑</el-button>
                </div>

                <div>
                  <el-popconfirm title="是否要删除该分类?" @confirm="del(row.id)">
                    <template #reference>
                      <el-button type="danger" :icon="Delete" text>删除</el-button>
                    </template>
                  </el-popconfirm>
                </div>
              </div>
            </template>
          </Table>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-head">
              <span>文档文章</span>
            </div>
          </template>
            <el-table :data="knowArticleList"
                      height="540"
                      border>
              <el-table-column prop="title" label="标题" width="200" />
              <el-table-column prop="summary" label="描述" width="300" />

              <el-table-column label="操作" width="100">
                <template #default="scope">
                  <!-- 按钮 -->
                  <div>
                    <div class="rowCC">
                      <el-button @click="showDetail(scope.row.id)" type="success" :icon="View" text>预览</el-button>
                    </div>

                    <div class="rowCC">
                      <el-button @click="editArticle('edit', scope.row)" type="primary" :icon="Edit" text>编辑</el-button>
                    </div>

                    <div class="rowCC">
                      <el-popconfirm title="是否要删除该文章?" @confirm="delArticle(scope.row.id, scope.row.documentId)">
                        <template #reference>
                          <el-button type="danger" :icon="Delete" text>删除</el-button>
                        </template>
                      </el-popconfirm>
                    </div>
                  </div>
                </template>

              </el-table-column>
            </el-table>

<!--            <template v-for="item in knowArticleList">

              <div class="article-list-item rowBC">
                {{ item.title }}

                <el-divider direction="vertical"></el-divider>
                {{item.summary}}

                &lt;!&ndash; 按钮 &ndash;&gt;
                <div>
                  <div>
                    <el-button @click="showDetail(item.id)" type="success" :icon="View" text>预览</el-button>
                  </div>

                  <div>
                    <el-button @click="editArticle('edit', item)" type="primary" :icon="Edit" text>编辑</el-button>
                  </div>

                  <div>
                    <el-popconfirm title="是否要删除该分类?" @confirm="delArticle(item.id)">
                      <template #reference>
                        <el-button type="danger" :icon="Delete" text>删除</el-button>
                      </template>
                    </el-popconfirm>
                  </div>
                </div>
              </div>
              <el-divider></el-divider>
            </template>-->
        </el-card>
      </el-col>
    </el-row>

    <Dialog
      :show="dialogConfig.show"
      :title="dialogConfig.title"
      :buttons="dialogConfig.buttons"
      @close="dialogConfig.show = false"
    >
      <el-form :model="formData" :rules="rules" ref="formDataRef" label-width="70">
        <el-form-item prop="title" label="文档名称">
          <el-input placeholder="请输入文档名称" v-model="formData.title"></el-input>
        </el-form-item>

        <el-form-item prop="categoryId" label="文档分类">
          <el-select v-model="formData.categoryId" clearable placeholder="请选择分类">
            <el-option v-for="item in knowCateList"
                       :label="item.title"
                       :value="item.id"
                       :key="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="description" label="文档简介">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 5 }"
            placeholder="请输入文档简介"
            v-model="formData.description"
          ></el-input>
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup>
import { Delete, Edit, View } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import {
  addOrUpdateKnowDocument, deleteKnowArticle,
  deleteKnowDocument,
  getAllKnowCate,
  getArticleList,
  getDocumentList
} from "@/api/know";
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()

const columns = [
  {
    label: '名称',
    prop: 'title',
    width: 150
  },
  {
    label: '描述',
    prop: 'description'
  },
  {
    label: '文章数量',
    prop: 'value',
    width: 90
  },
  {
    label: '操作',
    prop: 'handle',
    width: 150,
    scopedSlots: 'handle'
  }
]

const tableData = reactive({})
const knowCateList = ref()
const tableOptions = {
  extHeight: 100
}

const loadData = async () => {
  tableData.list = await getDocumentList()
  knowCateList.value = await getAllKnowCate()
}

//新增、修改
const dialogConfig = reactive({
  show: false,
  title: '分类管理对话框',
  buttons: [
    {
      type: 'danger',
      text: '确定',
      click: (e) => {
        console.log('确定')
        submitForm()
      }
    }
  ]
})

const formData = reactive({})
const rules = {
  title: [{ required: true, message: '请输入文档名' }],
  categoryId: [{ required: true, message: '请选择分类' }]
}
const formDataRef = ref()

const empty = {
  categoryId: "",
  description: "",
  id: "",
  title: "",
}
const showEdit = (type, data) => {
  dialogConfig.show = true
  if (type === 'add') {
    dialogConfig.title = '添加分类'
    formDataRef.value.resetFields()
    Object.assign(formData, empty)

  } else if (type === 'update') {
    dialogConfig.title = '编辑分类'
    Object.assign(formData, data)
  }
}

const submitForm = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) return

    let params = {}
    Object.assign(params, formData) //设置参数
    let resp = await addOrUpdateKnowDocument(params)
    ElMessage.success(resp)
    dialogConfig.show = false //对话框关闭
    await loadData() //重新加载数据
  })
}

//删除分类
const del = async (id) => {
  await deleteKnowDocument(id)
  ElMessage.success('删除成功！')
  await loadData() //重新加载数据
}

//文档文章

const rowClick = (row) => {
  loadArticleList(row.id)
}

//获取文档文章
const knowArticleList = ref()
const loadArticleList = async (documentId) => {
  knowArticleList.value = await getArticleList(documentId)
}
loadArticleList(1)

const editArticle = (type, data) => {
  router.push('/article/edit/' + (type === 'add' ? -1 : data.id) + '/' + 1)
}

const showDetail = (articleId) => {
  router.push('/article/view/' + articleId + '/' + 1)
}

const delArticle = async (articleId, documentId) => {
  await deleteKnowArticle(articleId)
  await loadArticleList(documentId)
  ElMessage.success('删除文章成功!')
}
</script>

<style lang="scss">
.doc-art-body {
  .article-list-item {
    margin: 5px 0;
  }
}
</style>
