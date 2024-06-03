<script lang="ts" setup>
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, ref } from 'vue';
import { useInfoStore } from '@/stores/userInfo.js'
import { getReordDataServcice } from '@/api/manage.js';
//import {customerAddRecord} from '@api/manage.js'

const userStore = useInfoStore();

const recordData = ref([{
    payId:0,        //充值订单id
    customerId:0,        //顾客id
    customerName:'',     //顾客名称
    employeeId:0,        //操作员工id
    payAmount:0.0,     //充值金额
    //notes:'',     //支付方式
    payDate:''         //充值日期
}])
const getReordData = async ()=>{
    let result = await getReordDataServcice()
    recordData.value = result.data;
    console.log(recordData.value);
}

let index = 0;
getReordData()

</script>

<template>
    <div class="pay_list_div" style="width: auto;">
        <el-table id="show-table" :data="recordData" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }">
            <el-table-column prop="payId" label="序号" width="90" />
            <el-table-column prop="customerId" label="顾客ID"  />
            <el-table-column prop="customerName" label="顾客名称" />
            <el-table-column prop="payAmount" label="充值金额" />
            <el-table-column prop="payDate" label="充值日期" />
            <el-table-column prop="employeeId" label="操作员工" />
        </el-table>
    </div>

</template>
<style scoped>
#show-table {
    width: 98%;
    margin: auto;
}

#select-supplier {
    margin: 30px 0 20px 5pc;
}

.fun-but {
    margin-top: 1pc;
    margin-bottom: 1pc;
}

#dialog-form {
    text-align: center;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>