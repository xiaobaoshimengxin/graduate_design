<script lang="ts" setup>
import { ref } from 'vue'
import { salesListService , saleRevokeOrder } from '@/api/sales.js'
import {useInfoStore} from '@/stores/userInfo.js'
import { ElMessage, ElMessageBox } from 'element-plus';
const userStore =  useInfoStore();

const saleHistory = ref([{
    saleId: '',  // 出售单号
    customer: '', // 顾客名
    saleDate: '',  // 售卖日期
    drugs: '',   // 药品列表
    drugList: [{
        drugName: '',
        drugCount: '',
        price: ''
    }],  // 售卖药品信息
    employee: '',    // 售卖员工
    payType:''       //支付方式
}])

const saleSearchInfo = ref({
    page: 1,
    pageSize: 10,
    saleDate: '',
    drugName: undefined
})


//订单撤销信息
const revokeData = ref({
    saleId:''
})

const total = ref('');

const historyList = async (saleSearchInfo) => {
    let result = await salesListService(saleSearchInfo.value);
    total.value = result.data.total;
    saleHistory.value = result.data.records;
    // console.log(saleHistory.value);

}
historyList(saleSearchInfo);

const doQuary = () => {
    historyList(saleSearchInfo);
}

const saleInfo = ref([{
    drugName: '',
    drugCount: '',
    price: ''
}])

const dialogSaleInfo = ref(false);
const showSaleInfo = (ind) => {
    // console.log(ind);
    saleInfo.value = saleHistory.value[ind].drugList;
    dialogSaleInfo.value = true;
}
const handleSizeChange = (val: number) => {
    saleSearchInfo.value.pageSize = val;
    historyList(saleSearchInfo);
}
const handleCurrentChange = (val: number) => {
    saleSearchInfo.value.page = val;
    historyList(saleSearchInfo);
}
//订单撤销事件
const RevokeOrder = (ind) => {
    revokeData.value.saleId = saleHistory.value[ind].saleId;        //获取需要撤销的订单id
    console.log(
        'saleid:',revokeData.value.saleId
    );
    
    let thispri_all = 0;
    for(let i = 0;i<saleHistory.value[ind].drugList.length;i++){
        let price = parseFloat(saleHistory.value[ind].drugList[i].price);
        let count = parseInt(saleHistory.value[ind].drugList[i].drugCount);
        thispri_all += price*count;
    }
    ElMessageBox.alert('撤销订单总额:' + thispri_all.toFixed(2), '成功', {
        confirmButtonText: '确认'
    })
    revokerequest(revokeData.value);
}
//撤销请求
const revokerequest = async (revokeData) =>{
    console.log(revokeData);
    await saleRevokeOrder(revokeData);
}

const outExport = () => {
    
    window.open('http://localhost:8080/sales/exports')
}

</script>

<template>
    <el-form :inline="true" class="top-selection">
        <el-form-item label="售卖日期:">
            <div class="demo-date-picker" style="width: 80%;">
                <el-date-picker v-model="saleSearchInfo.saleDate" type="date" placeholder="请选择出售日期" size="small"
                    value-format="YYYY-MM-DD" />
            </div>
        </el-form-item>
        <!-- <el-form-item label="出售药品:">
            <el-input v-model="saleSearchInfo.drugName" placeholder="请输入出售药名关键字" size="small"></el-input>
        </el-form-item> -->
        <el-form-item style="margin-left: -20px;">
            <el-button type="primary" size="small" @click="doQuary">查询</el-button>
        </el-form-item>
    </el-form>
    <el-button type="primary" class="out-file" @click="outExport"> 导出Excel </el-button>
    <el-table :data="saleHistory" style="width: 100%; margin: auto; " :header-cell-style="{ textAlign: 'center' }"
        :cell-style="{ textAlign: 'center' }" show-overflow-tooltip>
        <el-table-column prop="saleId" label="单号" width="120" />
        <el-table-column prop="saleDate" label="出售日期" width="200" />
        <el-table-column prop="customer" label="顾客" width="100" />
        <el-table-column prop="drugs" label="出售药品" />
        <el-table-column prop="payType" label="支付方式" />
        <el-table-column prop="employee" label="操作员工" width="150" />
        <el-table-column label="操作" >
            <template #default="scoped">
                <el-button type="" size="small" @click="showSaleInfo(scoped.$index)">查看详细信息</el-button>
                <el-button type="warning" size="small" :disabled="userStore.power<3" @click="RevokeOrder(scoped.$index)">撤销</el-button>
            </template>
        </el-table-column>
    </el-table>

    <div style="text-align: -webkit-center;">
        <div class="example-pagination-block" style="width: max-content;">
            <el-pagination layout="sizes, prev, pager, next, total" :total="total" @size-change="handleSizeChange"
                @current-change="handleCurrentChange" />
        </div>
    </div>

    <el-dialog title="出售药品信息" v-model="dialogSaleInfo" center width="40%">
        <el-table :data="saleInfo" :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
            <el-table-column label="药品" prop="drugName"></el-table-column>
            <el-table-column label="数量" prop="drugCount"></el-table-column>
            <el-table-column label="单价" prop="price"></el-table-column>
        </el-table>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogSaleInfo = false">确认</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style>
.top-selection {
    position: relative;
    margin-left: 20%;
    padding-top: 30px;
}

.out-file {
    position: relative;
    float: right;
    right: 25px;
    top: -50px;
}
</style>
