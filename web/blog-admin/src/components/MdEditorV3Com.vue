<template>
  <div>
    <!--  正文  -->
    <MdEditor v-model="(modelValue)"
              :style="style"/>
  </div>
</template>

<script setup>
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

const { modelValue, width, height} = defineProps({
  modelValue: {
    type: String,
    default: '# Hello Editor'
  },
  width: {
    type:String,
    default: '100%'
  },
  height: {
    type:String,
    default: '100%'
  },
})

const style = {
  width,
  height
}


</script>

<style lang="scss" scoped>

</style>