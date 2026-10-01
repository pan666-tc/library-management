import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('../views/Register.vue') },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/books',
    children: [
      { path: 'books', name: 'BookList', component: () => import('../views/BookList.vue') },
      { path: 'books/:id', name: 'BookDetail', component: () => import('../views/BookDetail.vue') },
      { path: 'search', name: 'BookSearch', component: () => import('../views/BookSearch.vue') },
      { path: 'history', name: 'BorrowHistory', component: () => import('../views/BorrowHistory.vue') },
      { path: 'profile', name: 'Profile', component: () => import('../views/Profile.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：没 token 跳登录页
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && to.path !== '/register' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
