import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from '../views/MainPage.vue'
import Search from '@/views/Search.vue'
import Login from '@/views/Accounts/Login.vue'
import Signup from '@/views/Accounts/Signup.vue'
import Mypage from '@/views/Accounts/Mypage.vue'
import Creatroom from '@/views/Creatroom.vue'


Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'MainPage',
        component: MainPage
    },
    {
        path: '/search',
        name: 'Search',
        component: Search
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        
    },
    {
        path: '/signup',
        name: 'Signup',
        component: Signup,
    },
    {
        path: '/mypage',
        name: 'Mypage',
        component: Mypage,
        meta: { requiresAuth: true }//로그인시 가능
    },
    {
        path: '/createroom',
        name: 'Creatroom',
        component: Creatroom,
        meta: { requiresAuth: true }//로그인시 가능
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
//로컬 스토리지 체크
const loggedIn = localStorage.getItem('user')
console.log(to)
//requiresAuth 체크
if (to.matched.some(record => record.meta.requiresAuth)) {
    // 로그인 상태가 아니면 로그인으로 보내버린다.
    if (!loggedIn) {
      next('/login');
      return;
    }
    next();
  }
  // requiresAuth가 false일때 즉, 권한이 필요 없는 페이지 일때
  next();
});

export default router