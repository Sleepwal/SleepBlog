const system = {
  url: '/mock/login',
  method: 'post',
  response: () => {
    return {
      code: 200,
      data: {
        token:"666666"
      },
      msg: "操作成功",
    }
  }
}

const loginOut = {
  url: '/mock/loginOut',
  method: 'post',
  response: () => {
    return {
      code: 200,
      title: 'mock请求测试'
    }
  }
}

export default [
  system,loginOut
]
