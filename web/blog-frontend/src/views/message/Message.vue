<template>
  <div class="message-body slide-in-container">
    <h2 class="rowCC">留言板</h2>
    <div class="message p-1">
<!--      <div class="mes-input m-1 rowCC">
        <el-input v-model="text" placeholder="留下点什么吧"></el-input>
        <el-button @click="commitMessage()" round color="white">发送</el-button>
      </div>

      <div class="rowCC">
        <el-button @click="playDan()" type="success">播放</el-button>
        <el-button @click="pauseDan()" type="warning">暂停</el-button>
        <el-button @click="stopDan()" type="danger">停止</el-button>
      </div>-->
    </div>
    <div class="mess-dan">
      <DanMu></DanMu>

<!--      <vue-danmaku ref="danmakuRef" v-model:danmus="danmus"
                  useSlot isSuspend :channels="5"
                  style="height:56vh; width:100%;display: block" >
        <template v-slot:dm="{ index, danmu }">
          <div class="dan-item">
            <span class="p-1 br-2"
                  style="color: black;background:rgba(255,255,255,0.6);">
              {{ danmu.nickname }}：{{ danmu.text }}</span>
          </div>
        </template>
      </vue-danmaku>-->
    </div>
<!--      <vue-danmaku v-model:danmus="danmus" style="height:30vh; width:100%;"></vue-danmaku>-->

  </div>

</template>

<script setup>
import {ref} from "vue";
import {addMessage, messageList} from "@/api/message";
import {ElMessage} from "element-plus";
import DanMu from "@/views/message/com/DanMu.vue"
import Test from "@/views/Test.vue"
import {useBasicStore} from "@/stores/basic";

// const danmus = ref(['danmu1', 'danmu2', 'danmu3', '...'])
const danmus = ref([
  { avatar: 'https://p.qqan.com/up/2020-12/16070652267848982.jpg', nickname: 'a', text: 'aaa' },
  { avatar: 'http://a.com/b.jpg', nickname: 'b', text: 'bbb' },
])

const getMessageList = async () => {
  danmus.value = await messageList()
}
// getMessageList()

const text = ref(null)
const userInfo = ref({})

const commitMessage = async () => {
  if(text.value === null) {
    ElMessage.warning('留言文本为空')
    return
  }

  //获取用户昵称
  userInfo.value = useBasicStore().getUserInfo()
  if(userInfo.value === null) {
    ElMessage.warning('用户未登录')
    return
  }

  await addMessage(text.value, Number.parseInt(userInfo.value.id))
  danmus.value.push({
        name: userInfo.value.nickname,
        text: text.value})
  ElMessage.success('留言成功！')
}

const danmakuRef = ref(null)
const playDan = () => {
  danmakuRef.value.play()
}

const pauseDan = () => {
  danmakuRef.value.pause()
}

const stopDan = () => {
  danmakuRef.value.stop()
}

</script>

<style lang="scss" scoped>
.message-body {
  padding-top: 7%;
  height: 100vh;
  background: url("https://ts1.cn.mm.bing.net/th/id/R-C.6674818270668e9c886b476256b791de?rik=lVcmG1WarOEqUg&riu=http%3a%2f%2fwww.sogoupc.com%2fuploads%2fallimg%2f120308%2f1-12030R10T9.jpg&ehk=IYfp7zUvGD7KPFVSPxK2f4aYOdJk2eervO4Z12Ko57c%3d&risl=&pid=ImgRaw&r=0") fixed;
}
  .message {
    border-radius: 30px;
    animation: slideUpIn 1s;

    .mes-input {
      //width: 600px;
      padding: 0 30%;
    }
    .mess-dan {
      position: relative;
      height: 500px;
      overflow: hidden;

      .dan-item {
        img {
          width: 100px;
          height: 100px;
        }
      }
    }

  }
</style>