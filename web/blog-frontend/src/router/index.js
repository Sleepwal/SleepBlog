import { createRouter, createWebHistory } from "vue-router";
import Framework from "@/layout/Framework.vue";
import Framework2 from "@/layout/Framework2.vue";
import WelcomeView2 from "@/views/welcome/WelcomeView2.vue";
import HomeFramework from "@/layout/HomeFramework.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/wel",
      name: "welcome",
      // component: WelcomeView,
      component: WelcomeView2,
      children: [
        {
          path: "/Login",
          name: "welcome-login",
          meta: { title: "登录" },
          // component: LoginPage,
          component: () => import("@/views/welcome/components/LoginPage.vue")
        }, {
          path: "/Register",
          name: "welcome-register",
          meta: { title: "注册" },
          // component: RegisterPage,
          component: () => import("@/views/welcome/components/RegisterPage.vue")
        }
      ]
    },
    {
      path: "/",
      redirect: "/index",
      name: "HomeFramework",
      component: HomeFramework,
      children: [
        {
          path: "index",
          name: "index",
          meta: { title: "首页", activePath: "/" },
          component: () => import("@/views/home/index.vue")
        }
      ]
    },
    {
      path: "/",
      name: "framework",
      redirect: "/index",
      component: Framework,
      children: [
        {
          path: "/test",
          name: "Test",
          component: () => import("@/views/Test.vue")
        },
        /*{
            path: 'index',
            name: 'index',
            meta: {title: "首页", activePath: '/'},
            component: () => import('@/views/home/index.vue')
        }, */
        {
          path: "ArticleDetail/:articleId",
          name: "ArticleDetail",
          meta: { title: "浏览文章", activePath: "/ArticleDetail" },
          component: () => import("@/views/article/ArticleDetail2.vue")
        },
        {
          path: "ArticleRelationChart",
          name: "ArticleRelationChart",
          meta: { title: "知识图谱", activePath: "/ArticleRelationChart" },
          component: () => import("@/views/article/ArticleRelationChart.vue")
        },

        {
          path: "category",
          name: "category",
          meta: { title: "分类", activePath: "/category" },
          component: () => import("@/views/category/Category.vue")
        }, {
          path: "category/:categoryId/:categoryName",
          name: "categoryDetail",
          meta: { title: "分类详情", activePath: "/category" },
          component: () => import("@/views/category/CategoryDetail.vue")
        },

        {
          path: "tag",
          name: "tag",
          meta: { title: "标签", activePath: "/tag" },
          component: () => import("@/views/tag/Tag.vue")
        }, {
          path: "tagDetail/:tagId/:tagName",
          name: "tagDetail",
          meta: { title: "标签详情", activePath: "/tag" },
          component: () => import("@/views/tag/TagDetail.vue")
        },

        {
          path: "about",
          name: "about",
          meta: { title: "关于", activePath: "/about" },
          component: () => import("@/views/about/About.vue")
        },

        {
          path: "friendLink",
          name: "friendLink",
          meta: { title: "友链", activePath: "/friendLink" },
          component: () => import("@/views/friendLink/FriendLink.vue")
        },

        {
          path: "toolsIndex",
          name: "toolsIndex",
          meta: { title: "工具浏览", activePath: "/toolsIndex" },
          component: () => import("@/views/tools/ToolsIndex.vue")
          /*children: [
              {
                  path: 'ipScan',
                  name: 'ipScan',
                  meta: {title: "IP扫描", activePath: '/toolsIndex'},
                  component: () => import('@/views/tools/com/IpScan.vue')
              },{
                  path: 'portScan',
                  name: 'portScan',
                  meta: {title: "IP扫描", activePath: '/toolsIndex'},
                  component: () => import('@/views/tools/com/PortScan.vue')
              },{
                  path: 'PingTest',
                  name: 'PingTest',
                  meta: {title: "ping测试", activePath: '/toolsIndex'},
                  component: () => import('@/views/tools/com/PingTest.vue')
              },{
                  path: 'Base64',
                  name: 'Base64',
                  meta: {title: "Base64编解码", activePath: '/toolsIndex'},
                  component: () => import('@/views/tools/com/Base64.vue')
              },{
                  path: 'MuYun',
                  name: 'MuYun',
                  meta: {title: "电子木鱼", activePath: '/toolsIndex'},
                  component: () => import('@/views/tools/com/MuYu.vue')
              },
          ]*/
        },

        {
          path: "TimeLineArticle",
          name: "TimeLineArticle",
          meta: { title: "归档", activePath: "/TimeLineArticle" },
          component: () => import("@/views/timeLine/TimeLineArticle.vue")

        },

        {
          path: "UserSetting",
          name: "UserSetting",
          meta: { title: "个人设置", activePath: "/UserSetting" },
          component: () => import("@/views/user/UserSetting.vue")

        },

        {
          path: "ChatRoom",
          name: "ChatRoom",
          meta: { title: "聊天室", activePath: "/ChatRoom" },
          component: () => import("@/views/chatRoom/ChatRoom.vue")

        }
      ]
    },
    {
      path: "/KnowledgeBase",
      redirect: "/KnowledgeBase/index",
      component: Framework,
      children: [
        {
          path: "index",
          name: "KnowledgeBase",
          meta: { title: "知识库", activePath: "/KnowledgeBase" },
          component: () => import("@/views/KnowledgeBase/index.vue")
        }]
    },
    {
      path: "/M",
      component: Framework2,
      children: [
        {
          path: "/Message",
          name: "Message",
          meta: { title: "留言", activePath: "/Message" },
          component: () => import("@/views/message/Message.vue")
        }
      ]
    },
    {
      path: "/OnlyArticle/:articleId",
      name: "OnlyArticle",
      meta: { title: "仅文章", activePath: "/ArticleDetail" },
      component: () => import("@/views/article/OnlyArticle.vue")
    }

  ]
});

router.beforeEach((to, form, next) => {
  document.title = to.meta.title; //替换标题

  //路由跳转时定位到页面顶部的方法
  document.body.scrollTop = 0; // chrome
  document.documentElement.scrollTop = 0; // firefox
  window.pageYOffset = 0; // safari

  next();
});

export default router;
