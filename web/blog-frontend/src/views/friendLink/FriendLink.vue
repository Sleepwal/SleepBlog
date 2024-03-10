<template>
  <div class="friend-link-body br-2 slide-in-container">
    <h2>友情链接</h2>
    <div class="friend-links">
      <div class="friend-link-card" v-for="link in friendLinks" :key="link.id">
        <div class="friend-link-avatar">
          <img :src="link.logo" alt="网站头像">
        </div>
        <div class="friend-link-info">
          <a :href="link.address" target="_blank">
            <h3>{{ link.name }}</h3>
            <p>{{ link.description }}</p>
          </a>
        </div>
      </div>
    </div>

    <div class="rowBC">
      <h2>友链申请格式：</h2>
      <el-button type="success" @click="openDialog">友链申请</el-button>
    </div>
    <div class="apply-format rowCC">
      <div class="apply-format-content">
        <p>网站名称：SleepBlog</p>
        <p>网站简介：Just for fun</p>
        <p>网站地址：https://sleep/friendLink</p>
        <p>网站头像：https://sleep/logo.png</p>
      </div>
    </div>

    <div class="mt-1">
      <LinkComment/>
    </div>
  </div>


  <el-dialog v-model="dialogVisible" title="友链申请"
             @close="closeDialog" class="br-2">
    <el-form :model="formData" label-position="top">
      <el-form-item label="网站名称">
        <el-input v-model="formData.name"></el-input>
      </el-form-item>
      <el-form-item label="网站简介">
        <el-input v-model="formData.description"></el-input>
      </el-form-item>
      <el-form-item label="网站地址">
        <el-input v-model="formData.address"></el-input>
      </el-form-item>
      <el-form-item label="网站头像">
        <el-input v-model="formData.logo"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </span>
  </el-dialog>
</template>

<script setup>
// 定义友链列表数据
import {reactive, ref} from "vue";
import {applyLink, getAllLink} from "@/api/link";
import {ElMessage} from "element-plus";
import LinkComment from "@/views/friendLink/LinkComment.vue";

const friendLinks = ref()
const loadLinkList = async () => {
  friendLinks.value = await getAllLink()
}
loadLinkList()

// 定义弹出对话框的显示状态
const dialogVisible = ref(false);

// 定义友链表单的数据
const formData = reactive({})

// 定义打开对话框的方法
const openDialog = () => {
  dialogVisible.value = true;
}

const closeDialog = () => {
  dialogVisible.value = false;

  //
  Object.keys(formData).map(key => {
    delete formData[key]
  })
}

// 定义提交友链表单的方法
const submitForm = async () => {
  // 将表单数据提交到后端处理
  await applyLink(formData)
  ElMessage.success('申请友链成功!')
  // 清空表单数据
  formData.name = '';
  formData.intro = '';
  formData.url = '';
  formData.avatar = '';
  // 关闭对话框
  dialogVisible.value = false;
};

</script>

<style lang="scss" scoped>
.friend-link-body {
  margin: 2% 10%;
  padding: 3%;
  background: var(--grey-1);

  .friend-links {
    display: flex;
    flex-wrap: wrap;

    .friend-link-card {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-right: 20px;
      margin-bottom: 20px;

      .friend-link-avatar img {
        width: 200px;
        height: 100px;

        &:hover {
          transform: scale(1.1);
          transition: transform 0.3s ease;
        }
      }

    }
  }

  .apply-format {
    margin-top: 20px;
    animation: slideUpIn 1s;

    .apply-format-content {
      flex: 1;
      background-color: var(--grey-2);
      font-size: 18px;
      border-left: 10px solid var(--color-red);
      text-align: center;
    }
  }

}

</style>