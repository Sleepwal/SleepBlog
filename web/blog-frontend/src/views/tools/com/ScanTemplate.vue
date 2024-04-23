<template>
  <div class="scan-template-body">
    <el-card shadow="hover" class="scan-template-input br-2 p-2">
      <div class="rowCC">
        <div class="template-input-left">
          <slot name="input"></slot>
        </div>

        <div class="m-1">
          <slot name="button"></slot>
        </div>
      </div>
    </el-card>

    <el-card shadow="hover" class="scan-template-result br-2 p-2">
      <template #header>
        <h2><slot name="result-header"></slot></h2>
      </template>

      <div class="tools-use-result">
        <el-button type="primary" v-for="r in result" class="m-1 fs-3"
                   plain @click="copy(r)">{{ r }}
        </el-button>
      </div>

      <slot name="custom-result"></slot>
    </el-card>
  </div>
</template>

<script setup>
import {ElMessage} from "element-plus";

const props = defineProps({
  result: {
    type: Array
  }
})

const copy = (value) => {
  navigator.clipboard.writeText(value)
  ElMessage.success('复制成功!')
}

</script>

<style lang="sass" scoped>

.scan-template-body
  .scan-template-input
    margin: 0 15%

  .template-input-left

  .scan-template-result
    text-align: center
    margin: 1% 15%

</style>