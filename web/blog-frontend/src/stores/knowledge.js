import {defineStore} from 'pinia'
import {reactive} from "vue";

export const useKnowStore = defineStore("knowledge", () => {
    const knowledgeInfo  = reactive({
        selectCateIndex: 1,
        selectDocumentIndex: 1,
        selectIndex: 1
    })
    function getKnowledgeInfo() {
        return knowledgeInfo
    }
    function setKnowledgeInfo(data) {
        const { selectCateIndex, selectDocumentIndex, selectIndex } = data
        knowledgeInfo.selectCateIndex = selectCateIndex
        knowledgeInfo.selectDocumentIndex = selectDocumentIndex
        knowledgeInfo.selectIndex = selectIndex
    }


    return { knowledgeInfo, getKnowledgeInfo, setKnowledgeInfo }
}, {
    persist: {
        enable: true,
    }
})