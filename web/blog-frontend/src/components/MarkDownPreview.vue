<template>
  <div class="rowBS">
    <div class="markdown-content" v-html="parsedMarkdown"></div>

    <div class="toc">
      <el-affix offset="100">
        toc
        <ul>
          <li v-for="item in tocList" :key="item.text">
            <a :href="'#' + item.id" @click.prevent="scrollTo(item.id)">{{ item.text }}</a>
          </li>
        </ul>
      </el-affix>

    </div>
  </div>

</template>

<script>

export default {
  data() {
    return {
      markdown: '# Hello, world!\n',
      tocList: []
    }
  },
  mounted() {
    // 获取文章中的所有标题
    let contentDom = document.querySelector(".markdown-content") //获取正文
    const titles = contentDom.querySelectorAll('h1, h2, h3')

    // 遍历标题，生成目录列表
    titles.forEach((title) => {
      // 获取标题的文本和标签名
      const text = title.innerText
      const tagName = title.tagName.toLowerCase()

      // 生成目录项
      const item = {
        text: text,
        tagName: tagName,
        id: 'toc-' + this.tocList.length
      }
      this.tocList.push(item)

      // 在标题前添加锚点
      title.innerHTML = `<a id="${item.id}"></a>${text}`
    })
  },
  computed: {
    parsedMarkdown() {
      return this.parseMarkdown(this.markdown)
    }
  },
  methods: {
    // 解析md语法，返回HTML片段
    parseMarkdown(markdown) {
      let html = ''
      // 匹配标题
      markdown = markdown.replace(/^#\s(.+)$/gm, '<h1>$1</h1>')
      markdown = markdown.replace(/^##\s(.+)$/gm, '<h2>$1</h2>')
      markdown = markdown.replace(/^###\s(.+)$/gm, '<h3>$1</h3>')
      // 匹配列表
      markdown = markdown.replace(/^\*\s(.+)$/gm, '<li>$1</li>')
      markdown = markdown.replace(/^(-|\+){1}\s(.+)$/gm, '<li>$2</li>')
      markdown = markdown.replace(/^(\d{1,2}\.)\s(.+)$/gm, '<li>$2</li>')
      markdown = markdown.replace(/<\/li>\s?<li>/g, '</li><li>')
      markdown = `<ul>${markdown}</ul>`
      // 匹配强调和加粗
      markdown = markdown.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
      markdown = markdown.replace(/\*(.+?)\*/g, '<em>$1</em>')
      // 匹配链接和图片
      markdown = markdown.replace(/\[([^\]]+)\]\(([^)]+)\)/g, '<a href="$2">$1</a>')
      markdown = markdown.replace(/!\[([^\]]+)\]\(([^)]+)\)/g, '<img src="$2" alt="$1">')
      // 匹配换行和段落
      markdown = markdown.split('\n')
          .map(line => line.trim())
          .filter(line => line !== '')
          .join('</p><p>')
      markdown = `<p>${markdown}</p>`
      return markdown
    },
    scrollTo(id) {
      const element = document.getElementById(id)
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' })
      }
    }
  }
}
</script>

<style>
.markdown-content {
  /* 样式自行定义 */
}
</style>