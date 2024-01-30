<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { supplierListService } from '@/api/manage.js'
import { purchaseAdd } from '@/api/purchase.js'

const supplierData = ref([{
    id: '',  // 主键id
    name: '',    // 供应商
    createTime: '',  // 录入时间
    address: '',     // 地址
    contactNumber: '',    // 联系电话
    statue: 1
}]);

const getSupplierList = async ()=>{
    let result = await supplierListService("");
    supplierData.value.splice(0, 1);
    for (let i = 0; i < result.data.length; i++){
        if (result.data[i].statue==1){
            supplierData.value.push(result.data[i]);
        }
    }
    // supplierData.value = result.data;
}
getSupplierList();

const formData = ref({
    supplierId: '',
    drugList: [{
        // drugBarCode: '',
        // drugName: '',
        // price: '',
        // specs: '',
        // category: '',
        // drugCount: '',
        // dateOfManufacture: '',
        // effectiveDate: ''
    }]
})


let drugInfo = ref({
    drugBarCode: '',
    drugName: '',
    price: '',
    specs: '',
    category: '',
    drugCount: '',
    dateOfManufacture: '',
    effectiveDate: ''
})

let index = 0;

const centerDialogVisible = ref(false)

const addData = () => {
    centerDialogVisible.value = true
    index = formData.value.drugList.length;
}

const drugListPush = () => {
    formData.value.drugList[index] = JSON.parse(JSON.stringify(drugInfo.value));
    centerDialogVisible.value = false;
    for (let key in drugInfo.value) {
        drugInfo.value[key] = '';
    }
}

const updataDrug = (ind) => {
    drugInfo.value = JSON.parse(JSON.stringify(formData.value.drugList[ind]));
    centerDialogVisible.value = true;
}

const deleteDrug = (ind) =>{
    formData.value.drugList.splice(ind,1);
}

deleteDrug(0);

import { useRouter } from 'vue-router';
const router = useRouter();
// 确定采购信息并发送
const purAddRequest = async ()=>{
    if (!formData.value.supplierId){
        ElMessage.error("请选择供应商");
    }else if (formData.value.drugList.length==0){
        ElMessage.error("请添加至少一件供应药品");
    }else {        
        // 发送请求
        await purchaseAdd(formData.value);
        ElMessage.success("添加采购信息成功");
        router.push('/purchase/history')
    }
    
}
</script>

<template>
    <div>
        <el-form :model="formData" :inline="true">
            <el-form-item id="select-supplier" label="供应商">
                <el-select v-model="formData.supplierId" class="m-2" placeholder="请选择供应商" size="small"
                    style="width: 180px;">
                    <el-option v-for="item in supplierData" :key="item.id" :label="item.name"
                        :value="item.id" />
                </el-select>
                <el-button id="addBut" type="primary" @click="addData"> 添加 </el-button>
            </el-form-item>

            <el-table id="show-table" :data="formData.drugList" :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column prop="drugName" label="药品名称" width="200" />
                <el-table-column prop="drugCount" label="数量" />
                <el-table-column prop="specs" label="规格" />
                <el-table-column prop="dateOfManufacture" label="生产日期" />
                <el-table-column prop="effectiveDate" label="有效日期" />
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button type="warning" size="small" @click="updataDrug(scope.$index)">修改</el-button>
                        <el-button type="danger" size="small" @click="deleteDrug(scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="text-align: center;">
                <el-button class="fun-but" type="primary" @click="purAddRequest">确定</el-button>
            </div>
        </el-form>
    </div>
    <el-dialog v-model="centerDialogVisible" title="添加采购药品" width="50%" center>
        <el-form id="dialog-form" :model="formData.drugList" :inline="true">
            <el-form-item label="条&nbsp&nbsp形&nbsp&nbsp码">
                <el-input v-model="drugInfo.drugBarCode" placeholder="请输入13位条形码" size="small"></el-input>
            </el-form-item>
            <el-form-item label="药品名称">
                <el-input v-model="drugInfo.drugName" placeholder="请输入药品名称" size="small"></el-input>
            </el-form-item>
            <el-form-item label="建议售价">
                <el-input v-model="drugInfo.price" placeholder="请输入建议售价" size="small"></el-input>
            </el-form-item>
            <el-form-item label="药品规格">
                <el-input v-model="drugInfo.specs" placeholder="请输入药品规格" size="small"></el-input>
            </el-form-item>
            <el-form-item label="药品类型">
                <el-input v-model="drugInfo.category" placeholder="请输入药品类型" size="small"></el-input>
            </el-form-item>
            <el-form-item label="药品数量">
                <el-input v-model="drugInfo.drugCount" placeholder="请输入药品数量" size="small"></el-input>
            </el-form-item>
            <el-form-item label="生产日期" style="width: 40%;">
                <el-date-picker v-model="drugInfo.dateOfManufacture" type="date" placeholder="请选择生产日期" size="small"
                    value-format="YYYY-MM-DD" />
            </el-form-item>
            <el-form-item label="有效日期" style="width: 40%;">
                <el-date-picker v-model="drugInfo.effectiveDate" type="date" placeholder="请选择有效日期" size="small"
                    value-format="YYYY-MM-DD" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="drugListPush">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>


<style scoped>
#show-table {
    width: 98%;
    margin: auto;
}

#select-supplier {
    margin: 30px 0 20px 5pc;
}
#addBut{
    margin-left: 38pc;
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