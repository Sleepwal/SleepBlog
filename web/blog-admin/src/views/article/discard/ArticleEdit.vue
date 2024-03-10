<template>
  <div>
    <Window :buttons="windowConfig.buttons" @close="closeWindow"
            :show="windowConfig.show">
      <el-form :model="articleFormData"
               :rules="rules"
               ref="articleFormDataRef">
        <!--    标题    -->
        <el-form-item prop="title">
          <div class="title-input">
            <el-input placeholder=”请输入博客标题”
                      v-model="articleFormData.title"></el-input>
          </div>
        </el-form-item>
        <!--   正文     -->
        <el-form-item prop="content">
          <div :style="{'width': '100%'}">
            <MarkDownEdit v-model="articleFormData.content"
                          :height="editorHeight"></MarkDownEdit>
          </div>
        </el-form-item>
      </el-form>

      <Dialog :show="dialogConfig.show" :title="dialogConfig.title"
              :buttons="dialogConfig.buttons" :width="'600'"
              @close="dialogConfig.show=false">
        <el-form :model="articleFormData" :rules="rules"
                 ref="articleFormDataRef" label-width="70"
                 class="article-setting-form">
          <!--     分类选择框     -->
          <el-form-item prop="categoryId" label="分类">
            <el-select v-model="articleFormData.categoryId"
                       clearable placeholder="请选择分类">
              <el-option v-for="item in categoryList"
                         :value="item.id" :label="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="thumbnail" label="封面">
            <ImgUpload v-model="articleFormData.thumbnail"></ImgUpload>
          </el-form-item>
          <el-form-item prop="summary" label="摘要">
            <el-input placeholder="请输入摘要" v-model="articleFormData.summary"
                      type="textarea" :autosize="{ minRows: 2, maxRows:4 }">
            </el-input>
          </el-form-item>
          <el-form-item prop="isComment" label="评论">
            <div class="articleFormData.isComment">
              <el-radio-group v-model="articleFormData.isComment">
                <el-radio :label="1">允许</el-radio>
                <el-radio :label="0">不允许</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item prop="isTop" label="是否置顶">
            <el-radio-group v-model="articleFormData.isTop">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="isAuto" label="轮播图">
            <el-radio-group v-model="articleFormData.isAuto">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="tag" label="标签">
            <div class="tag-input-item rowCS">
              <div>
                <el-tag v-for="(item,index) in articleFormData.tag"
                        @close="closeTag(index)" closable
                        class="mr-2">
                    {{item}}
                </el-tag>
              </div>
              <div class="info mr-2"
                    v-if="!showTagInput && (articleFormData.tag === undefined || articleFormData.tag.length === 0)">添加标签方便检索</div>
              <div class="add">
                <el-icon color="red"
                         @click="showTagInputHandler"
                         v-if="!showTagInput"><Plus /></el-icon>
              </div>
              <el-input class="tag-input"
                        v-if="showTagInput"
                        v-model="tagValue"
                        @change="addTag"></el-input>
            </div>

          </el-form-item>
        </el-form>
      </Dialog>
    </Window>
  </div>
</template>

<script setup>
import {ElMessage} from "element-plus";
import {getAllCategoryList} from "@/api/category";
import {addArticle, articleAutoSave, editArticleDetail} from "@/api/article";

const initArticleEdit = (type, data) => {

  windowConfig.show = true
  nextTick(() => {
    //清空
    articleFormDataRef.value.resetFields()
    articleFormData.value = { tag: [] }

    if(type === 0) {
      windowConfig.title = '新增文章'
    } else {
      loadArticleDetail(data.id)
    }
  })
}

defineExpose({ initArticleEdit })

//加载文章详情，修改用
const loadArticleDetail = async (id) => {
  articleFormData.value = await editArticleDetail(id)
  //后端数据是字符串，转换成int
  articleFormData.value.isAuto = parseInt(articleFormData.value.isAuto)
  articleFormData.value.isTop = parseInt(articleFormData.value.isTop)
  articleFormData.value.isComment = parseInt(articleFormData.value.isComment)

  //todo tag后端数据转换成数组
  if(articleFormData.value.tag) {
    articleFormData.value.tag = articleFormData.value.tag.split('|')
  } else {
    articleFormData.value.tag = []
  }
}

