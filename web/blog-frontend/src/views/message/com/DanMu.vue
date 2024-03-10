<template>
  <div class="bullet-wrap">
    <div class="bullet-item bf p-1 br-2 rowCC"
         v-for="item in showingBullets"
         @animationend='removeBullet'
         :key="item.id"
         :data-line="item.line">
      <el-avatar :src="item.avatar"></el-avatar>

      {{item.nickname}} : {{item.text}}
    </div>

    <div class="input-wrap rowCC">
      <el-input v-model.trim="inputText" type='text'
                maxlength='12'
                placeholder='来说点什么'></el-input>

      <el-button @click="clickSend()">发送</el-button>
    </div>
  </div>
</template>

<script setup>
import {onMounted, onUnmounted, reactive, ref} from "vue";
import {addMessage, messageList} from "@/api/message";
import {useBasicStore} from "@/stores/basic";
import {ElMessage} from "element-plus";

const getUUID = () => Math.random() + Math.random();

// 将要显示的弹幕队列
const waitBullets = ref([])

const getMessageList = async () => {
  waitBullets.value = await messageList()
}
getMessageList()

const showingBullets = ref([])

const options = reactive({
  lines: 5,
  currentLine: 0,
  isInfinite: false
})

const inputText = ref()

const showNextBullet = () => {
  if (!waitBullets.value.length) {
    return;
  }
  // 先确定弹道，跟上一个弹道错开即可
  options.currentLine = (options.currentLine % options.lines) + 1;
  // 从等待集合里取出第一个
  const currentBullet = waitBullets.value.shift();
  // 想要无限循环的话
  options.isInfinite && waitBullets.value.push({
    id: getUUID(),
    nickname: currentBullet.nickname,
    line: 0
  });
  // 设置弹幕的弹道
  currentBullet.line = options.currentLine;
  // 弹幕放进显示集合里，弹幕开始滚动
  showingBullets.value.push(currentBullet);
}
showNextBullet()

const timer = setInterval(showNextBullet, 500);

onUnmounted(() => {
  // 组件销毁前，清除定时器
  clearInterval(timer);
})

const clickSend = async () => {
  if (!inputText.value) {
    return;
  }

  //获取用户昵称
  let userInfo = useBasicStore().getUserInfo()
  if(userInfo === null) {
    ElMessage.warning('用户未登录')
    return
  }

  const newBullet = {
    id: getUUID(),
    nickname: userInfo.nickname,
    text: inputText.value,
    line: 0
  };
  waitBullets.value.push(newBullet);

  await addMessage(inputText.value, Number.parseInt(userInfo.id))
  ElMessage.success('留言成功！')
}

const removeBullet = () => {
  showingBullets.value.shift();
  // console.log(showingBullets.value);
}

</script>

<style lang="scss" scoped>
.bullet-wrap {
  //height: 50vh;
  position: relative;

  .bullet-item {
    position: absolute;
    background-color: var(--grey-1);
    color: var(--grey-9);
    animation: rightToLeft 15s linear both;
    transition: font-size .2s linear;

    &:hover {
      animation-play-state: paused;
      font-size: 20px;
    }
  }

  .bullet-item[data-line="1"] {
    top: 75px;
  }
  .bullet-item[data-line="2"] {
    top: 150px;
  }
  .bullet-item[data-line="3"] {
    top: 225px;
  }
  .bullet-item[data-line="4"] {
    top: 300px;
  }
  .bullet-item[data-line="5"] {
    top: 375px;
  }

  .input-wrap {
    margin: 0 30%;
  }
}

@keyframes rightToLeft {
  from {
    transform: translate(-100%);

  }
  to {
    transform: translate(100vw);
  }
}

</style>

