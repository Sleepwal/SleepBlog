<template>
  <div class="comment-list">
    <div v-for="comment in comments" :key="comment.id"
         class="comment-item">
      <div class="rowSS">
        <el-avatar :size="50" :src="comment.avatar"
                   class="comment-item-avatar m-1"/>

        <div class="comment-content">
          <div class="rowBC">
            <div class="comment-author">{{ comment.nickname }}</div>
            <div> {{ timeParse(comment.createTime) }}</div>
            <!--                        <div> {{ comment.createTime }}</div>-->
          </div>

          <div class="comment-text p-1 br-1">
            {{ comment.content }}
          </div>

          <div v-if="isShowReply">
            <span class="comment-reply">
                <el-button
                    text
                    type="danger"
                    @click="showReply(comment)"
                >回复
                </el-button>
            </span>
          </div>

          <div v-if="comment.isReply">
            <textarea v-model="comment.replyText"
                      placeholder="请输入评论"
                      style="width: 100%" @keyup.enter="addReply(comment)"/>
            <div>
              <span class="comment-btn"
                    @click="addReply(comment)">
                发送评论
              </span>
            </div>
          </div>
        </div>
      </div>

      <div class="comment-children">
        <CommentList v-if="comment.children"
                     :comments="comment.children"
                     :is-show-reply="false"
                     @addReply="addReply"/>
      </div>
    </div>

    <el-divider/>
  </div>
</template>

<script setup>

const props = defineProps({
  comments: {
    type: Array,
    default: []
  },
  isShowReply: {
    type: Boolean,
    default: true
  }
});

const showReply = (item) => { //显示回复框
  item.isReply = !item.isReply;
};

const emit = defineEmits(["addReply"]);

const addReply = (item) => {
  const replyText = item.replyText;

  emit("addReply", item, replyText);
  item.isReply = false;
  item.replyText = "";
  // console.log(item, replyText)
};

const timeParse = (date) => {
  const now = Date.now();
  const old = new Date(date);
  const minute = ((now - old) / 1000 / 60).toFixed(0);
  const hour = (minute / 60).toFixed(0);
  const day = (hour / 24).toFixed(0);

  console.log("日期：" + day)

  if (minute === "0") return "刚刚";
  if (day >= 2) return old.toLocaleString()
  return day === "0" ? (hour === "0" ? minute + "分钟前" : hour + "小时前") : day + "天前";
};


</script>

<style lang="scss" scoped>
.comment-list {
  margin-top: 20px;

  .comment-item {
    margin-bottom: 10px;

    .comment-item-avatar {

      &:hover {
        transition: .5s;
        transform: rotate(720deg)
      }
    }

    .comment-content {
      flex: 1;

      .comment-author {
        font-weight: bold;
      }

      .comment-text {
        //background: var(--grey-3);
      }

      .comment-btn {
        margin-top: 10px;
        @include my-btn;
      }
    }

    .comment-children {
      margin-left: 40px;
    }
  }
}

</style>