//自动保存
const autoSave = () => {
  //为空不保存
  if((articleFormData.value.title === "" && articleFormData.value.content === "")
      || time.value === null || dialogConfig.show)
    return

  const data ={}
  Object.assign(data, articleFormData.value)
  articleAutoSave(data).then(res => {
    articleFormData.value.id = res
    ElMessage("自动保存成功！")
  })
}
//执行自动保存
let time = ref(null)
const startTime = () => {
  time.value = setInterval(() => {
    autoSave()
  }, 1000 * 60 * 10)
}
const clearTime = () => {
  if(time.value !== null) {
    clearInterval(time.value)
    time.value = null
  }
}


//新增、修改
const windowConfig = reactive({
  show: false,
  title: "分类管理对话框",
  buttons: [
    {
      type: "danger",
      text: "确定",
      click: e => {
        showSetting()
      }
    },
  ]
})

//关闭窗口，加载新数据
const emit = defineEmits(['callback'])
const closeWindow = () => {
  windowConfig.show = false
  emit('callback')
  if(time.value != null)
    clearInterval(time.value)
}

const showEdit = (type, data) => {
  windowConfig.show = true
}

const editorHeight = window.innerHeight - 210

//1. 编辑文章部分
const mdContent = ref('# 初始化内容')

const rules = {
  title: [{ required: true, message: '请选择输入标题' }],
  content: [{ required: true, message: '请选择输入正文' }],
  categoryId: [{ required: true, message: '请选择分类' }],
  isComment: [{ required: true, message: '请选择是否允许评论' }],
  isTop: [{ required: true, message: '请选择是否允许置顶' }],
  isAuto: [{ required: true, message: '请选择是否设置为轮播图' }],
}


//2. 填写设置对话框部分
//文章相关设置
const articleFormData = ref({
  // categoryId: '',
  // thumbnail: '',
  // summary: '',
  // isComment: '',
  // isTop: '',
  // isAuto: '',
  tag: []
})

const dialogConfig = reactive({
  show: false,
  title: "文章设置",
  buttons: [{
      type: "danger",
      text: "确定",
      click: e => {
        submitArticle();
      }
    },]
})

//文章分类
const categoryList = ref()
const loadCategoryList = async () => {
  categoryList.value = await getAllCategoryList()
}

loadCategoryList()

const showSetting = () => {
  dialogConfig.show = true
}

//删除标签
const closeTag = (index) => {
  articleFormData.value.tag.splice(index, 1)
}

//显示标签输入框
const showTagInput = ref(false)

const showTagInputHandler = () => {
  showTagInput.value = true
}

//tag值
const tagValue = ref(null)

const addTag = () => {
  //为空
  if(tagValue.value === undefined) return

  //过滤重复标签
  if(articleFormData.value.tag.indexOf(tagValue.value) === -1) {
    articleFormData.value.tag.push(tagValue.value) //添加到表单中
  } else {
    ElMessage.error("标签名重复")
  }

  showTagInput.value = false
  tagValue.value = undefined //清除
}

const articleFormDataRef = ref({})

const submitArticle = () => {
  articleFormDataRef.value.validate(async valid => {
    if(!valid) return

    const data ={}
    Object.assign(data, articleFormData.value)
    await addArticle(data)
    ElMessage.success('添加新文章成功！')
    dialogConfig.show = false
    closeWindow()
  })
}


</script>

<style lang="scss">
.title-input {
  width: 100%;
}

.article-setting-form {
  .isComment {
  }

  .tag-input-item {
    .info {
      color: #bbb8b8;
    }
    .tag-input {
      width: 120px;
    }
    .add:hover {
      cursor: pointer;
    }
  }
}

</style>