<template>
  <div class="comment-body br-2 p-2"
       id="c1" ref="comRef">
    <h2>评论区</h2>

    <div class="rowCC">
        <el-input
            v-model="text"
            @keyup.ctrl.enter="submit()"
            type="textarea"
            :autosize="{ minRows: 2 }"
            placeholder="请输入评论, ctrl+enter发送"
            show-word-limit
            maxlength="50"
        />
      <div>
          <span class="comment-btn rowCC"
                @click="submit()">发送评论</span>
      </div>
    </div>

    <div>
      <CommentList :comments="comments"
                   @addReply="addReply"></CommentList>
    </div>
  </div>
</template>

<script setup>
import { useSystemStore } from "@/stores/system";
import CommentList from "@/components/comment/com/CommentList.vue";
import { addComment, getCommentList } from "@/api/comment";
import { ref } from "vue";
import { useBasicStore } from "@/stores/basic";
import { ElMessage } from "element-plus";

const system = useSystemStore();
const basic = useBasicStore();

const props = defineProps({
  articleId: {
    type: Number
  }
});

const text = ref();
const comments = ref([]);
const loadCommentList = async (articleId) => {
  let res = await getCommentList({
    articleId: articleId,
    pageNum: 1,
    pageSize: 5
  });

  comments.value = res.rows;
};
// 主动暴露childMethod方法
defineExpose({ loadCommentList });

const submit = async () => {
  console.log(text.value);
  await addComment({
    articleId: props.articleId,
    content: text.value,
    createBy: basic.userInfo.id
  });

  await loadCommentList(props.articleId);
  text.value = "";
};

const addReply = (item, replyText) => {
  addComment({
    articleId: props.articleId,
    rootId: item.id,
    content: replyText,
    replyCommentId: item.id,
    replyCommentUserId: item.createBy
  });

  loadCommentList(props.articleId);
};

const comRef = ref();

</script>

<style lang="scss" scoped>

.comment-body {
  background-color: var(--grey-2);

  h2 {
    @include custom-border-bottom(5%);
    margin-bottom: 10px;
  }

  .comment-btn {
    width: 80px;
    height: 50px;
    @include my-btn;
  }

  .jump-comment-button {
    position: fixed;
    bottom: 8%;
    right: 4.5%;
    z-index: 10;

    @include my-btn;
  }
}


</style>