import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from '../views/MainPage.vue'
import Search from '@/views/Search.vue'
import Login from '@/views/Accounts/Login.vue'
import Signup from '@/views/Accounts/Signup.vue'
import Mypage from '@/views/Accounts/Mypage.vue'
import Creatroom from '@/views/Creatroom.vue'
import Room from '@/views/Room'


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
        params: 'my',
        // meta: { requiresAuth: true } //로그인시 가능
    },
    {
        path: '/createroom',
        name: 'Creatroom',
        component: Creatroom,
        // meta: { requiresAuth: true } //로그인시 가능
    },
    {
        path: '/gameroom/:roomid',
        name: 'Room',
        component: Room,
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router