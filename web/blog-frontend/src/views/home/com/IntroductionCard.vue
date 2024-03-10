<template>
  <div class="introduction-card br-2 p-1">
    <div class="card-sections p-1 br-1 rowCC">
<!--      <div class="rowCC">-->
        <img class="avatar"
             :src="avatar" alt="Avatar" />

        <div class="right-section">
          <div class="username rowCC">{{ username }}</div>

          <div class="intro-section rowCC">
            {{ intro }}
          </div>

          <div class="stats-section rowCC br-1">
            <div class="stat-item">
              <div class="stat-label">文章</div>
              <div class="stat-value">{{ info.articleCount }}</div>
            </div>
            <div class="stat-item">
              <div class="stat-label">分类</div>
              <div class="stat-value">{{ info.categoryCount }}</div>
            </div>
            <div class="stat-item">
              <div class="stat-label">标签</div>
              <span class="stat-value">{{ info.tagCount }}</span>
            </div>
            <div class="stat-item">
              <div class="stat-label">评论</div>
              <div class="stat-value">{{ info.commentCount }}</div>
            </div>
          </div>

          <div class="social-links rowCC">
            <a v-for="link in socialMediaLinks" :key="link.name" :href="link.url">
              <el-image :src="link.img" class="br-1">123</el-image>
            </a>
          </div>
        </div>
<!--      </div>-->

    </div>

  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import myAvatar from "@/assets/avatar.jpg"
import {getBlogInfoCount} from "@/api/system";

const username = ref('SleepWalker')
const intro = ref("I'm a sleepwalker")
const info = reactive({
  articleCount: 0,
  categoryCount: 0,
  tagCount: 0,
  commentCount: 0,
})

const avatar = myAvatar
const socialMediaLinks = [
    { name: 'bilibili', url: 'https://space.bilibili.com/156854398', img: 'https://pic2.zhimg.com/v2-58bd7241580b48ecc6aeed62dda2939b_1440w.jpg?source=172ae18b' },
    { name: '知乎', url: 'https://zhuanlan.zhihu.com/p/639105051', img: 'https://ts1.cn.mm.bing.net/th/id/R-C.f0bd97564b85fd953f0d3a416650ef32?rik=%2fSDTEtosZK4hIw&riu=http%3a%2f%2fwww.kuaipng.com%2fUploads%2fpic%2fw%2f2021%2f04-07%2f98963%2fwater_98963_698_698_.png&ehk=8l2O6L%2b3ARFH2w6EYU%2b57FSZ%2fh3noVqeAY4b4%2fZ2%2fr4%3d&risl=&pid=ImgRaw&r=0' },
    { name: 'GitHub', url: 'https://github.com/Sleepwal', img: 'https://ts1.cn.mm.bing.net/th/id/R-C.56080d12be395e2e46ec3ffe951d7201?rik=EKaRF8NImyHtHQ&riu=http%3a%2f%2fbpic.588ku.com%2felement_pic%2f00%2f25%2f58%2f9656d059a2d139b.jpg&ehk=CvLk413bia5qGV3%2bo9cWtjGpNP2jG62gPzmTkJFb54Q%3d&risl=&pid=ImgRaw&r=0' },
]

const loadBlogInfoCount = async () => {
  let res = await getBlogInfoCount()
  Object.assign(info, res)
}
loadBlogInfoCount()

</script>

<style lang="scss" scoped>
.introduction-card {
  //background: linear-gradient(to left, #c31432, #240b36);
  margin-bottom: 30px;
  position: relative;
  //background-image: linear-gradient(144deg,#AF40FF, #5B42F3 50%,#00DDEB);
  //transition: 0.3s;

  //&:hover {
  //  transform: translateY(-10px);
  //}

  .card-sections {
    //background: var(--grey-1-a7);
    background: linear-gradient(to left, #c31432, #240b36);
    box-shadow: rgba(151, 65, 252, 0.2) 0 15px 30px -5px;
    position: relative;
    transition: all 0.6s cubic-bezier(0.23, 1, 0.320, 1);
    overflow: hidden;

    &:hover {
      .right-section {
        transform: translate(-50%, -50%) rotate(0deg);
        opacity: 1;
      }

      .avatar {
        scale: 0;
        transform: rotate(-45deg);
      }
    }

    .avatar {
      width: 70%;
      //height: 200px;
      border-radius: 10%;
      //margin-right: 10px;
      transition: all 0.6s cubic-bezier(0.23, 1, 0.320, 1);

      &:hover {
        transform: rotate(-5deg) scale(1.1);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
      }
    }

    .right-section {
      background-color: var(--grey-1-a7);
      color: var(--grey-9);
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%) rotate(-45deg);
      width: 100%;
      height: 100%;
      padding: 20px;
      box-sizing: border-box;
      opacity: 0;
      transition: all 0.6s cubic-bezier(0.23, 1, 0.320, 1);

      .username {
        font-size: 18px;
        font-weight: bold;
        //@include custom-border-bottom(30%);
      }

      .intro-section {
        margin-bottom: 10px;
      }

    }

    .stats-section {
      background: var(--grey-1-a7);
      margin-bottom: 10px;
      flex-wrap: wrap;

      .stat-item {
        margin-right: 20px;
        .stat-label {
          font-weight: bold;
          @include flex-center;
        }

        .stat-value {
          font-size: 25px;
          height: 32px;
          color: var(--color-green);

          @include flex-center;
        }
      }
    }

    .social-links {

      .el-image {
        height: 35px;
        width: 35px;
        margin-right: 10px;
        transition: transform .5s;

        &:hover {
          transform: scale(1.2); /* （120% 缩放 - 注意：如果缩放太大，它会超出视口） */
          cursor:pointer; //经过页面时，鼠标会变成一只手
        }
      }
    }

  }
}


</style>
