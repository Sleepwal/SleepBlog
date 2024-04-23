<template>
    <div>
        <ScanTemplate>
            <template #input>
                <el-form>
                    <el-form-item size="large">
                        <div class="rowBC">
                            <el-input v-model="text" type="text" class="fs-2"
                                      placeholder="请输入需要编码的字符串"/>
                            <el-button @click="base64Encode()" type="success" class="fs-2" plain>编码</el-button>
                        </div>
                    </el-form-item>

                </el-form>

                <el-form>
                    <el-form-item size="large">
                        <div class="rowBC">
                            <el-input v-model="base64Text" type="text" label="" class="fs-2"
                                      placeholder="请输入需要解码的字符串"/>
                            <el-button @click="base64Decode()" type="success" plain class="fs-2">解码</el-button>
                        </div>
                    </el-form-item>
                </el-form>
            </template>

            <template #result-header>
                {{resultHeader}}
            </template>

            <template #custom-result>
              <div class="fs-2">{{result}}</div>
            </template>
        </ScanTemplate>
    </div>
</template>

<script setup>
import {ref} from 'vue'
import ScanTemplate from "@/views/tools/com/ScanTemplate.vue";

const text = ref()
const base64Text = ref()
const result = ref()
const resultHeader = ref('编码/解码内容')

const base64Encode = () => {
    result.value = window.btoa(text.value); //编码
    resultHeader.value = '编码内容'
}

const base64Decode = () => {
    result.value  = window.atob(base64Text.value); //解码
    resultHeader.value = '解码内容'
}

</script>

<style lang="scss">

</style>