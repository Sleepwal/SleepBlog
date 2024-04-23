<template>
  <div>
    <el-button type="primary" @click="showEdit('add')" class="m-2" icon="plus">用户</el-button>
    <Table :columns="columns" :show-pagination="false"
           :data-source="tableData" :fetch="loadData"
           :options="tableOptions">
      <!--    状态    -->
      <template #statusName="{index, row}">
        <span v-if="row.status === '0'"><el-tag type="success">正常</el-tag></span>
        <span v-if="row.status === '1'"><el-tag>失效</el-tag></span>
      </template>
      <!--    用户类型    -->
      <template #typeName="{index, row}">
        <span v-if="row.type === '1'"><el-tag type="success">管理员</el-tag></span>
        <span v-if="row.type === '0'"><el-tag>普通用户</el-tag></span>
      </template>
      <template #handle="{index, row}">
        <div class="handle ">
          <div>
            <el-button @click="showEdit('update', row)" type="primary" :icon="Edit" text >编辑</el-button>
          </div>

          <div>
            <el-popconfirm title="是否要删除该分类?" @confirm="del(row.id)">
              <template #reference>
                <el-button type="danger" :icon="Delete" text>删除</el-button>
              </template>
            </el-popconfirm>
          </div>

        </div>
      </template>
    </Table>

    <Dialog :show="dialogConfig.show" :title="dialogConfig.title"
            :buttons="dialogConfig.buttons"
            @close="dialogConfig.show=false">
      <el-form :model="formData" :rules="rules"
               ref="formDataRef" :label-width="80">
        <el-form-item prop="username" label="账号">
          <el-input placeholder="请输入账号"
                    v-model="formData.username">
          </el-input>
        </el-form-item>
        <el-form-item prop="nickname" label="昵称">
          <el-input placeholder="请输入用户昵称"
                    v-model="formData.nickname">
          </el-input>
        </el-form-item>
        <el-form-item prop="type" label="用户类型">
          <el-select v-model="formData.type"
                     clearable placeholder="请选择用户类型">
            <el-option value="1" label="管理员"></el-option>
            <el-option value="0" label="普通用户"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="status" label="用户状态">
          <el-select v-model="formData.status"
                     clearable placeholder="请选择用户状态">
            <el-option value="0" label="正常"></el-option>
            <el-option value="1" label="失效"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input placeholder="请输入邮箱"
                    v-model="formData.email">
          </el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="个人简介">
          <el-input type="textarea" :autosize="{minRows: 2, maxRows: 5}"
                    placeholder="请输入个人简介"
                    v-model="formData.introduction">
          </el-input>
        </el-form-item>
      </el-form>
    </Dialog>

    <Dialog :show="addDialogConfig.show"
            :title="addDialogConfig.title"
            width="70%"
            :buttons="addDialogConfig.buttons"
            @close="addDialogConfig.show=false">
      <el-form :model="addFormData" :rules="rules"
               ref="addFormDataRef" :label-width="80">
        <el-form-item prop="username" label="账号">
          <el-input placeholder="请输入账号"
                    v-model="addFormData.username">
          </el-input>
        </el-form-item>

        <el-form-item prop="nickname" label="昵称">
          <el-input placeholder="请输入用户昵称"
                    v-model="addFormData.nickname">
          </el-input>
        </el-form-item>

        <el-form-item prop="password" label="密码" :rules="[{required: true, message: '请输入密码'}]">
          <el-input placeholder="请输入密码"
                    v-model="addFormData.password">
          </el-input>
        </el-form-item>

        <el-form-item prop="avatar" label="头像" :rules="[{required: true, message: '请选择头像'}]">
<!--          <el-input placeholder="请选择头像" v-model="addFormData.avatar"></el-input>-->

          <el-radio-group v-model="addFormData.avatar" class="img-list">
            <el-radio v-for="item in imgList"
                      :label="item"
                      class="img-item">
              <Cover :cover="item"/>
            </el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item prop="type" label="用户类型">
          <el-select v-model="addFormData.type"
                     clearable placeholder="请选择用户类型">
            <el-option value="1" label="管理员"></el-option>
            <el-option value="0" label="普通用户"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="sex" label="性别" :rules="[{required: true, message: '请选择性别'}]">
          <el-select v-model="addFormData.sex"
                     clearable placeholder="请选择性别">
            <el-option value="1" label="男"></el-option>
            <el-option value="0" label="女"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="email" label="邮箱">
          <el-input placeholder="请输入邮箱"
                    v-model="addFormData.email">
          </el-input>
        </el-form-item>

        <el-form-item prop="introduction" label="个人简介">
          <el-input type="textarea" :autosize="{minRows: 2, maxRows: 5}"
                    placeholder="请输入个人简介"
                    v-model="addFormData.introduction">
          </el-input>
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup>
import {
  Delete,
  Edit,
} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import { addUser, delUser, updateUser, userList } from "@/api/system/user";

