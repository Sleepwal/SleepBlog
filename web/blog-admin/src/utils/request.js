import axios from "axios";
import { ElLoading, ElMessage, ElMessageBox, ElNotification } from "element-plus";
import errorCode from "@/utils/errorCode";
import { useBasicStore } from "@/store/basic";

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: import.meta.env.VITE_APP_BASE_URL,
  // 超时
  timeout: 10000
})

let loadingInstance = null //loading实例

//请求前拦截
service.interceptors.request.use(config => {
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false

  //获取token
  const { token } = useBasicStore()
  if(token && !isToken) {
    config.headers['token'] = token // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  //如果req.method给get 请求参数设置为 ?name=xxx
  if ('get'.includes(config.method?.toLowerCase())) config.params = config.data

  //req loading
  if (config.reqLoading ?? true) {
    loadingInstance = ElLoading.service({
      lock: true,
      fullscreen: true,
      // spinner: 'CircleCheck',
      text: '数据载入中...',
      background: 'rgba(0, 0, 0, 0.3)'
    })
  }

  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

//请求后拦截
service.interceptors.response.use(res => {
    //取消加载
    if (loadingInstance) {
      loadingInstance && loadingInstance.close()
    }

  // 未设置状态码则默认成功状态
  const code = res.data.code || 200
  // 获取错误信息
  const msg = errorCode[code] || res.data.msg || errorCode['default']
  if (code === 401) {
    // ElMessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
    //   confirmButtonText: '重新登录',
    //   cancelButtonText: '取消',
    //   type: 'warning'
    // })

    useBasicStore().resetStateAndToLogin()
    return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
  } else if (code === 500) {
    ElMessage({
      message: msg,
      type: 'error'
    })
    return Promise.reject(new Error(msg))
  } else if (code !== 200) {
    ElNotification.error({
      title: msg
    })
    return Promise.reject('error')
  } else { //code === 200
    // 把字符串total 转换成 数字 total
    if (res.data.data && res.data.data.total) {
      res.data.data.total = parseInt(res.data.data.total)
    }
    return res.data.data
  }
},
  error => {
    //取消加载
    if (loadingInstance) {
      loadingInstance && loadingInstance.close()
    }
    console.log('err' + error)
    let { message } = error
    if (message === 'Network Error') {
      message = '后端接口连接异常'
    } else if (message.includes('timeout')) {
      message = '系统接口请求超时'
    } else if (message.includes('Request failed with status code')) {
      message = '系统接口' + message.substr(message.length - 3) + '异常'
    }
    ElMessage({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service