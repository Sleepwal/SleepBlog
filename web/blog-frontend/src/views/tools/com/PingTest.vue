<template>
    <div>
        <ScanTemplate>
            <template #input>
                <el-input v-model="domain" type="text" size="large" class="fs-2"
                          placeholder="请输入域名,如baidu.com"/>
            </template>

            <template #button>
                <el-button @click="submit()" type="success" plain class="fs-2">ping测试</el-button>
            </template>

            <template #result-header>
                ping
            </template>

            <template #custom-result>
              <div class="fs-2">
                <div>域名: {{result.domain}}</div>
                <div>IP地址: {{result.ipAddress}}</div>
                <div>时间: {{ (result.pingTime / 1000 ).toFixed(2) }} s</div>
              </div>
            </template>
        </ScanTemplate>
    </div>
</template>

<script setup>
import {ref} from 'vue'
import {ping} from "@/api/tools";
import ScanTemplate from "@/views/tools/com/ScanTemplate.vue";

const domain = ref()
const result = ref({
    domain: '',
    ipAddress: '',
    pingTime: '',
})

const submit = async () => {
    result.value = await ping(domain.value)
}

</script>

<style lang="scss">

</style>