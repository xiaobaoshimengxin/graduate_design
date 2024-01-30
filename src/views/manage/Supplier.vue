<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { supplierListService, supAddService, supUpdateService } from '@/api/manage.js'

// const options = ref([
//     {
//         supplierId: 'Option',
//         supplierName: 'Option1',
//     }
// ])

const supplierData = ref([{
    id: '',  // 主键id
    name: '',    // 供应商
    createTime: '',  // 录入时间
    address: '',     // 地址
    contactNumber: '',    // 联系电话
    statue: 1   // 1表示正常
}]);

const supSearchChar = ref('');

const getSupplierList = async (sW)=>{
    let result = await supplierListService(sW);    
    supplierData.value = result.data;
};

getSupplierList(supSearchChar.value);

const sup = ref({
    id: '',  // 主键id
    name: '',    // 供应商
    createTime: '',  // 录入时间
    address: '',     // 地址
    contactNumber: '',    // 联系电话
    statue:1
});

let index = 0;

const centerDialogVisible = ref(false)

// 按供应商关键字查询
const doQuary = () => {
    getSupplierList(supSearchChar.value)
};


const addData = () => {
    for (let key in sup.value) {
        sup.value[key] = '';
    }
    centerDialogVisible.value = true
    index = supplierData.value.length;
};

const supplierDataPush = async () => {
    if (index == supplierData.value.length){
        sup.value.createTime=new Date().toISOString().slice(0, 10);  // YYY-MM-DD格式时间
        // 发送添加请求
        await supAddService(sup.value);
        ElMessage.success("添加成功");
    }else{
        // 发送更新请求
        await supUpdateService(sup.value);
        ElMessage.success("修改成功");
    }
    getSupplierList("");
    centerDialogVisible.value = false;
    for (let key in sup.value) {
        sup.value[key] = '';
    }
}

const updataDrug = (ind) => {
    index = ind;
    sup.value = JSON.parse(JSON.stringify(supplierData.value[index]));
    centerDialogVisible.value = true;
}


const updateStatue = async (ind) => {
    supplierData.value[ind].statue = supplierData.value[ind].statue ? 0 : 1;
    sup.value = JSON.parse(JSON.stringify(supplierData.value[ind]));
    // 发送请求    
    await supUpdateService(sup.value);
    ElMessage.success("成功");
}

// const deleteD =()=>{
//     supplierData.value.splice(0, 1);
// }

// deleteD();
</script>

<template>
    <div>
        <div style="text-align: left;">
            <el-button class="fun-but" style="margin-left: 10%; " type="primary" @click="addData"> 添加 </el-button>
            <el-form :inline="true" style="float: right; padding-right: 5%; padding-top: 20px;">
                <el-form-item label="">
                    <el-input v-model="supSearchChar" placeholder="请输入供应商关键字" ></el-input>
                </el-form-item>
                <el-form-item style="margin-left: -20px;">
                    <el-button type="primary"  @click="doQuary">查询</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table id="show-table" :data="supplierData" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" show-overflow-tooltip>
            <el-table-column prop="id" label="序号" width="90" />
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="address" label="地址" width="300"/>
            <el-table-column prop="createTime" label="录入时间" />
            <el-table-column prop="contactNumber" label="联系方式" />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button :disabled="supplierData[scope.$index].statue===0" type="warning" size="small" @click="updataDrug(scope.$index)">修改</el-button>
                    <el-button type="danger" size="small"
                        @click="updateStatue(scope.$index)">{{ supplierData[scope.$index].statue ? "停用" : "启用" }}</el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
    <el-dialog v-model="centerDialogVisible" title="添加供应商信息" width="30%" center>
        <el-form id="dialog-form" :model="sup">
            <el-form-item label="供&nbsp&nbsp应&nbsp&nbsp商:">
                <el-input v-model="sup.name" placeholder="请输入供应商名称" size="small"></el-input>
            </el-form-item>
            <el-form-item label="公司地址:">
                <el-input v-model="sup.address" placeholder="请输入公司地址" size="small"></el-input>
            </el-form-item>
            <el-form-item label="联系方式:">
                <el-input v-model="sup.contactNumber" placeholder="请输入供应商联系方式" size="small"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="supplierDataPush">
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