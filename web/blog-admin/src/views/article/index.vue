<template>
  <div>
    <div class="top-search">
      <el-form :model="searchFormData">
        <el-row :gutter="50">
          <el-col :span="6">
            <el-form-item prop="title" label="标题">
              <el-input
                placeholder="请输入标题"
                v-model="searchFormData.title"
                clearable
                @keyup.enter.nativ="loadData"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item prop="status" label="状态">
              <el-select v-model="searchFormData.status" clearable placeholder="选择状态">
                <el-option :value="0" label="已发布"></el-option>
                <el-option :value="1" label="草稿"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item prop="category" label="分类">
              <el-select v-model="searchFormData.categoryId" clearable placeholder="选择分类">
                <el-option v-for="item in categoryList" :value="item.id" :label="item.name"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-button type="success" icon="search" @click="loadData">搜索</el-button>
          </el-col>
        </el-row>
<!--        <el-row>-->
<!--          <el-button type="primary" @click="showEdit(0, undefined)" icon="plus">新增文章</el-button>-->
<!--        </el-row>-->
      </el-form>
    </div>
    <div>
      <Table
        :columns="columns"
        :show-pagination="true"
        :data-source="tableData"
        :fetch="loadData"
        :options="tableOptions"
        :pageInfo="pageInfo"
      >
        <!-- 封面 -->
        <template #thumbnail="{ index, row }">
          <Cover :cover="row.thumbnail"></Cover>
        </template>

        <!--   文章信息     -->
        <template #articleInfo="{ index, row }">
          <div>分类： {{ row.categoryName }}</div>

          <div>
            标签：
            <el-tag v-for="tag in row.tagNames"
                    class="mr-2px">{{ tag }}</el-tag>
          </div>

          <div>浏览量：{{ row.viewCount }}</div>
          <div>
            <!--            <span v-if="row.isAuto === '0'" class="mr-2" :style="{ color: 'blue' }">首页轮播图</span>-->
            <span v-if="row.isTop === '0'" class="mr-2" :style="{ color: 'blue' }">置顶</span>
          </div>
          <div>
            <span v-if="row.isComment === '1'" :style="{ color: 'blue' }">允许评论</span>
          </div>
        </template>

        <!--    状态    -->
        <template #statusName="{ index, row }">
          <span v-if="row.status === '0'"><el-tag type="success">已发布</el-tag></span>
          <span v-if="row.status === '1'"><el-tag type="warning">已草稿</el-tag></span>
        </template>
        <!--    时间    -->
        <template #time="{ index, row }">
          <div>创建：{{ row.createTime }}</div>
          <div>更新：{{ row.updateTime }}</div>
        </template>
        <!--    操作    -->
        <template #handle="{ index, row }">
          <div class="handle">
            <div>
              <!-- 添加 -->
              <el-button @click="showEdit(1, row)" type="primary" :icon="Edit" text>编辑</el-button>
            </div>

            <div>
              <!-- 删除 -->
              <el-popconfirm title="是否要删除该分类?" @confirm="delArticle(row.id)">
                <template #reference>
                  <el-button type="danger" :icon="Delete" text>删除</el-button>
                </template>
              </el-popconfirm>
            </div>

            <div>
              <!-- 预览 -->
              <el-button @click="showView(row.id)" type="success" :icon="View" text>预览</el-button>
            </div>
          </div>
        </template>
      </Table>
    </div>
  </div>
</template>

<script setup>
import { Delete, Edit, View } from '@element-plus/icons-vue'
import { getCategoryList } from '@/api/category'
import { deleteArticle, getArticleSearchList } from '@/api/article'
import { useRouter, useRoute } from 'vue-router'
import { useBasicStore } from '@/store/basic'
import { ElMessage } from 'element-plus'

const basicStore = useBasicStore()
const router = useRouter()
const route = useRoute()

//搜索栏
const searchFormData = reactive({})
const categoryList = ref([])
const loadCategoryList = async () => {
  categoryList.value = await getCategoryList()
}
loadCategoryList()

//文章列表
const columns = [
  {
    label: '封面',
    prop: 'thumbnail',
    width: 65,
    scopedSlots: 'thumbnail'
  },
  {
    label: '标题',
    prop: 'title',
    width: 140
  },
  {
    label: '文章摘要',
    prop: 'summary'
  },
  {
    label: '文章状态',
    prop: 'statusName',
    width: 120,
    scopedSlots: 'statusName'
  },
  {
    label: '文章信息',
    prop: 'articleInfo',
    width: 170,
    scopedSlots: 'articleInfo'
  },
  {
    label: '时间',
    prop: 'time',
    width: 100,
    scopedSlots: 'time'
  },
  {
    label: '操作',
    prop: 'handle',
    width: 140,
    scopedSlots: 'handle'
  }
]

const tableData = reactive({})
const tableOptions = {
  extHeight: 70
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

  let resp = await getArticleSearchList({
    pageNum: pageInfo.currentPage,
    size: pageInfo.pageSize,
    title: searchFormData.title,
    status: searchFormData.status,
    categoryId: searchFormData.categoryId
  })

  tableData.list = resp.rows
  tableData.total = resp.total
}

//新增、修改
const articleEditRef = ref(null)
//type为0：新增文章
//type为1：修改文章
const showEdit = (type, data) => {
  //存储当前页数
  basicStore.setCurrentPage(pageInfo.currentPage)
  //跳转
  router.push('edit/' + (type === 0 ? -1 : data.id) + '/' + 0)
}

const showView = (data) => {
  //存储当前页数
  basicStore.setCurrentPage(pageInfo.currentPage)
  router.push('view/' + data + '/' + 0)
}

//删除
const delArticle = async (articleId) => {
  let res = await deleteArticle(articleId)
  await loadData()
  ElMessage.success('删除文章成功！')
}
</script>

<style lang="scss">
.mr-2px {
  margin-right: 2px;
}
</style>
