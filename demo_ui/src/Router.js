import VueRouter from 'vue-router'
import Vue from 'vue'
import Login from './components/Login.vue'
import Register from './components/resgister/Register.vue'
import Home from './components/home/Home.vue'
import UserHome from './components/userhome/UserHome.vue'
import Index from './components/home/Index.vue'
import Welcome from './components/home/Welcome.vue'
import Depart from './components/home/Depart.vue'
import Job from './components/home/Job.vue'
import Myself from './components/home/Myself.vue'
import Product from './components/home/Product.vue'
import Customer from './components/home/Customer.vue'
import Order from './components/home/Order.vue'
import Main from './assets/index.vue'
import Clogin from './components/Clogin.vue'
import CusterRegister from './components/resgister/CusterRegister.vue'
import Material from './components/home/Material.vue'
import Supplier from './components/home/Supplier.vue'
import POrder from './components/home/POrder.vue'
import Produce from './components/home/Produce.vue'
// 创建路由对象
const routes = [
    {
        path: '/',
        redirect: '/clogin',
        component: Main,
        children: [
            {
                path: '/login',
                component: Login,
            },
            {
                path: '/register',
                component: Register,
            },
            {
                path: '/clogin',
                component: Clogin,
            },
            {
                path: '/custerregister',
                component: CusterRegister,
            },
        ]
    },
    
    {
        path: '/home',
        component: Home,
        redirect: '/employ',
        children: [
            {
                path: '/welcome',
                component: Welcome
            },
            {
                path: '/myself',
                component: Myself
            },
            {
                path: '/employ',
                component: Index
            },
            {
                path: '/depart',
                component: Depart,
            },
            {
                path: '/job',
                component: Job,
            },
            {
                path: '/product',
                component: Product,
            },
            {
                path: '/customer',
                component: Customer,
            },
            {
                path: '/order',
                component: Order,
            },
            {
                path: '/material',
                component: Material,
            },
            {
                path: '/supplier',
                component: Supplier,
            },
            {
                path: '/porder',
                component: POrder,
            },
            {
                path: '/produce',
                component: Produce,
            }
        ]
    },
    {
        path: "/userhome",
        component: UserHome
    }]



const router = new VueRouter({
    // 配置URL和组价直接的映射关系
    routes,
    // history模式 
    mode: 'history'
})
// 将router对象传入到vue实例中
Vue.use(VueRouter)
export default router