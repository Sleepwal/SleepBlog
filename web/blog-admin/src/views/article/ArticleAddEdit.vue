<template>
  <div class="article-add-edit-body">
    <el-scrollbar height="90vh">
      <el-input placeholder=”请输入文章标题” size="large"
                v-model="articleFormData.title">
        <template #prefix>标题：</template>
      </el-input>

      <el-divider></el-divider>

      <MdEditor v-model="articleFormData.content"
                @onUploadImg="onUploadImg"
                class="article-add-edit-content"/>


      <!--   知识库文章选项   -->
      <el-form :model="articleFormData"
               v-if="type === '1'"
               :rules="knowRules"
               ref="articleFormDataRef"
               label-width="100"
               class="article-add-edit-form p-5"
      >
        <!--     文档选择框     -->
        <el-form-item prop="categoryId" label="文档">
          <el-select v-model="articleFormData.documentId"
                     clearable placeholder="请选择文档">
            <el-option v-for="item in documentList"
                       :value="item.id" :label="item.title"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="summary" label="摘要">
          <el-input placeholder="请输入摘要" v-model="articleFormData.summary"
                    type="textarea" :autosize="{ minRows: 2, maxRows:4 }">
          </el-input>
        </el-form-item>

        <div class="rowCC">
          <el-button @click="submitArticle"
                     type="primary"
                     color="#626aef"
                     :disabled="articleFormData.title === '' || articleFormData.content === ''">
            提交
          </el-button>
        </div>
      </el-form>

      <!--   正常文章选项   -->
      <el-form :model="articleFormData"
               v-else
               :rules="rules"
               ref="articleFormDataRef"
               label-width="100"
               class="article-add-edit-form p-5">
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
              <el-radio :label="'1'">允许</el-radio>
              <el-radio :label="'0'">不允许</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
        <el-form-item prop="isTop" label="是否置顶">
          <el-radio-group v-model="articleFormData.isTop">
            <el-radio :label="'1'">是</el-radio>
            <el-radio :label="'0'">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item prop="tag" label="标签">
          <div class="tag-input-item rowCS">
            <div>
              <el-tag v-for="(item,index) in articleFormData.tags"
                      @close="closeTag(index)" closable
                      class="mr-2">
                {{item}}
              </el-tag>
            </div>
            <div class="info mr-2"
                 v-if="!showTagInput && (articleFormData.tags === undefined || articleFormData.tags.length === 0)">添加标签方便检索</div>
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


        <div class="rowCC">
          <el-button @click="submitArticle"
                     type="primary"
                     color="#626aef"
                     :disabled="articleFormData.title === '' || articleFormData.content === ''">
            提交
          </el-button>
        </div>

      </el-form>
    </el-scrollbar>

  </div>
</template>

<script setup>
import {addArticle, editArticleDetail, updateArticle} from "@/api/article";
import {useRoute} from "vue-router"
import {getAllCategoryList} from "@/api/category";
import {ElMessage} from "element-plus";
import router from "@/router";
import { addKnowArticle, getDocumentList, knowArticleDetail, updateKnowArticle } from "@/api/know";
import { MdEditor } from "md-editor-v3";
import 'md-editor-v3/lib/style.css';
import { uploadImg } from "@/api/upload";
const route = useRoute()
const editorHeight = window.innerHeight - 190

/**
 * type为'0'：正常文章,
 * type为'1'：知识库文章
 * @type {Ref<UnwrapRef<string | RouteParamValue[]>>}
 */
const type = ref(route.params.type)
/**
 * articleId为'-1': 添加文章,
 * articleId为'正数': 修改文章
 * @type {Ref<UnwrapRef<string | RouteParamValue[]>>}
 */
const articleId = ref(route.params.articleId)

//1. 编辑文章部分
const articleFormData = reactive({
  title: '',
  content: '',
  tags: []
})

/**
 * 上传图片
 * @param files
 * @param callback
 * @returns {Promise<void>}
 */
const onUploadImg = async (files, callback) => {
  let res = await uploadImg(files[0])
  // console.log([res])
  callback([res]);
};


//请求文章详情
const loadArticleDetail = async () => {
  if(articleId.value === '-1') //没有id就不用加载
    return

  if(type.value === '1') { //知识库文章
    const res = await knowArticleDetail(articleId.value)
    Object.assign(articleFormData, res)

  } else { //正常文章
    const res = await editArticleDetail(articleId.value)
    Object.assign(articleFormData, res)
  }

}
loadArticleDetail()

//表单校验
const rules = {
  title: [{ required: true, message: '请选择输入标题' }],
  content: [{ required: true, message: '请选择输入正文' }],
  categoryId: [{ required: true, message: '请选择分类' }],
  isComment: [{ required: true, message: '请选择是否允许评论' }],
  isTop: [{ required: true, message: '请选择是否允许置顶' }],
}

//知识库表单校验
const knowRules = {
  title: [{ required: true, message: '请选择输入标题' }],
  content: [{ required: true, message: '请选择输入正文' }],
  documentId: [{ required: true, message: '请选择分类' }],
}


//2. 填写设置对话框部分
//文章相关设置
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

const categoryList = ref() //文章分类
const documentList = ref() //文档
const loadCategoryList = async () => {
  categoryList.value = await getAllCategoryList()
  documentList.value = await getDocumentList()
}
loadCategoryList()

//删除标签
const closeTag = (index) => {
  articleFormData.tags.splice(index, 1)
}

//显示标签输入框
const showTagInput = ref(false)

const showTagInputHandler = () => {
  showTagInput.value = true
}

//标签
const tagValue = ref(null)

/**
 * 添加标签
 */
const addTag = () => {
  //为空
  if(tagValue.value === undefined) return

  //过滤重复标签
  if(articleFormData.tags.indexOf(tagValue.value) === -1) {
    articleFormData.tags.push(tagValue.value) //添加到表单中
  } else {
    ElMessage.error("标签名重复")
  }

  showTagInput.value = false
  tagValue.value = undefined //清除
}

//表单提交后校验
const articleFormDataRef = ref()

const submitArticle = () => {
  articleFormDataRef.value.validate(async valid => {
    if(!valid) return

    const data ={}
    Object.assign(data, articleFormData)
    if(articleId.value === '-1') { //没有id,添加文章
      if(type.value === '1') { //知识库文章
        await addKnowArticle(data)
        ElMessage.success('添加新文章成功！')
        await router.push('/knowledge/DocAndArticle')
      }
      else { //正常文章
        await addArticle(data)
        ElMessage.success('添加新文章成功！')
        await router.push('/article/manage')
      }
    }
    else { //有id,修改文章
      if(type.value === '1') { //知识库文章
        await updateKnowArticle(data)
        ElMessage.success('修改文章成功！')
        await router.push('/knowledge/DocAndArticle')
      }
      else { //正常文章
        await updateArticle(data)
        ElMessage.success('修改文章成功！')
        await router.push('/article/manage')
      }
    }

  })
}
</script>

<style lang="scss" scoped>

.article-add-edit-body {

  .article-add-edit-content {
    height: 75vh;
  }

  .article-add-edit-form {
    border: 1px solid black;
    margin: 1% 10%;
  }

}


</style>