<script setup lang="ts">
import { onMounted, ref, } from 'vue'
import { drugListSearchService, barCodeQuaryService, drugNameQuaryService , GetdrugPeriodInfo ,GetdrugRemindInfo } from '@/api/drug.js'
import { ElMessage } from 'element-plus';
import {useInfoStore} from '@/stores/userInfo.js'
const userStore =  useInfoStore();

const drugData = ref([{
    id: '',
    drugName: '',
    price: '',
    specs: '',
    num: '',
    dateOfManufacture: '',
    effectiveDate: ''
}])

const pageData = ref({
    page: 1,
    pageSize: 10
})

const total = ref();

const drugList = async (pageData) => {
    let result = await drugListSearchService(pageData.value);
    total.value = result.data.total;
    drugData.value = result.data.records;
}

onMounted(() => {
    drugList(pageData);
})


const periodInfo = ref([{
    id:0,
    drugName:'',
    barCode:'',
    num:0,
    effDate:'',
}])

const drugPeriodInfo = ref(false);
//展示临期信息
const showPeriodInfo = () =>{
    drugPeriodInfo.value = true;
    requestForPeriodInfo();
}

//发送获取临期药品信息请求
const requestForPeriodInfo = async () =>{
    let result = await GetdrugPeriodInfo();
    console.log(result.data);
    // console.log("这是result");
    
    periodInfo.value = result.data;
    console.log("执行了吗");
    
    console.log(periodInfo.value);
}
//库存预警信息
const remindInfo = ref([{
    drugName:'',
    barCode:'',
    num:0,
}])
const drugRemindInfo = ref(false);
//库存预警
const showRemindInfo = () =>{
    drugRemindInfo.value = true;
    requestForRemindInfo();

}
//请求发送
const requestForRemindInfo = async ()=>{
    let result = await GetdrugRemindInfo();
    console.log(result.data);
    remindInfo.value = result.data;
    console.log("执行了吗");
    console.log(remindInfo.value);
    
    
}
const barCode = ref('');
const drugName = ref('');

const barCodeQuary = async () => {
    let result = await barCodeQuaryService(barCode);
    drugName.value = '';
    drugData.value = result.data;
    total.value = drugData.value.length;
}
// 按名称查
const drugNameQuary = async () => {
    let result = await drugNameQuaryService(drugName);
    barCode.value = '';
    drugData.value = result.data;
    total.value = drugData.value.length;
}
// 每页条数变化
const handleSizeChange = (val: number) => {
    pageData.value.pageSize = val;
    drugList(pageData);
}
// 页数变化
const handleCurrentChange = (val: number) => {
    pageData.value.page = val;
    drugList(pageData);
}

</script>

<template>
    <!-- <label>token:</label><span>{{ token }}</span> -->
    <el-form :inline="true" id="searchForm">
        <el-form-item label="条码查询:">
            <el-input v-model="barCode" placeholder="请输入13位药品条码" size="small"></el-input>
        </el-form-item>
        <el-form-item style="margin-left: -20px;">
            <el-button type="primary" size="small" @click="barCodeQuary">查询</el-button>
        </el-form-item>

        <el-form-item label="药名查询:" style="margin-left: 20%;">
            <el-input v-model="drugName" placeholder="请输入药名关键字" size="small"></el-input>
        </el-form-item>
        <el-form-item style="margin-left: -20px;">
            <el-button type="primary" size="small" @click="drugNameQuary">查询</el-button>
        </el-form-item>
        <el-button style="margin-left: 100px;" type="warning" size="small" @click="showPeriodInfo()">临期提示</el-button>
        <el-button style="margin-left: 200px;" type="warning" size="small" @click="showRemindInfo()">库存预警</el-button>
    </el-form>
    <!-- :header-cell-style="{ textAlign: 'center' }"  -->
    <el-table :data="drugData" id="showTable">
        <el-table-column prop="id" label="id" width="80" />
        <el-table-column prop="drugName" label="药品名称" width="200" />
        <el-table-column prop="purPrice" label="进价" :v-show="!(userStore.power<1)"/>
        <el-table-column prop="price" label="售价" />
        <el-table-column prop="specs" label="规格" />
        <el-table-column prop="num" label="数量" />
        <el-table-column prop="dateOfManufacture" label="生产日期" />
        <el-table-column prop="effectiveDate" label="有效日期" />
    </el-table>

    <div style="text-align: -webkit-center;">
        <div class="example-pagination-block" style="width: max-content; ">
            <el-pagination layout="sizes, prev, pager, next, total" :total="total" @size-change="handleSizeChange"
                @current-change="handleCurrentChange" />
        </div>
    </div>

    <el-dialog title="临期药品信息" v-model="drugPeriodInfo" center width="60%">
        <el-table :data="periodInfo" :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
            <el-table-column label="药品ID" prop="id"></el-table-column>
            <el-table-column label="药品名称" prop="drugName"></el-table-column>
            <el-table-column label="药品条码" prop="barCode"></el-table-column>
            <el-table-column label="数量" prop="num"></el-table-column>
            <el-table-column label="到期日期" prop="effDate"></el-table-column>
        </el-table>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="drugPeriodInfo = false">确认</el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog title="库存预警信息" v-model="drugRemindInfo" center width="40%">
        <el-table :data="remindInfo" :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
            <el-table-column label="药品名称" prop="drugName"></el-table-column>
            <el-table-column label="药品条码" prop="barCode"></el-table-column>
            <el-table-column label="数量" prop="num"></el-table-column>
        </el-table>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="drugRemindInfo = false">确认</el-button>
            </span>
        </template>
    </el-dialog>

</template>

<style>
#searchForm {
    margin-left: 8%;
    padding-top: 15px;
}

#showTable {
    width: 98%;
    margin: auto;
}

.example-pagination-block {
    margin-top: 10px;
    padding-bottom: 10px;
}
</style>


