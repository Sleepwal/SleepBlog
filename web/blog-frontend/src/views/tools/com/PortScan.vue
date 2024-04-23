<template>
  <div>
    <ScanTemplate :result="resultPort">
      <template #input>
        <el-input v-model="targetIp" type="text" class="fs-2" size="large"
                  placeholder="请输入目标IP" />
        <el-input v-model="startPort" type="text"
                  placeholder="请输入起始端口" class="mt-1 fs-2" size="large" />
        <el-input v-model="endPort" type="text"
                  placeholder="请输入结束端口" class="mt-1 fs-2" size="large"/>
      </template>

      <template #button>
        <div class="m-1">
          <el-button @click="startTcpScan()" type="success" plain>TCP扫描</el-button>
        </div>

        <div class="m-1">
          <el-button @click="startUdpScan()" type="success" plain>UDP扫描</el-button>
        </div>
      </template>

      <template #result-header>
        目标IP: {{targetIp}}
      </template>

    </ScanTemplate>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {tcpPortScan, udpPortScan} from "@/api/tools";
import ScanTemplate from "@/views/tools/com/ScanTemplate.vue";

const targetIp = ref()
const startPort = ref()
const endPort = ref()
const resultPort = ref()

const startTcpScan = async () => {
  resultPort.value = await tcpPortScan(targetIp.value, startPort.value, endPort.value)
}

const startUdpScan = async () => {
  resultPort.value = await udpPortScan(targetIp.value, startPort.value, endPort.value)
}


</script>

<style lang="scss" scoped>



</style>