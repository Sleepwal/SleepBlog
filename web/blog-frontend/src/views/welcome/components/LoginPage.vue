<template>
  <div>
    <h2 class="wel-title">登录界面</h2>
    <el-form class="reg-login-form" :model="form" :rules="rules" ref="formRef">
      <el-form-item prop="username">
        <el-input v-model="form.username" type="text" placeholder="请输入账号">
          <template #prefix>
            <el-icon slot="prefix">
              <User />
            </el-icon>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input v-model="form.password" type="password" show-password placeholder="请输入密码">
          <template #prefix>
            <el-icon slot="prefix">
              <Lock />
            </el-icon>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="code">
        <el-row>
          <el-col :span="12" class="rowCC">
            <el-input v-model="form.code" placeholder="请输入验证码"
                      auto-complete="off" @keyup.enter="login">
              <template #prefix>
                <el-icon slot="prefix">
                  <CircleCheck />
                </el-icon>
              </template>
            </el-input>
          </el-col>

          <el-col :span="12" class="rowCC">
            <!--加载验证码-->
            <img onclick="this.src='http://localhost:8081/api/common/captcha?d='+new Date()*1"
                 src="http://localhost:8081/api/common/captcha" alt="验证码" />
          </el-col>
        </el-row>
      </el-form-item>

      <el-row style="margin-top: 5px;">
        <el-col :span="12">
          <!--          <el-checkbox v-model="form.remember"><strong>记住我</strong></el-checkbox>-->
        </el-col>
        <el-col :span="12" style="text-align: right">
          <el-link><strong>忘记密码?</strong></el-link>
        </el-col>
      </el-row>

      <!--      <div class="mt-20" align="center">
              <el-button @click="login()" type="success" style="width: 250px;" plain>登录</el-button>
            </div>-->
      <div class="sub-button rowCC" @click="login()">
        <el-icon size="28">
          <Right />
        </el-icon>
        <span class="sub-btn-text">登录</span>
      </div>

      <!--分割线-->
      <el-divider v-if="showDivider">
        <span style="font-size: 15px;">没有账号?</span>
        <el-button @click="router.push({path: '/Register'})"
                   type="warning" text>
          注册
        </el-button>
      </el-divider>

    </el-form>
  </div>
</template>

<script setup>
import { Lock, User, CircleCheck } from "@element-plus/icons-vue";
import { reactive, ref } from "vue";
import router from "@/router";
import { userLogin } from "@/api/user";
import { setToken } from "@/utils/auth";
import { ElMessage } from "element-plus";
import { useBasicStore } from "@/stores/basic";

const props = defineProps({
  showDivider: {
    type: Boolean,
    default: true
  }
});

const form = reactive({
  username: "",
  password: "",
  remember: false,
  code: ""
});
const formRef = ref();

const validateUsername = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请输入账号"));
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error("账号不能包含特殊字符，只能是中文/英文"));
  } else {
    callback();
  }
};

const rules = {
  username: [
    { validator: validateUsername, trigger: ["blur", "change"] },
    { min: 4, max: 15, message: "合法的用户名的长度在4-15个字符之间", trigger: ["blur", "change"] }
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 4, max: 16, message: "合法的密码的长度在4-16个字符之间", trigger: ["blur", "change"] }
  ],
  code: [
    { required: true, message: "请输入验证码", trigger: "blur" }
  ]
};

const basicStore = useBasicStore();
const login = () => { //用户登录
  formRef.value.validate(async isValid => {
    if (!isValid) return;

    let response = await userLogin(form.username, form.password, form.code);
    // 登录成功记录token和用户信息，登录失败给对应提示
    basicStore.setToken(response.token);
    basicStore.setUserInfo(response.userInfo);

    ElMessage.success("登录成功");
    //上一步登录的路径
    const loginUrl = basicStore.getLoginUrl();
    if (loginUrl !== "") {
      await router.push({ path: loginUrl });
    } else {
      await router.push({ path: "/" });
    }
  });
};

</script>

<style lang="scss" scoped>

.mt-20 {
  margin-top: 20px;
}

</style>


