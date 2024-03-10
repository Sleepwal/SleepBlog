<template>
  <div class="page rowCC">
    <div class="count">已累积:{{ count }}</div>

    <img
      :class="['muyu-img', { 'img-active': imgActive }]"
      :src="muYuImg"
      @click="clickMuyu"
    />

    <div :class="['tips', { 'tips-active': tipsActive }]">
      {{ tips }}
    </div>

    <audio controls ref="audio" class="aud">
      <source src="https://clemmensen.top/static/muyu.mp3" />
    </audio>

    <audio controls ref="bgm" loop="loop" class="aud">
      <source src="https://clemmensen.top/static/28297622582.mp3" />
    </audio>

    <div class="txt-right">
      <div
        :class="['txt', { 'txt-active': index === sel }]"
        v-for="(item, index) in list"
        :key="index"
        @click="changeIndex(index)"
      >
        {{ item.type }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import muYu from "@/assets/comment/木鱼.png"

const muYuImg = muYu

const count = ref(0);
const tips = ref("功德 +1");
const tipsActive = ref(false);
const imgActive = ref(false);
const time = ref(300);
const audio = ref(null);
const bgm = ref(null);
const sel = ref(0);
const list = ref([
  {
    type: "功德",
    tips: "功德 +1",
  },
  {
    type: "Bug",
    tips: "Bug -1",
  },
]);

function playBGM() {
  bgm.value.play();
}
function playMuyu() {
  audio.value.play();
}
function clickMuyu() {
  count.value++;
  tipsActive.value = true;
  imgActive.value = true;

  setTimeout(() => {
    tipsActive.value = false;
    imgActive.value = false;
  }, time.value);

  playMuyu();
}

function changeIndex(index) {
  sel.value = index;
  tips.value = list.value[sel.value].tips;
}
</script>

<style lang="scss" scoped>
@keyframes textMove {
  from {
    top: 50%;
    opacity: 1;
  }
  to {
    top: 20%;
    opacity: 0;
  }
}
@keyframes muyuScale {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(0.95);
  }
  100% {
    transform: scale(1);
  }
}

.page {
  overflow: hidden;
  position: relative;
  user-select: none;

  .txt-right {
    position: absolute;
    right: 20px;
    cursor: pointer;

    .txt {
      margin-bottom: 10px;
      padding: 20px;
      border-radius: 10px;
      font-size: 20px;
    }
    .txt-active {
      background: var(--grey-4);
    }
  }

  .count {
    position: absolute;
    top: 20%;
    left: 10%;
    font-size: 28px;
    text-align: center;
  }

  .muyu-img {
    height: 45vh;
    width: 30vw;
  }

  .img-active {
    animation: muyuScale 0.5s;
  }

  .tips {
    position: absolute;
    left: 23%;
    opacity: 0;
    font-size: 25px;
  }
  .tips-active {
    animation: textMove 1s;
  }
  .aud {
    display: none;
  }
}
</style>
