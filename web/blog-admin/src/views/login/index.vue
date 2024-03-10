<template>
  <div class="login-container rowCC">
    <div class="box-img rowCC">
      <div class="img1"></div>
    </div>
    <el-form ref="refLoginForm"
             class="login-form"
             :model="subForm"
             :rules="formRules">
      <div class="title-container">
        <h3 class="title text-center">{{ settings.title }}</h3>
      </div>
      <el-form-item prop="keyword" :rules="formRules.isNotNull('username不能为空')">
        <div class="rowSC flex-1">
<!--          <span class="svg-container">
            <svg-icon icon-class="user" />
          </span>-->

          <el-input
            v-model="subForm.keyword"
            placeholder="请输入用户名"
            class="my-input" >
            <template #prefix>
              <span class="svg-container">
                <svg-icon icon-class="user" />
              </span>
            </template>
          </el-input>
          <!--占位-->
          <div class="show-pwd" />
        </div>
      </el-form-item>

      <!--<el-form-item prop="password" :rules="formRules.passwordValid">-->
      <el-form-item prop="password" :rules="formRules.isNotNull('密码不能为空')">
        <div class="rowSC flex-1">
<!--          <span class="svg-container">-->
<!--            <svg-icon icon-class="password" />-->
<!--          </span>-->
          <el-input
            :key="passwordType"
            ref="refPassword"
            v-model="subForm.password"
            :type="passwordType"
            name="password"
            placeholder="请输入密码"
            @keyup.enter="handleLogin"
            class="my-input"
          >
            <template #prefix>
              <span class="svg-container">
                <svg-icon icon-class="password" />
              </span>
            </template>
          </el-input>
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </div>
      </el-form-item>

      <el-form-item prop="code">
        <div class="rowCC">
          <el-input
            v-model="subForm.code"
            placeholder="请输入验证码"
            auto-complete="off"
            @keyup.enter="handleLogin"
            class="mt-1 my-input"
          >
            <template #prefix>
              <span class="svg-container">
                <el-icon><CircleCheckFilled /></el-icon>
              </span>
            </template>
          </el-input>
          <!--加载验证码-->
          <img
            onclick="this.src='http://localhost:8082/api/common/admin/captcha?d='+new Date()"
            src="http://localhost:8082/api/common/admin/captcha"
            alt="验证码"
          />
        </div>
      </el-form-item>

      <div class="tip-message">{{ tipMessage }}</div>
      <el-button
        :loading="subLoading" type="primary"
        class="login-btn"
        size="default"
        @click.prevent="handleLogin">
        Login
      </el-button>
    </el-form>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBasicStore } from '@/store/basic'
import { elMessage, useElement } from '@/hooks/use-element'
import { loginReq } from '@/api/system'
import { login } from '@/api/login'
import { getUserInfo } from '@/api/system/user'

/* listen router change and set the query  */
const { settings } = useBasicStore()
//element valid
const formRules = useElement().formRules
//form
const subForm = reactive({
  keyword: '',
  password: '',
  // keyword: 'SleepWalker',
  // password: '1234',
  code: ''
})
const state = reactive({
  otherQuery: {},
  redirect: undefined
})
const route = useRoute()
const getOtherQuery = (query) => {
  return Object.keys(query).reduce((acc, cur) => {
    if (cur !== 'redirect') {
      acc[cur] = query[cur]
    }
    return acc
  }, {})
}
watch(
  () => route.query,
  (query) => {
    if (query) {
      state.redirect = query.redirect
      state.otherQuery = getOtherQuery(query)
    }
  },
  { immediate: true }
)

/*
 *  login relative
 * */
const subLoading = ref(false)
//tip message
const tipMessage = ref('')
//sub form
const refLoginForm = ref(null)
const handleLogin = () => {
  refLoginForm.value.validate((valid) => {
    // subLoading.value = true
    if (valid) loginFunc()
    // loginFunMock()
  })
}
const router = useRouter()
const basicStore = useBasicStore()

const loginFunc = async () => {
  let res = await login(subForm.keyword, subForm.password, subForm.code)
  elMessage('登录成功')

  basicStore.setToken(res.token)
  // userStore.setToken(res.token)

  let info = await getUserInfo()
  // basicStore.setUserInfo(info)
  basicStore.setUsername(info.username)
  basicStore.setAvatar(info.avatar)

  await router.push('/')
}

/*
 *  password show or hidden
 * */
const passwordType = ref('password')
const refPassword = ref(null)
const showPwd = () => {
  if (passwordType.value === 'password') {
    passwordType.value = ''
  } else {
    passwordType.value = 'password'
  }
  nextTick(() => {
    refPassword.value.focus()
  })
}
</script>

<style lang="scss" scoped>
$bg: #f5ebeb;
$box-bg: #fff;
$color-btn: #5e7ea2;
$color-shadow-top: #94814d;
$color-shadow-bottom: #c8af69;

$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  //height: 100vh;
  //width: 100%;
  background: $bg;
  //padding: 10%;

  .box-img {
    //flex: 1;
    //position: relative;

    .img1 {
      background: url("https://www.chinayis.com/image.php?maxwidth=1550&maxheight=1550&image=/Uploads/Picture/2018-07-11/5b454f1d68e0b.JPG");
      width: 50vw;
      height: 100vh;
      background-size: cover;
      overflow: hidden;
      //position: absolute;
    }

  }

  .login-form {
    //flex: 1;
    width: 50vw;
    height: 100vh;
    background: $bg;
    padding: 15% 8%;
    //margin: 0 10%;

    .my-input {
      border-radius: 10px;
      width: 100%;
      padding: 10px 20px;
      transition: .3s;
      font-size: 20px;

      @extend .new-protrusion;

      //&:hover {
      //  @extend .new-depression;
      //}
    }

    .login-btn {
      width: 100%;
      height: 54px;
      border: 0;
      font-size: 18px;
      font-weight: bold;
      background: $color-btn;
      transition: box-shadow 0.3s ease-in-out;
      @extend .new-protrusion;

      &:hover {
        @extend .new-depression
      }
    }


    img {
      margin-left: 10px;
      width: 160px;
      height: 50px;
    }
  }
  .title-container {
    .title {
      font-size: 22px;
      //color: #eee;
      margin: 0 auto 25px auto;
      text-align: center;
      font-weight: bold;
    }
  }
}

.new-depression {
  //background: var(--colour-btn);
  //box-shadow: inset 19px 19px 38px #d0c8c8,
  //inset -19px -19px 38px #ffffff;
}

.new-protrusion {
  box-shadow:  19px 19px 38px #d0c8c8,
  -19px -19px 38px #ffffff;
  //background: var(--colour-btn);
  //box-shadow: inset 0 0 0 $color-shadow-top, inset 0 0 0 $color-shadow-bottom,
  //6px 8px 17px $color-shadow-top, -5px -5px 25px $color-shadow-bottom;
}

.svg-container {
  padding-left: 6px;
  text-align: center;
  width: 30px;
}

//错误提示信息
.tip-message {
  color: #e4393c;
  height: 30px;
  margin-top: -12px;
  font-size: 12px;
}

.show-pwd {
  width: 50px;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
}
</style>

<style lang="scss">

//css 样式重置 增加个前缀避免全局污染
.login-container {
  .el-input__wrapper {
    background-color: transparent;
    box-shadow: none;
  }
  .el-form-item {

  }
  .el-input input {
    border: 0;
    -webkit-appearance: none;
    color: black;
  }
  //hiden the input border
  .el-input__inner {
    box-shadow: none !important;
  }
}
</style>
