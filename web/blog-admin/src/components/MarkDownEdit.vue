<template>
  <div>
    <v-md-editor v-model="(modelValue)" :height="height + 'px'"
                 :disabled-menus="[]" :include-level="[1, 2, 3, 4, 5, 6]"
                 @upload-image="handleUploadImage" @change="change">
    </v-md-editor>
  </div>
</template>

<script setup>
import {uploadImg} from "@/api/upload";

import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

import hljs from 'highlight.js';

VMdEditor.use(githubTheme, {
  Hljs: hljs,
});

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  height: {
    type: Number,
    default: 500
  }
})

const handleUploadImage = async (event, insertImage, files) => {
  let resp = await uploadImg(files[0])

  insertImage({
    url: resp,
    desc: '图片'
  })
}

const emit = defineEmits()

const change = (text, html) => {
  emit("update:modelValue", text)
  emit("html", html)
}


</script>

<style lang="scss">

</style>