<template>
  <div class="rowBC">
    <el-upload name="file" :show-file-list="false"
               accept=".png,.PNG,.jpg,.JPG,.gif,.GIF"
               :multiple="true"
               :http-request="uploadImage">
      <el-button type="primary">上传图片</el-button>
    </el-upload>
    <template v-if="modelValue">
      <div>
        <img :src="modelValue" alt="无图片"
             style="width: 120px; height: 120px;" class="m-2"/>
      </div>
    </template>
  </div>
</template>

<script setup>

import {uploadImg} from "@/api/upload";
import {ElMessage} from "element-plus";

const props = defineProps({
  modelValue: {
    type: String,
    default: null
  }
})

const imgUrl = ref()
const emit = defineEmits({})

const uploadImage = (file) => {
  console.log(file.file)
  let result = uploadImg(file.file).then((resp) => {
    imgUrl.value = resp
    emit("update:modelValue", resp)
    ElMessage.success("上传成功")
  })
}

</script>

<style lang="scss">

</style>