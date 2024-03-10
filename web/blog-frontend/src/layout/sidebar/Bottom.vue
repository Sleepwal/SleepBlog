<template>
  <el-footer class="blog-footer rowBC">
    <div>
      <div><el-icon color="#409EFC"><Timer /></el-icon>Running time:
        <span class="footer-running-time">
          {{ runningTime }}
        </span>
      </div>
      <div class="">@Copyright 2023-{{new Date().getFullYear()}} 广东技术师范大学 SleepWalker</div>
      <div class="footer-info">
        <span>文章总浏览量: {{ visitCount }}</span>
        <span>用户访问量: {{ visitorCount }}</span>
      </div>
    </div>

    <div class="social-icons">
      <a v-for="link in socialMediaLinks" :key="link.name" :href="link.url">
        <el-image :src="link.img" class="br-1">123</el-image>
      </a>
    </div>
  </el-footer>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {Timer} from '@element-plus/icons-vue'
import { getAccessCount, getTotalViewCount } from "@/api/system";

const socialMediaLinks = ref([
  { name: 'bilibili', url: 'https://space.bilibili.com/156854398', img: 'https://pic2.zhimg.com/v2-58bd7241580b48ecc6aeed62dda2939b_1440w.jpg?source=172ae18b' },
  { name: '知乎', url: 'https://zhuanlan.zhihu.com/p/639105051', img: 'https://ts1.cn.mm.bing.net/th/id/R-C.f0bd97564b85fd953f0d3a416650ef32?rik=%2fSDTEtosZK4hIw&riu=http%3a%2f%2fwww.kuaipng.com%2fUploads%2fpic%2fw%2f2021%2f04-07%2f98963%2fwater_98963_698_698_.png&ehk=8l2O6L%2b3ARFH2w6EYU%2b57FSZ%2fh3noVqeAY4b4%2fZ2%2fr4%3d&risl=&pid=ImgRaw&r=0' },
  { name: 'GitHub', url: 'https://github.com/Sleepwal', img: 'https://ts1.cn.mm.bing.net/th/id/R-C.56080d12be395e2e46ec3ffe951d7201?rik=EKaRF8NImyHtHQ&riu=http%3a%2f%2fbpic.588ku.com%2felement_pic%2f00%2f25%2f58%2f9656d059a2d139b.jpg&ehk=CvLk413bia5qGV3%2bo9cWtjGpNP2jG62gPzmTkJFb54Q%3d&risl=&pid=ImgRaw&r=0' },
]);

const startTime = new Date('2023-05-14T11:50:00'); // 记录网站启动时间

const visitCount = ref(100); // 文章访问量
const visitorCount = ref(50); // 访客量
const loadCount = async () => {
  visitCount.value = await getTotalViewCount()
  visitorCount.value = await getAccessCount()
}
loadCount()

const runningTime = ref('')
const updateRunningTime = () => {
  const now = new Date();
  const diff = now.getTime() - startTime.getTime();
  const days = Math.floor(diff / (1000 * 60 * 60 * 24));
  const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
  const seconds = Math.floor((diff % (1000 * 60)) / 1000);
  runningTime.value = `${days} 天 ${hours} 时 ${minutes} 分 ${seconds} 秒`;
};

onMounted(() => {
  updateRunningTime();
  setInterval(updateRunningTime, 1000); // 每秒更新一次运行时间
});

</script>

<style lang="scss" scoped>
.blog-footer {
  //background-color: var(--grey-4);
  color: var(--grey-9);
  padding: 50px 25%;
  text-align: center;
  font-size: 18px;

  .social-icons {
    margin-top: 10px;

    .el-image {
      height: 30px;
      width: 30px;
      transition: transform .5s;

      &:hover {
        transform: scale(1.2); /* （120% 缩放 - 注意：如果缩放太大，它会超出视口） */
        cursor:pointer; //经过页面时，鼠标会变成一只手
      }
    }
    a {
      margin-right: 10px;
      color: var(--grey-9);
    }
  }
}

.footer-info span {
  margin-right: 10px;
}

.footer-running-time {
  color: var(--color-green);
}

</style>