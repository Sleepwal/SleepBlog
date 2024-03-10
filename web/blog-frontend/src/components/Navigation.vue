<template>
  <div class="base-contain-toc br-2">
    <div class="toc-card-header rowBS">
      <div>
        <span><el-icon><Expand /></el-icon></span>
        <span>目录</span>
      </div>
      <span class="progress">{{ progress }}</span>
    </div>

    <div class="toc-content">
      <div v-for="title in titles.list"
           :key="title.id"
           @click="scrollToView(title.scrollTop)"
           :class="[
                  'toc-item',
                  currentTitle.id === title.id ? 'active' : '',
              ]"
           :style="{ marginLeft: title.level * 20 + 'px' }"
           v-show="title.isVisible"
           :title="title.rawName">
        {{ title.name }}
      </div>
    </div>

  </div>
</template>

<script setup>
import {reactive, ref} from "vue";

const props = defineProps({
  mySelector: {
    type: String
  }
})

//目录
const titles = reactive({});
let currentTitle = reactive({});
const progress = ref(0);

function getTitles() {
  let titles = [];
  let levels = ["h1", "h2", "h3", "h4"];

  // console.log(props.mySelector)
  let articleElement = document.querySelector(props.mySelector);
  if (!articleElement) {
    return titles;
  }

  let elements = Array.from(articleElement.querySelectorAll("*"));

  // 调整标签等级
  let tagNames = new Set(
      elements.map((el) => el.tagName.toLowerCase())
  );
  for (let i = levels.length - 1; i >= 0; i--) {
    if (!tagNames.has(levels[i])) {
      levels.splice(i, 1);
    }
  }

  let serialNumbers = levels.map(() => 0);
  for (let i = 0; i < elements.length; i++) {
    const element = elements[i];
    let tagName = element.tagName.toLowerCase();
    let level = levels.indexOf(tagName);
    if (level === -1) continue;

    let id = tagName + "-" + element.innerText + "-" + i;
    let node = {
      id,
      level,
      parent: null,
      children: [],
      rawName: element.innerText,
      scrollTop: element.offsetTop,
    };

    if (titles.length > 0) {
      let lastNode = titles.at(-1);

      // 遇到子标题
      if (lastNode.level < node.level) {
        node.parent = lastNode;
        lastNode.children.push(node);
      }
      // 遇到上一级标题
      else if (lastNode.level > node.level) {
        serialNumbers.fill(0, level + 1);
        let parent = lastNode.parent;
        while (parent) {
          if (parent.level < node.level) {
            parent.children.push(node);
            node.parent = parent;
            break;
          }
          parent = parent.parent;
        }
      }
      // 遇到平级
      else if (lastNode.parent) {
        node.parent = lastNode.parent;
        lastNode.parent.children.push(node);
      }
    }

    serialNumbers[level] += 1;
    let serialNumber = serialNumbers.slice(0, level + 1).join(".");

    node.isVisible = node.parent == null;
    // node.name = serialNumber + ". " + element.innerText;
    node.name = element.innerText;
    titles.push(node);
  }

  return titles;
}

const updateTitles = () => {
  setTimeout(() => {
    titles.list = getTitles()
  }, 500)
}

defineExpose({
  updateTitles
})

// 监听滚动事件并更新样式
window.addEventListener("scroll",  () => {
  /*progress.value =
      parseInt(
          (window.scrollY / document.documentElement.scrollHeight) *
          100
      ) + "%";*/
  progress.value = parseInt(
    (window.scrollY / (document.documentElement.scrollHeight - window.innerHeight))
    * 100
  ).toFixed(0) + "%";

  let visibleTitles = [];
  let isGetCurrentTitle = false //已获取到当前标题，前面的不用展开

  for (let i = titles.list.length - 1; i >= 0; i--) {
    const title = titles.list[i];

    if (title.scrollTop <= window.scrollY) {

      if (currentTitle.id === title.id || isGetCurrentTitle) return

      if(!isGetCurrentTitle) {
        Object.assign(currentTitle, title)
        isGetCurrentTitle = true
      }

      // 展开节点
      setChildrenVisible(title, true);
      visibleTitles.push(title);

      // 展开父节点
      let parent = title.parent;
      while (parent) {
        setChildrenVisible(parent, true);
        visibleTitles.push(parent);
        parent = parent.parent;
      }

      // 折叠其余节点
      for (const t of titles.list) {
        if (!visibleTitles.includes(t)) {
          setChildrenVisible(t, false);
        }
      }
    }
  }
})

// 设置子节点的可见性
const setChildrenVisible = (title, isVisible) => {
  for (const child of title.children) {
    child.isVisible = isVisible;
  }
}

const scrollToView = (scrollTop) => {
  window.scrollTo({ top: scrollTop, behavior: "smooth" });
}

</script>

<style lang="scss">
.base-contain-toc {
  background: var(--grey-2);
  color: var(--grey-9);
  box-shadow: 0 3px 8px 6px rgba(7, 17, 27, 0.05);
  padding: 20px 24px;
  margin-top: 25px;
  box-sizing: border-box;
  width: 20vw;

  .toc-card-header {
    text-align: left !important;
    margin-bottom: 15px;
    .progress {
      color: var(--grey-9);
      font-style: italic;
      font-size: 140%;
    }
  }

  .toc-content {
    max-height: calc(80vh - 120px);
    overflow: auto;
    margin-right: -24px;
    padding-right: 20px;

    .toc-item {
      color: var(--grey-9);
      margin: 5px 0;
      line-height: 28px;
      cursor: pointer;
      font-size: 14px;
      padding: 2px 6px;
      display: -webkit-box;
      overflow: hidden;
      text-overflow: ellipsis;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;

      &:hover {
        color: #1892ff;
      }

    }
  }
  .active {
    background-color: rgba(122, 187, 211, 0.62);

    &:hover {
      background-color: rgba(122, 187, 211, 0.62);
    }
  }
}
</style>