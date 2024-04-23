<template>
  <div class="commit-body">
    <el-scrollbar height="90vh">
      <el-input v-model="article.title"
                placeholder="请输入标题"
                size="large"></el-input>

      <div class="commit-content">
        <MdEditor v-model="article.content"
                  @onUploadImg="onUploadImg"
                  :theme="theme"
                  class="content-editor"/>
      </div>
      <el-divider></el-divider>

      <h2 style="text-align: center">发布设置</h2>

      <el-form :model="article"
               :rules="rules"
               ref="formRef"
               label-width="100"
               class="commit-body-form p-5">

        <!--     分类选择框     -->
        <el-form-item prop="categoryId" label="分类">
          <el-select v-model="article.categoryId"
                     clearable placeholder="请选择分类">
            <el-option v-for="item in categoryList"
                       :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="thumbnail" label="封面">
          <ImgUpload v-model="article.thumbnail"></ImgUpload>
        </el-form-item>

        <el-form-item prop="summary" label="摘要">
          <el-input placeholder="请输入摘要" v-model="article.summary"
                    type="textarea" :autosize="{ minRows: 2, maxRows:4 }">
          </el-input>
        </el-form-item>

        <el-form-item prop="isComment" label="是否评论">
          <div class="article.isComment">
            <el-radio-group v-model="article.isComment">
              <el-radio :label="1">允许</el-radio>
              <el-radio :label="0">不允许</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>

        <el-form-item prop="isTop" label="是否置顶">
          <el-radio-group v-model="article.isTop">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
       
        <el-form-item prop="tag" label="标签">
          <div class="tag-input-item rowCS">
            <div>
              <el-tag v-for="(item,index) in article.tags"
                      @close="closeTag(index)" closable
                      class="mr-2">
                {{item}}
              </el-tag>
            </div>
            <div class="info mr-2"
                 v-if="!showTagInput && (article.tags === undefined || article.tags.length === 0)">添加标签方便检索</div>
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
          <el-button @click="commitArticle"
                     v-if="article.title === '' || article.content === ''"
                     type="primary"
                     color="#626aef"
                     disabled
          >
            发布文章
          </el-button>

          <el-button @click="commitArticle"
                     v-else
                     color="#626aef"
                     type="primary" >
            发布文章
          </el-button>
        </div>

      </el-form>

    </el-scrollbar>

  </div>
</template>

<script setup>
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import MdEditorV3Com from "@/components/MdEditorV3Com.vue"
import { addArticle, updateArticle } from "@/api/article";
import { ElMessage } from "element-plus";
import router from "@/router";
import { getAllCategoryList } from "@/api/category";
import { uploadImg } from "@/api/upload";

const article = reactive({
  title: '',
  content: '',
  tags: [],
})

const rules = {
  title: [{ required: true, message: '请选择输入标题' }],
  content: [{ required: true, message: '请选择输入正文' }],
  categoryId: [{ required: true, message: '请选择分类' }],
  isComment: [{ required: true, message: '请选择是否允许评论' }],
  isAuto: [{ required: true, message: '请选择是否设置为轮播图' }],
}

const theme = useConfigStore().theme

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

//文章分类
const categoryList = ref()
const loadCategoryList = async () => {
  categoryList.value = await getAllCategoryList()
}
loadCategoryList()

//标签
const tagValue = ref(null)
const showTagInput = ref(false)

/**
 * 显示标签输入框
 */
const showTagInputHandler = () => {
  showTagInput.value = true
}

/**
 * 删除标签
 * @param index
 */
const closeTag = (index) => {
  article.tags.splice(index, 1)
}

/**
 * 添加标签
 */
const addTag = () => {
  //为空
  if(tagValue.value === undefined) return

  //过滤重复标签
  if(article.tags.indexOf(tagValue.value) === -1) {
    article.tags.push(tagValue.value) //添加到表单中
  } else {
    ElMessage.error("标签名重复")
  }

  showTagInput.value = false
  tagValue.value = undefined //清除
}

const formRef = ref()
const commitArticle = () => {
  formRef.value.validate( async isValid => {
    if (isValid) {

      const data ={}
      Object.assign(data, article)
      // console.log(data)
      await addArticle(data)
      ElMessage.success('发布文章成功！')
    }
  })
}


</script>

<style lang="sass" scoped>

.commit-body
  .commit-content
    .content-editor

  .commit-body-form
    margin: 2% 10%
    border: 1px solid black

</style>