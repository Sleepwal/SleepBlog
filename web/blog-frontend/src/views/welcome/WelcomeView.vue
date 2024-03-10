<template>
  <div class="out-box">

    <div class="wel-box glass center-50 p-5 br-2">
      <router-view v-slot="{ Component }">
        <transition name="el-zoom-in-center" mode="out-in">
          <component :is="Component" style="height: 100%"/>
        </transition>
      </router-view>
    </div>

    <div class="balls">
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </div>

    <div class="welcome-bottom glass center-bottom0 br-2 ta-c">
      <router-link to="/" class="bottom-link fs-2">
        <el-icon class="icon-align-text"><HomeFilled /></el-icon>
        回到首页
      </router-link>
      <div class="fs-2 fw-b">欢迎来到我的个人博客</div>
    </div>

    <ToolsBar/>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {HomeFilled} from "@element-plus/icons-vue"
import ToolsBar from "@/layout/sidebar/ToolsBar.vue";

const show = ref(true)
</script>

<style lang="scss" scoped>
.out-box {
  font-family:华文楷体,serif;
  //background-size:cover;
  //background: url("@/assets/blackBackground.jpg") no-repeat;
  background: var(--top-bg);
  //background: #1e1e1e;
  width:100%;			/*大小设置为100%*/
  height:100%;			/*大小设置为100%*/
  position:fixed;
  background-size:100% 100%;

  .balls {
    animation: scale-rotate-in ease-out 1s;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;

    span {
      background: rgba(255, 255, 255, .05);
      position: absolute;
      border-radius: 20px;
      box-shadow: 1px 1px 3px rgba(255, 255, 255, 0.71);

      &:nth-child(1) {
        top: 10%;
        right: 32%;
        width: 120px;
        height: 120px;
        //background: linear-gradient(to right, #03001e, #7303c0, #ec38bc, #fdeff9);
        animation: ball-move ease-in-out 4s infinite alternate;
      }

      &:nth-child(2) {
        top: 12%;
        left: 32%;
        width: 60px;
        height: 60px;
        //background:  #f8cdda;
        animation: ball-move ease-in-out 1.5s infinite alternate;
      }

      &:nth-child(3) {
        bottom: 17%;
        left: 30%;
        width: 80px;
        height: 80px;
        //background: linear-gradient(to right, #ff6e7f, #bfe9ff);
        animation: ball-move ease-in-out 2s infinite alternate;
      }

      &:nth-child(4) {
        bottom: 14%;
        right: 25%;
        width: 200px;
        height: 200px;
        //background: linear-gradient(to right, #5433ff, #20bdff, #a5fecb);
        animation: ball-move ease-in-out 3s infinite alternate;
      }
    }
  }

}

.wel-box {
  width:450px;
  animation: blur ease-out 1s .2s backwards;
}

.welcome-bottom {
  text-shadow: 0 0 10px orange;
  color: white;
  width:500px;
}

.bottom-link {
  //color: var(--color-red);
}

@keyframes ball-move {
  0% { transform: translateY(0); }
  100% { transform: translateY(-30px); }
}

@keyframes scale-rotate-in {
  0% { transform: scale(0,0) rotate(60deg); }
  100% { transform: scale(1,1) rotate(0); }
}

</style>

<style lang="scss">

//css 样式重置 增加个前缀避免全局污染
.wel-box {
  .el-input__wrapper {
    background-color: transparent;
    box-shadow: none;
  }

  .el-input input {
    border: 0;
    border-bottom: 1px solid #666;
    -webkit-appearance: none;
    //color: black;
  }
  .el-input__inner {
    box-shadow: none !important;
  }
  .el-input {
    --el-input-text-color: white;
    --el-input-placeholder-color: white;
  }

  .sub-button {
    width: 70px;
    height: 70px;
    font-weight: bold;
    color: white;
    margin-left: 40%;
    cursor: pointer;
    background: rgba(255,255,255,0.1);
    box-shadow: 0 2px 10px #111;
    border-radius: 50%;
    transition: .3s;

    &:hover {
      width: 160px;
      margin-left: 27%;
      border-radius: 70px;
      box-shadow: 0 0 10px #999,
      inset 0 0 5px #333;

      .sub-btn-text {
        display: block;
      }
    }

    .sub-btn-text {
      font-size: 20px;
      display: none;
      animation: titleScale .7s;
    }
  }

  .wel-title {
    margin: 0 0 30px;
    padding: 0;
    text-align: center;
    font-weight: bold;
  }

  .reg-login-form {
    animation: slideUpIn 1s;

    img {
      width: 160px;
      height: 42px;
    }
  }

}

</style>