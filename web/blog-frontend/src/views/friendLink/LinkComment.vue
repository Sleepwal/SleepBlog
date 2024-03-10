<template>
    <div class="comment-body br-2 p-2"
         id="c1" ref="comRef">
      <h2>评论区</h2>

      <div>
        <textarea v-model="text"
                  @keyup.enter="submit()"
                  placeholder="请输入评论"
                  style="width: 100%"/>
        <div>
          <span class="comment-btn"
                @click="submit()">发送评论</span>
        </div>
      </div>

      <div>
        <CommentList :comments="comments"
                     @addReply="addReply"></CommentList>
      </div>

<!--      <div class="jump-comment-button"
           @click="jumpToComment()">
        <el-icon><ChatSquare /></el-icon>
      </div>-->
    </div>
</template>

<script setup>
import {useSystemStore} from "@/stores/system";
import CommentList from "@/components/comment/com/CommentList.vue";
import {addComment, getLinkComment} from "@/api/comment";
import {ref} from "vue";
import {useBasicStore} from "@/stores/basic";

const system = useSystemStore()
const basic = useBasicStore()

const props = defineProps({
  articleId: {
    type: Number
  }
})

const text = ref()
const comments = ref([])
const loadCommentList = async (articleId) => {
  let res = await getLinkComment({
    pageNum: 1,
    pageSize: 5
  })

  comments.value = res.rows
}
loadCommentList()

const submit = async () => {
  console.log(text.value)
  await addComment({
    type: '1',
    content: text.value,
    createBy: basic.userInfo.id
  })

  await loadCommentList()
  text.value = ''
}

const addReply = (item, replyText) => {
  addComment({
    type: '1',
    rootId: item.id,
    content: replyText,
    replyCommentId: item.id,
    replyCommentUserId: item.createBy,
  })

  loadCommentList()
}

const comRef = ref()
const jumpToComment = () => {
  const element = document.getElementById('c1')
  if (element)
    element.scrollIntoView({ behavior: 'smooth' })
}

</script>

<style lang="scss" scoped>

.comment-body {
  background-color: var(--grey-2);

  h2 {
    @include custom-border-bottom(5%);
    margin-bottom: 10px;
  }

  .comment-btn {
    margin-top: 10px;
    @include my-btn;
  }

  .jump-comment-button {
    position: fixed;
    bottom: 10.6%;
    right: 3%;
    z-index: 10;

    @include my-btn;
  }
}


</style>