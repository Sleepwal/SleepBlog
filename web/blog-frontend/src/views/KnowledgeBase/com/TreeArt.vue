<template>
  <div :key="art.id" @click="changeDoc(art)"
       :class="[
                    'art-container',
                      currentArt.id === art.id ? 'active' : '',
                    ]">
    <el-icon><Document /></el-icon>
    {{art.title}}
  </div>
</template>

<script setup>
import {inject, provide, reactive, ref} from "vue";
import {
  Document,
} from '@element-plus/icons-vue'
import {useKnowStore} from "@/stores/knowledge";

const props = defineProps({
  art: {
  },
  docId: {

  }
})

const knowStore = useKnowStore()

//注入
const changeArt = inject('changeArt')
const currentArt = inject('currentArt')
const currentDoc = inject('currentDoc')

const changeDoc = art => {
  changeArt(art)
  currentArt.id = art.id
  currentDoc.id = props.docId

  knowStore.knowledgeInfo.selectDocumentIndex = currentDoc.id
}
</script>

<style lang="scss" scoped>
.art-container {
  padding-top: 5px;
  cursor: pointer;

  &:hover {
    color: var(--color-blue);
  }

}
.active {
  color: var(--color-blue);
}
</style>