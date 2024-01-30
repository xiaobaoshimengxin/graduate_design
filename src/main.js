import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElemenetPlus from 'element-plus'
import 'element-plus/dist/index.css' 
import {createPinia} from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

const app = createApp(App)
const pinia = createPinia();
const persist = createPersistedState();

pinia.use(persist);
app.use(pinia);
app.use(router);
app.use(ElemenetPlus);
app.mount('#app');
