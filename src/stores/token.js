import { defineStore } from 'pinia'
import { ref } from 'vue'
/*
第一个参数：名字唯一性
第二个参数：函数，函数内部定义状态所有内容
*/

export const useTokenStore = defineStore(
    'token',
    () => {
        // 响应式变量
        const token = ref('');

        // 函数：修改token的值
        const setToken = (newToken) => {
            token.value = newToken;
        }

        // 函数：移除token值
        const clearToken = () => {
            token.value = '';
        }
        return {
            token, setToken, clearToken
        }
    },
    {
        // 持久化存储
        persist:true
    }
);

