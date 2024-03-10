<template>
  <div>
    <ScanTemplate :result="resultIp">
      <template #input>
        <el-input v-model="startHost" type="text"
                  placeholder="请输入起始IP地址"/>
        <el-input v-model="endHost" type="text"
                  placeholder="请输入结束IP地址" class="mt-1"/>
      </template>

      <template #button>
        <el-button @click="submit()" type="success" plain>开始扫描</el-button>
      </template>

      <template #result-header>
        存活IP地址
      </template>
    </ScanTemplate>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {IPLiveScan} from "@/api/tools";
import ScanTemplate from "@/views/tools/com/ScanTemplate.vue";

const startHost = ref()
const endHost = ref()
const resultIp = ref()

const submit = async () => {
    resultIp.value = await IPLiveScan(startHost.value, endHost.value)
}

</script>

<style lang="scss" scoped>

</style>