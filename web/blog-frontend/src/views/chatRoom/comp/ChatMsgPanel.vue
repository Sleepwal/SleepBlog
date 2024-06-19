<template>
  <div class="chat-msg-body">
    <el-scrollbar
      height="70vh"
      class="p-1">
      <div
        v-for="(item, index) in receiveMessage"
        :key="index"
        ref="msgListRef"
        class="msg-item slideLeftInAnimation"
        :style="{
          flexDirection:
            item.name === form.nickname ? 'row-reverse' : 'row',
        }">
        <el-avatar v-if="item.name === form.nickname"
                   :src="userInfo.avatar" />
        <el-avatar v-else
                   :src="item.avatar === undefined ? avatarList[index % 7] : item.avatar" />

        <div>
          <div class="columnBE" v-if="item.name === form.nickname">
            <strong>
              {{ item.name }}
            </strong>
          </div>
          <div v-else>
            <strong>
              {{ item.name }}
            </strong>
          </div>

          <div class="frame-right" v-if="item.name === form.nickname">
            <div class="triangle-right"></div>
            <span class="content-right">{{ item.content }}</span>
          </div>

          <div class="frame" v-else>
            <div class="triangle"></div>
            <span class="content">{{ item.content }}</span>
          </div>

          <div>{{ item.time }}</div>
        </div>

      </div>
    </el-scrollbar>

    <div class="chat-send p-1">
      <textarea
        v-model="form.message"
        @keyup.enter="broadcast"
        placeholder="输入回车键发送"
        class="input-msg"
      />

      <div class="rowBC">
        <span></span>
        <span>
          <button
            class="send-button"
            @click="broadcast">
            发送
          </button>
          <!--          <button class="send-button" @click="online">上线</button>-->
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import a1 from "@/assets/avatars/a1.jpg";
import a2 from "@/assets/avatars/a2.jpg";
import a3 from "@/assets/avatars/a3.jpg";
import a4 from "@/assets/avatars/a4.jpg";
import a5 from "@/assets/avatars/a5.jpg";
import a6 from "@/assets/avatars/a6.jpg";
import { nextTick, onMounted, reactive, ref, watch } from "vue";
import { useBasicStore } from "@/stores/basic";
import { addChatMessage, getChatList } from "@/api/chat";

const avatarList = [a1, a2, a3, a4, a5, a6];

const userInfo = useBasicStore().getUserInfo();

const form = reactive({
  nickname: userInfo.nickname,
  code: "",
  message: ""
});

const socket = new WebSocket("ws://127.0.0.1:8083");
const receiveMessage = ref([]);

const doGetChatList = async () => {
  const resp = await getChatList();
  // console.log("resp = ", resp);
  // console.log("receiveMessage = ", receiveMessage.value);
  receiveMessage.value.push(...resp);
};
doGetChatList();

const connectWebSocket = () => {
  socket.addEventListener("open", (event) => {
    sendMessage({
      nickname: form.nickname,
      code: 10003
    });
  });

  socket.addEventListener("message", (event) => {
    console.log("WebSocket message received:", event.data);

    const resp = JSON.parse(event.data);
    receiveMessage.value.push(resp);
  });

  socket.addEventListener("close", (event) => {
    console.log("WebSocket closed:", event);
  });

  socket.addEventListener("error", (event) => {
    console.error("WebSocket error:", event);
  });
};
const broadcast = () => {
  addChatMessage({
    id: userInfo.id,
    content: form.message
  });

  sendMessage({
    id: userInfo.id,
    nickname: form.nickname,
    code: 10002,
    type: 3,
    content: form.message
  });

  form.message = "";
};

const online = () => {
  sendMessage({
    nickname: form.nickname,
    code: 10001
  });
};

const sendMessage = (data) => {
  if (socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify(data));
  } else {
    console.error("WebSocket not open.");
  }
};

onMounted(() => {
  connectWebSocket();
});

const msgListRef = ref();
watch(
  () => receiveMessage.value,
  (newVal) => {
    nextTick(() => {
      msgListRef.value[newVal.length - 1].scrollIntoView({
        behavior: "smooth"
      }); // 关键代码
    });
  },
  {
    deep: true
  }
);

</script>

<style lang="scss" scoped>
.chat-msg-body {

  .msg-item {
    display: flex;
    align-items: center;

    .content-frame {
      border: 1px solid black;
    }

    .frame {
      width: 100%;
      float: left;
      position: relative;
      padding: 0 0 0 5px;
    }

    .triangle {
      width: 0;
      height: 0;
      border-top: 5px solid transparent;
      border-bottom: 5px solid transparent;
      border-right: 5px solid var(--color-cyan-light);
      position: absolute;
      left: 0;
      top: 6px;
    }

    .content {
      float: left;
      padding: 7px 10px;
      background-color: var(--color-cyan-light);
      font-size: 30px;
      border-radius: 5px;
      max-width: 50vw;
      word-break: break-all;
    }

    .frame-right {
      width: 100%;
      float: left;
      position: relative;
      padding: 0 5px 0 0;
    }

    .triangle-right {
      width: 0;
      height: 0;
      border-top: 5px solid transparent;
      border-bottom: 5px solid transparent;
      border-left: 5px solid var(--color-pink-light);
      position: absolute;
      right: 0;
      top: 6px;
    }

    .content-right {
      float: right;
      padding: 7px 10px;
      background-color: var(--color-pink-light);
      font-size: 30px;
      border-radius: 2px;
      max-width: 50vw;
      word-break: break-all;
    }
  }

  .chat-send {
    border-top: 1px solid #c7c1c1;

    label {
      display: block;
      margin-bottom: 8px;
      font-weight: bold;
    }

    .input-msg {
      width: 100%;
      overflow: hidden;
      box-sizing: border-box;
      resize: none;
      outline: none;
      border: none;
      font-size: 15px;
      background-color: transparent;
    }

    .send-button {
      background-color: #4caf50;
      color: white;
      border: none;
      padding: 10px 15px;
      font-size: 16px;
      margin: 4px 2px;
      cursor: pointer;
      border-radius: 5px;
    }
  }
}
</style>