const columns = [
  {
    label: '账号',
    prop: 'username',
    width: 200,
  },{
    label: '昵称',
    prop: 'nickname',
    width: 200,
  },{
    label: '用户类型',
    prop: 'typeName',
    width: 180,
    scopedSlots: 'typeName'
  },{
    label: '用户状态',
    prop: 'statusName',
    width: 150,
    scopedSlots: "statusName",
  },{
    label: '邮箱',
    prop: 'email',
    width: 150,
  },{
    label: '个人简介',
    prop: 'introduction',
  },{
    label: '操作',
    prop: 'handle',
    width: 180,
    scopedSlots: "handle",
  },
]

const tableData = reactive({})
const tableOptions = {
  extHeight: 10,
}

const loadData = async () => {
  tableData.list = await userList()
}

//新增、修改
const dialogConfig = reactive({
  show: false,
  title: "用户管理",
  buttons: [
    {
      type: "danger",
      text: "确定",
      click: e => {
        submitForm()
      }
    },
  ]
})

const addDialogConfig = reactive({
  show: false,
  title: "添加用户",
  buttons: [
    {
      type: "danger",
      text: "确定",
      click: e => {
        submitAddForm()
      }
    },
  ]
})

const formData = reactive({})
const rules = {
  username: [{required: true, message: '请输入用户账号'}],
  nickname: [{required: true, message: '请输入用户昵称'}],
  type: [{required: true, message: '请选择用户类型'}],
  status: [{required: true, message: '请选择用户状态'}],
  email: [
    { required: true, message: '请输入邮件地址', trigger: 'blur' },
    {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
  ],
}
const formDataRef = ref()

const addFormData = reactive({})
const addFormDataRef = ref()

const imgList = [
  'https://pic4.zhimg.com/v2-e81448887e1a5aaa5d7a23c59254b1ff_r.jpg',
  'https://pic4.zhimg.com/80/v2-0dbc479a4fa0f130a1622e36b9f43057_720w.webp',
  'https://pic3.zhimg.com/v2-831534cc60bb87d1ad15740b75ca08ce_r.jpg',
  'https://pic3.zhimg.com/80/v2-76544369e41409d816de62e3c2d5441e_720w.webp',
  'https://pic2.zhimg.com/80/v2-de9bcc0cca5f34dceded94e288ad3239_720w.webp'
]

const empty = {
  id: '',
  username: '',
  nickname: '',
  password: '',
  avatar: '',
  type: '',
  sex: '',
  email: '',
  introduction: '',
}
const showEdit = (type, data) => {
  if(type === 'add') {
    addDialogConfig.show = true
    addFormDataRef.value.resetFields()
    //清空formData
    Object.assign(addFormData, empty)

  } else if(type === 'update') {
    dialogConfig.show = true
    dialogConfig.title = "编辑用户"
    Object.assign(formData, data)
  }
}

const submitForm = () => {
  formDataRef.value.validate(async valid => {
    if(!valid) return

    let params = {}
    Object.assign(params, formData) //设置参数
    let resp = await updateUser(params)
    ElMessage.success("修改成功！")
    dialogConfig.show = false //对话框关闭
    await loadData() //重新加载数据
  })
}

const submitAddForm = () => {
  addFormDataRef.value.validate(async valid => {
    if(!valid) return

    let params = {}
    Object.assign(params, addFormData) //设置参数
    let resp = await addUser(params)
    ElMessage.success("添加成功！")
    addDialogConfig.show = false //对话框关闭
    await loadData() //重新加载数据
  })
}

//删除分类
const del = async (id) => {
  let resp = await delUser(id)
  ElMessage.success(resp)
  await loadData() //重新加载数据
}

</script>

<style lang="scss" scoped>

.img-list {
  height: 100px;

  .img-item {
    width: 150px;
  }
}

</style>