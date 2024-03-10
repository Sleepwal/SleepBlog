import { createRouter, createWebHashHistory } from "vue-router";
import Layout from "@/layout/index.vue";

export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404.vue'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        //using el svg icon, the elSvgIcon first when at the same time using elSvgIcon and icon
        meta: { title: 'Dashboard', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/setting-switch',
    component: Layout,
    hidden: true,
    meta: { title: 'Setting Switch', elSvgIcon: 'Fold' },
    children: [
      {
        path: 'index',
        component: () => import('@/views/setting-switch/index.vue'),
        name: 'SettingSwitch',
        meta: { title: 'Setting Switch', elSvgIcon: 'example' }
      }
    ]
  },
  {
    path: '/a',
    component: Layout,
    children: [
      {
        path: '/Commit',
        name: 'Commit',
        component: () => import('@/views/commit/index.vue'),
        meta: { title: '发布文章', icon: 'form'}
      }
    ]
  },
  {
    path: '/Article',
    component: Layout,
    redirect: '/Article/manage',
    meta: { title: '文章', elSvgIcon: 'Folder' },
    children: [
      {
        path: 'manage',
        component: () => import('@/views/article/index.vue'),
        name: 'manage',
        meta: { title: '文章管理', elSvgIcon: 'Edit'}
      },{
        path: 'view/:articleId/:type',
        component: () => import('@/views/article/ArticlePreView.vue'),
        hidden: true,
        name: 'PreView',
        meta: { title: '文章预览', elSvgIcon: 'example' }
      },{
        path: 'edit/:articleId/:type',
        component: () => import('@/views/article/ArticleAddEdit.vue'),
        hidden: true,
        name: 'ArticleAddEdit',
        meta: { title: '文章编辑', elSvgIcon: 'Edit' }
      },{
        path: 'category',
        component: () => import('@/views/article/Category.vue'),
        name: 'category',
        meta: { title: '分类管理', elSvgIcon: 'Collection' }
      },{
        path: 'Tag',
        name: 'Tag',
        component: () => import('@/views/tag/index.vue'),
        meta: { title: '标签管理', icon: 'guide'}
      },{
        path: 'ArticleComment',
        name: 'ArticleComment',
        component: () => import('@/views/article/ArticleComment.vue'),
        meta: { title: '评论管理', icon: 'message'}
      },
    ]
  },
  {
    path: '/knowledge',
    component: Layout,
    meta: { title: '知识库', elSvgIcon: 'Notebook' },
    children: [
      {
        path: 'KnowCategory',
        name: 'KnowCategory',
        component: () => import('@/views/knowledge/KnowCategory.vue'),
        meta: { title: '类型管理', elSvgIcon: 'Memo'}
      },{
        path: 'DocAndArticle',
        name: 'DocAndArticle',
        component: () => import('@/views/knowledge/DocAndArticle.vue'),
        meta: { title: '文档和文章管理', elSvgIcon: 'Document'}
      },
    ]
  },
  {
    path: '/user',
    component: Layout,
    meta: { title: '用户', elSvgIcon: 'user' },
    children: [
      {
        path: 'MySetting',
        name: 'MySetting',
        component: () => import('@/views/user/MySetting.vue'),
        meta: { title: '个人设置', icon: 'setting'}
      },{
        path: 'UserManager',
        name: 'UserManager',
        component: () => import('@/views/user/UserManager.vue'),
        meta: { title: '用户管理', icon: 'user'}
      },
    ]
  },
  {
    path: '/a',
    component: Layout,
    children: [
      {
        path: '/message',
        name: '/message',
        component: () => import('@/views/message/index.vue'),
        meta: { title: '留言管理', icon: 'message'}
      }
    ]
  },
  {
    path: '/FriendLink',
    component: Layout,
    meta: { title: '友链', icon: 'link'},
    children: [
      {
        path: 'Manager',
        name: 'Manager',
        component: () => import('@/views/friendLink/index.vue'),
        meta: { title: '友链管理', icon: 'position'}
      },
      {
        path: 'LinkComment',
        name: 'LinkComment',
        component: () => import('@/views/friendLink/LinkComment.vue'),
        meta: { title: '评论管理', icon: 'message'}
      },
    ]
  },
  {
    path: '/SystemLog',
    component: Layout,
    meta: { title: '日志', icon: 'list'},
    children: [
      {
        path: 'SystemLog',
        name: 'SystemLog',
        component: () => import('@/views/systemLog/index.vue'),
        meta: { title: '日志管理', icon: 'list'}
      },
    ]
  },

  /*{
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/ArticleComment.vue'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1/ArticleComment.vue'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2/ArticleComment.vue'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1/ArticleComment.vue'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2/ArticleComment.vue'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3/ArticleComment.vue'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/ArticleComment.vue'),
        name: 'Menu2',
        meta: { title: 'menu2' }
      }
    ]
  },*/
  // basicDemo,
]

//配置
export const settingRoutes = [

]

//角色和code数组动态路由
export const roleCodeRoutes = [

]
/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // 404 page must be placed at the end !!!
  { path: '/:catchAll(.*)', name: 'CatchAll', redirect: '/404', hidden: true },
]

const router = createRouter({
  history: createWebHashHistory(),
  scrollBehavior: () => ({ top: 0 }),
  routes: constantRoutes
})

export default router
