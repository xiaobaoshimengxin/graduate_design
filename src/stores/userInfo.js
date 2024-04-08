import { defineStore } from 'pinia'
import { ref } from 'vue'
/*
第一个参数：名字唯一性
第二个参数：函数，函数内部定义状态所有内容
*/

export const useInfoStore = defineStore(
    'id',
    () => {
        // 响应式变量
        const id = ref('');
        const power = ref('');
        const name = ref('')
        // 函数：修改的值
        const setId = (newId) => {
            id.value = newId;
        }
        const setPower = (newPower) => {
            power.value = newPower;
        }
        const setName = (newName) =>{
            power.value = newName;
        }
        // 函数：移除值
        const clearId = () => {
            id.value = '';
        }
        const clearPower = ()=>{
            power.value = ''
        }
        return {
            id, setId, clearId,
            power, setPower, clearPower
        }
    },
    {
        // 持久化存储
        persist:true
    }
);

