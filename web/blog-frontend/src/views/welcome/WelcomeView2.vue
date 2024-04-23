<template>
  <div class="wel-body rowCC">
    <div :class="{'wel-container':true, rowCC: true, active:isActive}">
<!--    <div class="wel-container rowCC">-->
      <div class="sign-up wel-container-form">
        <RegisterPage :show-divider="false"/>
      </div>

      <div class="sign-in wel-container-form">
        <LoginPage :show-divider="false"/>
      </div>

      <div class="toggle-container">
        <div class="toggle">
          <div class="toggle-panel toggle-left">
            <h1>Welcome!</h1>
            <p>已有账号，前往登录</p>
            <button @click="isActive = !isActive" class="hidden" id="login">
              <span class="btn-sp">登录</span></button>
          </div>

          <div class="toggle-panel toggle-right">
            <h1>Hello!</h1>
            <p>没有账号，前往注册</p>
            <button @click="isActive = !isActive" class="hidden" id="register">
              <span class="btn-sp">注册</span></button>
          </div>
        </div>
      </div>
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
      <div class=" fw-b">双链博客系统</div>
    </div>

    <ToolsBar/>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {HomeFilled} from "@element-plus/icons-vue"
import ToolsBar from "@/layout/sidebar/ToolsBar.vue";
import RegisterPage from "@/views/welcome/components/RegisterPage.vue";
import LoginPage from "@/views/welcome/components/LoginPage.vue";

const show = ref(true)

const isActive = ref(false)

</script>

<style lang="scss" scoped>
.wel-body {
  background: var(--top-bg);
  flex-direction: column;
  min-height: 100vh;

  .wel-container {
    flex-direction: column;
    font-family:华文楷体,serif;
    background-color: var(--grey-1-a5);
    border-radius: 30px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, .35);
    position: relative;
    overflow: hidden;
    width: 768px;
    max-width: 100%;
    min-height: 550px;

    button {
      font-size: 14px;
      padding: 10px 45px;
      border: 1px solid transparent;
      border-radius: 8px;
      font-weight: 600;
      letter-spacing: 0.5px;
      text-transform: uppercase;
      margin-top: 10px;
      cursor: pointer;
      color: #ff7576;
      background-color: rgba(43, 48, 68, 0.71);
      outline: none;
      box-shadow: 0 1px 2px #2B3044, 0 4px 16px #2B3044;
    }

    .wel-container-form {
      position: absolute;
      top: 2%;
      height: 100%;
      transition: all .6s ease-in-out;
    }

    .sign-in {
      left: 10px;
      top: 15%;
      width: 47%;
      z-index: 2;
    }

    &.active .sign-in {
      transform: translateX(100%);
      opacity: 0;
    }

    .sign-up {
      left: 10px;
      opacity: 0;
      z-index: 1;
    }

    &.active .sign-up {
      transform: translateX(100%);
      opacity: 1;
      z-index: 5;
      animation: move .6s;
    }

    .toggle-container {
      position: absolute;
      top: 0;
      left: 50%;
      width: 50%;
      height: 100%;
      overflow: hidden;
      transition: all .6s ease-in-out;
      border-radius: 150px 0 0 100px;
      z-index: 1000;
    }

    &.active .toggle-container {
      transform: translateX(-100%);
      border-radius: 0 150px 100px 0;
    }

    .toggle {
      height: 100%;
      background: linear-gradient(to right, rgba(84, 74, 125, 0.5), rgba(255, 212, 82, 0.5));
      color: #fff;
      position: relative;
      left: -100%;
      width: 200%;
      transform: translateX(0);
      transition: all .6s ease-in-out;
    }

    &.active .toggle {
      transform: translateX(50%);
    }

    .toggle-panel {
      position: absolute;
      width: 50%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      padding: 0 30px;
      text-align: center;
      top: 0;
      transform: translateX(0);
      transition: all .6s ease-in-out;
    }

    .toggle-left {
      transform: translateX(-100%);
    }

    &.active .toggle-left {
      transform: translateX(0);
    }

    .toggle-right {
      right: 0;
      transform: translateX(0);
    }

    &.active .toggle-right {
      transform: translateX(200%);
    }
  }

  .balls {
    animation: scale-rotate-in ease-out 1s;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;

    span {
      background: rgba(255, 255, 255, .3);
      position: absolute;
      border-radius: 20px;
      box-shadow: 1px 1px 3px rgba(255, 255, 255, 0.71);

      &:nth-child(1) {
        top: 10%;
        right: 16%;
        width: 120px;
        height: 120px;
        //background: linear-gradient(to right, #03001e, #7303c0, #ec38bc, #fdeff9);
        animation: ball-move ease-in-out 4s infinite alternate;
      }

      &:nth-child(2) {
        top: 12%;
        left: 24%;
        width: 60px;
        height: 60px;
        //background:  #f8cdda;
        animation: ball-move ease-in-out 1.5s infinite alternate;
      }

      &:nth-child(3) {
        bottom: 17%;
        left: 14%;
        width: 80px;
        height: 80px;
        //background: linear-gradient(to right, #ff6e7f, #bfe9ff);
        animation: ball-move ease-in-out 2s infinite alternate;
      }

      &:nth-child(4) {
        bottom: 14%;
        right: 10%;
        width: 200px;
        height: 200px;
        //background: linear-gradient(to right, #5433ff, #20bdff, #a5fecb);
        animation: ball-move ease-in-out 3s infinite alternate;
      }
    }
  }

}

.welcome-bottom {
  text-shadow: 0 0 10px orange;
  color: white;
  width:500px;
}

@keyframes ball-move {
  0% { transform: translateY(0); }
  100% { transform: translateY(-30px); }
}

@keyframes scale-rotate-in {
  0% { transform: scale(0,0) rotate(60deg); }
  100% { transform: scale(1,1) rotate(0); }
}

@keyframes move {
  0%, 49.99% {
    opacity: 0;
    z-index: 1;
  }
  50%, 100% {
    opacity: 1;
    z-index: 5;
  }
}

</style>

<style lang="scss">

//css 样式重置 增加个前缀避免全局污染
.wel-body {
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
    --el-input-text-color: var(--grey-9);
    --el-input-placeholder-color: var(--grey-9);
  }

  .el-icon {
    color: var(--grey-9);
  }

  .sub-button {
    width: 70px;
    height: 70px;
    font-weight: bold;
    color: var(--grey-9);
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

  img {
    width: 160px;
    height: 42px;
  }

}

</style>