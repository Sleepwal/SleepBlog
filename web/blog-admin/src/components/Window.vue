<template>
  <div class="window" v-if="show">
    <div class="window-title rowSC">
      <span @click="close" class="window-title-back">
        <el-icon><Back /></el-icon>
      返回
      </span>

    </div>
    <div class="window-dody">
      <slot></slot>
    </div>
    <div class="window-footer">
      <template v-if="buttons && buttons.length > 0">
        <div class="dialog-buttons rowCC p-4">
          <el-button v-for="btn in buttons"
                     :type="btn.type" @click="btn.click">
            {{btn.text}}
          </el-button>
          <el-button  @click="close">
            取消
          </el-button>
        </div>

      </template>
    </div>

  </div>
</template>

<script setup>

const props = defineProps({
  show: {
    type: Boolean,
    default: false
  },
  buttons: {
    type: Array
  },
  showCancel: {
    type: Boolean,
    default: true
  },
})

const emit = defineEmits(["close", "closeCallback"])
const close = () => {
  emit("close")
  emit("closeCallback")
}

</script>

<style lang="scss">
.window {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: calc(100vh - 80px);
  background-color: whitesmoke;
  z-index: 20;

  .window-title {
    height: 30px;

    .window-title-back:hover {
      cursor: pointer;
    }
  }
  .window-dody {
    height: calc(100vh - 165px);
    padding: 10px;
  }
  .window-footer {

  }
}
</style>