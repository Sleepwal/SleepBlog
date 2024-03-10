<template>
    <div class="doc-container br-1" @mouseover="hoverDoc" @mouseleave="leaveDoc">
      <div class="rowBC">
        <div>
          <h2>
            <el-icon><Folder /></el-icon>
            {{doc.title}}
          </h2>

          <div class="doc-description">
            {{doc.description}}
          </div>
        </div>

        <div>
          <el-tag type="success" class="rowCC">
            {{doc.createTime.substring(0, 4)}}
          </el-tag>
        </div>
      </div>

      <el-collapse-transition>
        <div v-show="showArt || currentDoc.id === doc.id">
          <TreeArt  v-for="art in doc.children"
                    :art="art" :doc-id="doc.id"></TreeArt>
        </div>

<!--        <div v-show="showArt">
          <div v-for="art in doc.children"
               :key="art.id" @click="changeDoc(art)"
               :class="[
                          'art-container',
                          currentArt.id === art.id ? 'active' : '',
                          ]">
            <el-icon><Document /></el-icon>
            {{art.title}}
          </div>
        </div>-->
      </el-collapse-transition>
    </div>
</template>

<script setup>
import {inject, reactive, ref, watch} from "vue";
import {getKnowledgeDetail} from "@/api/knowledge";
import TreeArt from "@/views/KnowledgeBase/com/TreeArt.vue";
import {Folder} from "@element-plus/icons-vue"

const props = defineProps({
  doc: {
  },
})

const currentDoc = inject('currentDoc')

const showArt = ref(false)

const hoverDoc = () => {
  showArt.value = true
}
const leaveDoc = () => {
  showArt.value = false
}

</script>

<style lang="scss" scoped>
.doc-container {
  border-bottom: 2px solid black;
  background-color: var(--grey-3);
  margin: 5px;
  box-shadow: 0 3px 8px 6px var(--grey-9-a5);
  padding: 5px;

  .doc-title {}
  .doc-description {
    font-style: italic;
    padding-bottom: 5px;
  }

}


  //.art-container {
  //  padding-top: 5px;
  //  cursor: pointer;
  //  &:hover {
  //    color: deepskyblue;
  //  }
  //}
  //.active {
  //  color: deepskyblue;
  //}

</style>