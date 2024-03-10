import {defineStore} from 'pinia'
import {ref} from "vue";

export const useSystemStore = defineStore('system', () =>{
  const currentTheme = ref('light')

  function getTheme() {
    return currentTheme.value
  }

  function setTheme(data) {
    currentTheme.value = data
  }

  function resetState() {
    currentTheme.value = 'light' //reset
  }

  return { currentTheme, getTheme, setTheme, resetState }
},{
  persist: {
    enable: true,
  }
})
