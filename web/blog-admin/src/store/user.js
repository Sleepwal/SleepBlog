import { nextTick, reactive, ref } from "vue";
import { defineStore } from "pinia";
import router from "@/router";

export const useUserStore = defineStore('user', () => {
  const token = ref('')
  function getToken() {
    return token.value
  }
  function setToken(data) {
    token.value = data
  }

  const userInfo = reactive( {
    id: '',
    username: '',
    nickname:'',
    email: '',
    avatar: '',
  })
  function getUsername() {
    return userInfo.username
  }
  function getUserInfo() {
    return userInfo
  }
  function setUserInfo(data) {
    const { username, nickname, id, email, avatar } = data
    userInfo.username = username
    userInfo.nickname = nickname
    userInfo.id = id
    userInfo.email = email
    userInfo.avatar = avatar
  }

  function resetState() {
    token.value = '' //reset token
    //reset userInfo
    userInfo.username = ''
    userInfo.nickname = ''
    userInfo.id = ''
    userInfo.email = ''
    userInfo.avatar = ''
  }
  function resetStateAndToLogin() {
    resetState()

    nextTick(() => {
      router.push({ path: '/Login' })
    })
  }

  return { token, getToken, setToken,
    userInfo, getUsername, getUserInfo, setUserInfo,
    resetState, resetStateAndToLogin
  }
},{
  persist: {
    enable: true,
  }
})
