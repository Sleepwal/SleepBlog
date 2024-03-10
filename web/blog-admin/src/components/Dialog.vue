<template>
  <div>
    <el-dialog :show-close="showClose" :draggable="true"
               v-model="(show)" :close-on-click-modal="false"
               :title="title" custom-class="custom-dialog"
               :top="top" :width="width" >
      <div class="dialog-body py-2 rowCC">
        <slot></slot>
      </div>

      <template v-if="buttons && buttons.length > 0">
        <div class="dialog-buttons rowCC p-4">
          <el-button v-for="btn in buttons" round
                     :type="btn.type" @click="btn.click">
              {{btn.text}}
          </el-button>
          <el-button  @click="close" round>
            取消
          </el-button>
        </div>

      </template>
    </el-dialog>
  </div>
</template>

<script setup>

const props = defineProps({
  show: {
    type: Boolean,
    default: false
  },
  title: {
    type: String
  },
  showClose: {
    type: Boolean,
    default: false
  },
  showCancel: {
    type: Boolean,
    default: true
  },
  top: {
    type: String,
    default: "50px"
  },
  width: {
    type: String,
    default: "30%"
  },
  buttons: {
    type: Array
  }
})

const emit = defineEmits({})
const close = () => {
  emit("close")
}

</script>

<style lang="scss">
.el-dialog {
  border-radius: 20px;
}

.custom-dialog {

  .el-dialog__body {
    padding: 0;
  }

  .dialog-body {
    border-top: 2px solid #ddd;
    border-bottom: 2px solid #ddd;
    min-height: 80px;
  }

  .dialog-buttons {

  }
}
</style>