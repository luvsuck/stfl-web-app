import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import FactoryManage from '../views/FactoryManage.vue'
import ProductManage from '../views/ProductManage.vue'
import ProductStat from '../views/ProductStat.vue'
import ProductReport from '../views/ProductReport.vue'
import { compile } from 'vue'

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    {
        path: '/home', name: 'Home', component: Home, children: [
            { path: 'factory/manage', name: 'FactoryManage', component: FactoryManage },
            { path: 'product/manage', name: 'ProductManage', component: ProductManage },
            { path: 'stat/product', name: 'ProductStat', component: ProductStat }
        ]
    },
    { path: '/manage', name: 'ManageChart', component: ProductReport }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router

