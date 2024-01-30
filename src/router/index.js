import { createRouter, createWebHistory } from 'vue-router'
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import SearchVue from '@/views/basic/Search.vue'
import Supplier from '@/views/manage/Supplier.vue'
import Customer from '@/views/manage/Customer.vue'
import Employee from '@/views/manage/Employee.vue'
import PurchaseHistory from '@/views/purchase/PurchaseHistory.vue'
import PurchaseAdd from '@/views/purchase/PurchaseAdd.vue'
import SalesHistory from '@/views/sales/SalesHistory.vue'
import SalesAdd from '@/views/sales/SalesAdd.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/login', component:LoginVue},
    {
      path: '/', 
      component: LayoutVue,
      redirect:'/search',
      children: [
        { path: '/search', component: SearchVue },
        { path: '/purchase/history', component: PurchaseHistory },
        { path: '/purchase/add', component: PurchaseAdd },
        { path: '/sales/history', component: SalesHistory },
        { path: '/sales/add', component: SalesAdd },
        { path: '/manage/supplier', component: Supplier },
        { path: '/manage/customer', component: Customer },
        { path: '/manage/employee', component: Employee },
      ]
    }
  ]
})

export default router
