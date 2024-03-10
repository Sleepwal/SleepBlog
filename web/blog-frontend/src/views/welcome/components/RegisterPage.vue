<template>
  <div style="text-align: center;">
    <div>
      <h2 class="wel-title">注册新用户</h2>
    </div>
    <div class="reg-login-form" style="margin-top: 40px">
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" :maxlength="15" type="text" placeholder="账号">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="nickname">
          <el-input v-model="form.nickname" :maxlength="15" type="text" placeholder="用户昵称">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="form.password" :maxlength="16" type="password" show-password placeholder="密码">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password_repeat">
          <el-input v-model="form.password_repeat" :maxlength="16" type="password" show-password placeholder="确认密码">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="email">
          <el-input v-model="form.email" type="email" placeholder="电子邮件地址">
            <template #prefix>
              <el-icon><Message /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="code">
          <div class="rowCC">
            <el-input v-model="form.code" placeholder="请输入验证码"
                      auto-complete="off" @keyup.enter="register()"
                      class="mt-1">
              <template #prefix>
                <el-icon slot="prefix">
                  <CircleCheck/>
                </el-icon>
              </template>
            </el-input>
            <!--加载验证码-->
            <img onclick="this.src='http://localhost:8081/api/common/captcha?d='+new Date()*1"
                 src="http://localhost:8081/api/common/captcha" alt="验证码"/>
          </div>

        </el-form-item>

      </el-form>
    </div>

    <div style="margin-top: 30px">
<!--      <el-button style="width: 270px"
                 type="warning" @click="register()" plain>
        立即注册</el-button>-->

      <div @click="register()"
           class="sub-button rowCC">
        <el-icon size="28"><Back /></el-icon>
        <span class="sub-btn-text">注册</span>
      </div>
    </div>

    <el-divider v-if="showDivider">
      <span style="font-size: 14px;">已有账号?</span>
      <el-button @click="router.push('/Login')"
                 text type="primary">登录</el-button>
<!--      <el-link @click="router.push('/Login')">立即登录</el-link>-->
    </el-divider>
  </div>
</template>

<script setup>
import {Lock, Message, User,CircleCheck} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {userRegister} from "@/api/user";

const props = defineProps({
  showDivider: {
    type: Boolean,
    default: true
  }
})

const form = reactive({
  username: '',
  nickname: '',
  password: '',
  password_repeat: '',
  email: '',
  code: '',
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入账号'))
  } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
    callback(new Error('账号不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

const validateNickname = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户别名'))
  } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
    callback(new Error('昵称不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error("两次输入的密码不一致"))
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
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, max: 16, message: '合法的密码的长度在4-16个字符之间', trigger: ['blur', 'change'] }
  ],
  password_repeat: [
    { validator: validatePassword, trigger: ['blur', 'change'] },
  ],
  email: [
    { required: true, message: '请输入邮件地址', trigger: 'blur' },
    {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
  ],
}

const formRef = ref()

const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid) {
      userRegister(form).then((response) => {
        ElMessage.success('注册成功')
        //注册成功后调整到登录
        router.push('/Login')
      })
    }
  })
}

</script>

<style lang="scss" scoped>

</style>