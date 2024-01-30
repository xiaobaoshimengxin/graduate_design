// 导入axios
import axios from "axios";
import { ElMessage } from "element-plus";
import { useTokenStore } from "@/stores/token.js";

// const baseURL = 'http://locathost:8080';
// 公共前缀
const baseURL = '/api';
const instance = axios.create({ baseURL })

// 请求拦截器
instance.interceptors.request.use(
    (config)=>{
        // 请求前的回调
        const tokenStore = useTokenStore();
        if (tokenStore.token){
            // 添加token
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    (err)=>{
        // 请求失败的回调
        Promise.reject(err);
    }
)

// import { useRouter } from "vue-router";
// const router = useRouter();

import router from '@/router'

// 响应拦截器
instance.interceptors.response.use(
    result => {
        if (result.data.code === 1) {
            // 请求数据成功响应，返回数据
            return result.data;
        }
        // 失败处理
        ElMessage.error(result.data.msg ? result.data.msg : "服务异常");
        return Promise.reject(result.data);
    },
    err => {
        if (err.response.status===401){
            ElMessage.error("请先登录");
            router.push("/login")
        }
        else{
            ElMessage.error("服务器响应异常");
        }
        return Promise.reject(err);
    }
)

export default instance;


