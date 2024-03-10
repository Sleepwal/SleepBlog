<template>
  <div class="user-setting-body slide-in-container">
    <el-card>
      <template #header>
        <h2>用户信息</h2>
      </template>

      <div class="user-info-form">
        <el-form
          :model="form"
          :rules="rules"
          ref="formRef">
          <el-form-item prop="username" label="账号">
            <el-input
              v-model="form.username"
              :maxlength="15"
              type="text"
              placeholder="账号">
              <template #prefix>
                <el-icon>
                  <User />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="nickname" label="昵称">
            <el-input
              v-model="form.nickname"
              :maxlength="15"
              type="text"
              placeholder="用户昵称">
              <template #prefix>
                <el-icon>
                  <User />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item label="原密码">
            <el-input
              v-model="form.oldPassword"
              :maxlength="15"
              type="password"
              show-password
            >
              <template #prefix>
                <el-icon>
                  <Lock />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item label="新密码">
            <el-input
              v-model="form.newPassword"
              :maxlength="15"
              type="password"
              show-password
            >
              <template #prefix>
                <el-icon>
                  <Lock />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="email" label="邮箱">
            <el-input
              v-model="form.email"
              type="email"
              placeholder="电子邮件地址">
              <template #prefix>
                <el-icon>
                  <Message />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="sex" label="性别">
            <el-radio-group v-model="form.sex">
              <el-radio label="1">男</el-radio>
              <el-radio label="0">女</el-radio>
              <el-radio label="2">未知</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>

      <span class="rowCC">
        <el-button type="primary"
         @click="submit()">
          确认修改
        </el-button>
      </span>
    </el-card>

  </div>
</template>

<script setup>
import { Lock, Message, User, CircleCheck } from "@element-plus/icons-vue";
import router from "@/router";
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { getUserInfo, savaUserInfo, userRegister } from "@/api/user";

const formRef = ref();
const form = reactive({
  username: "",
  nickname: "",
  oldPassword: "",
  newPassword: "",
  email: "",
  sex: ""
});

const loadUserInfo = async () => {
  const data = await getUserInfo();
  Object.assign(form, data);
  Object.assign(passForm, data);
  // console.log(data)
};
loadUserInfo();

const submit = () => {
  formRef.value.validate(async (isValid) => {
    if (isValid) {
      if(form.oldPassword != "" && form.newPassword == "") {
        ElMessage.warning("请同时填写原密码和新密码")
        return
      }
      if(form.oldPassword == "" && form.newPassword != "") {
        ElMessage.warning("请同时填写原密码和新密码")
        return
      }

      let msg = await savaUserInfo(form);
      ElMessage.success(msg);
      await router.push("/");
    }
  });
};

const validateUsername = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请输入账号"));
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error("账号不能包含特殊字符，只能是中文/英文"));
  } else {
    callback();
  }
};

//===表单校验===
const validateNickname = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请输入用户别名"));
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error("昵称不能包含特殊字符，只能是中文/英文"));
  } else {
    callback();
  }
};

const rules = {
  username: [
    { required: true, validator: validateUsername, trigger: ["blur", "change"] },
    { min: 4, max: 15, message: "合法的用户名的长度在4-15个字符之间", trigger: ["blur", "change"] }
  ],
  nickname: [
    { required: true, validator: validateNickname, trigger: ["blur", "change"] },
    { min: 4, max: 15, message: "合法的别名的长度在4-15个字符之间", trigger: ["blur", "change"] }
  ],
  email: [
    { required: true, message: "请输入邮件地址", trigger: "blur" },
    { type: "email", message: "请输入合法的电子邮件地址", trigger: ["blur", "change"] }
  ],
  sex: [
    { required: true, message: "请输入性别", trigger: "blur" }
  ]
};


</script>

<style lang="scss" scoped>
.user-setting-body {
  padding: 0 20% 5% 20%;


  .user-info-form {
    padding: 0 10%;
  }

}
</style>