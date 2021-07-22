import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from '../views/MainPage.vue'
import Search from '@/views/Search.vue'
import Login from '@/views/Accounts/Login.vue'
import Signup from '@/views/Accounts/Signup.vue'
import MyPage from '@/views/Accounts/Mypage.vue'
import Creatroom from '@/views/Creatroom.vue'


Vue.use(VueRouter)

const routes = [{
        path: '/main',
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
        component: Login
    },
    {
        path: '/signup',
        name: 'Signup',
        component: Signup
    },
    {
        path: '/mypage',
        name: 'Mypage',
        component: MyPage
    },
    {
        path: '/createroom',
        name: 'Creatroom',
        component: Creatroom
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router