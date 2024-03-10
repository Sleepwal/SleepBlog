import Layout from '@/layout/index.vue'
const BasicDemo = {
  path: '/basic-demo',
  component: Layout,
  meta: { title: 'Basic Demo', icon: 'eye-open' },
  alwaysShow: true,
  children: [
    {
      path: 'hook',
      component: () => import('@/views/basic-demo/hook/index.vue'),
      name: 'Hook',
      meta: { title: 'Hook' }
    },
    {
      path: 'pinia',
      component: () => import('@/views/basic-demo/pinia/index.vue'),
      name: 'Pinia',
      meta: { title: 'Pinia' }
    },
    {
      path: 'mock',
      component: () => import('@/views/basic-demo/mock/index.vue'),
      name: 'Mock',
      meta: { title: 'Mock' }
    },
    {
      path: 'svg-icon',
      component: () => import('@/views/basic-demo/svg-icon/index.vue'),
      name: 'SvgIcon',
      meta: { title: 'Svg Icon' }
    },
  ]
}

export default BasicDemo
