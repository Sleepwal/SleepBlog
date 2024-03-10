<template>
  <div>
    <el-form :model="formData" :rules="rules" ref="formDataRef">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card>
            <template #header>
              个人信息
            </template>

            <el-form-item prop="username" label="账号">
              <el-input v-model="formData.username" :maxlength="15" type="text"
                        placeholder="请输入账号">
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="nickname" label="昵称">
              <el-input v-model="formData.nickname" :maxlength="15" type="text"
                        placeholder="请输入用户昵称">
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item label="密码">
              <el-link @click="showEdit" type="danger">修改密码</el-link>
            </el-form-item>

            <el-form-item prop="email" label="邮箱地址">
              <el-input v-model="formData.email" type="email" placeholder="请输入电子邮件地址">
                <template #prefix>
                  <el-icon><Message /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="saveUserInfo" type="danger">保存</el-button>
            </el-form-item>
          </el-card>

        </el-col>
        <el-col :span="12">
          <el-card>
            <template #header>
              个人简介
            </template>
            <el-form-item prop="introduction">
              <MarkDownEdit v-model="formData.introduction" style="width: 100%"></MarkDownEdit>
            </el-form-item>
          </el-card>
        </el-col>
      </el-row>
    </el-form>

    <Dialog :show="dialogConfig.show" :title="dialogConfig.title"
            :buttons="dialogConfig.buttons"
            @close="dialogConfig.show=false">
      <el-form :model="passFormData" :rules="passRules"
               ref="passFormRef" :label-width="80">
        <el-form-item prop="password" label="密码">
          <el-input type="password" placeholder="请输入新密码"
                    v-model="passFormData.password">
          </el-input>
        </el-form-item>
        <el-form-item prop="password_repeat" label="确认密码">
          <el-input type="repeatPass"
                    placeholder="请重复输入新密码"
                    v-model="passFormData.password_repeat">
          </el-input>
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup>

import {getUserInfo, updatePassword, updateUser} from "@/api/system/user";
import {ElMessage} from "element-plus";

const formData = ref({})
const formDataRef = ref()

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户账号'))
  } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
    callback(new Error('账号不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

const validateNickname = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户昵称'))
  } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
    callback(new Error('昵称不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { validator: validateUsername, trigger: ['blur', 'change'] },
    { min: 4, max: 15, message: '合法的用户名的长度在4-15个字符之间', trigger: ['blur', 'change'] },
  ],
  nickname: [
    { validator: validateNickname, trigger: ['blur', 'change'] },
    { min: 4, max: 15, message: '合法的别名的长度在4-15个字符之间', trigger: ['blur', 'change'] },
  ],
  email: [
    { required: true, message: '请输入邮件地址', trigger: 'blur' },
    {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
  ],
}

const loadUserInfo = async () => {
  formData.value = await getUserInfo()
}
loadUserInfo()

const saveUserInfo = () => {
  formDataRef.value.validate(async valid => {
    if (!valid) return

    await updateUser(formData.value)
    ElMessage.success("保存用户信息成功！")
  })
}

//修改密码
const dialogConfig = reactive({
  show: false,
  title: "修改密码",
  buttons: [
    {
      type: "danger",
      text: "确定",
      click: e => {
        savePassword()
      }
    },
  ]
})

const passFormData = ref({})
const passFormRef = ref()

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== passFormData.value.password) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const passRules = {
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, max: 16, message: '合法的密码的长度在4-16个字符之间', trigger: ['blur', 'change'] }
  ],
  password_repeat: [
    { required: true, validator: validatePassword, trigger: ['blur', 'change'] },
  ],
}

const showEdit = () => {
  dialogConfig.show = true
  passFormData.value.id = formData.value.id
}

const savePassword = async () => {
  await updatePassword(passFormData.value)
  ElMessage.success("修改密码成功！")
  dialogConfig.show = false
}

</script>

<style lang="scss" scoped>

</style>