import './assets/main.css'
import { createApp } from "vue";
import { createPinia } from "pinia";

//import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElemenetPlus from 'element-plus'
import 'element-plus/dist/index.css' 

//import {createPinia} from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
// 引入Element-plus
//import ElementPlus from "element-plus";
//import "element-plus/dist/index.css";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";

import * as echarts from 'echarts'
import VueEcharts from 'vue-echarts'
//5.13 16:40
// Vue.prototype.$echarts = echarts

// import echarts from 'echarts'; Vue.prototype.$echarts = echarts;
 
// 引入图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
 
// 引入路由
//import router from "./router/index.js";
 
// 引入echarts
// import echarts from "@/utils/Echarts.js";
 
// 引入整个项目入口文件
//import App from "./App.vue";
 
// 定义全局样式
//import "./style.less";

const app = createApp(App)
const pinia = createPinia();
const persist = createPersistedState();

pinia.use(persist);
app.use(pinia);
app.use(router);
app.use(ElemenetPlus);

//5.13 13:59
app.use(VueEcharts);
VueEcharts.echarts = echarts;

app.mount('#app');




// vue3的挂载方式（一个用于注册能够被应用内所有组件实例访问到的全局属性的对象。）
//app.config.globalProperties.$echarts = echarts; 

// app.use(router).use(piniaStore).use(ElementPlus, {
//     // size: 'small',
//     zIndex: 3000,
//     locale: zhCn,
//   });




