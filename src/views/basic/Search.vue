<script setup lang="ts">
import { ref, watch } from 'vue'
import { drugListSearchService, barCodeQuaryService, drugNameQuaryService } from '@/api/drug.js'
import { ElMessage } from 'element-plus';

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

const total = ref('');

const drugList = async (pageData) => {
    let result = await drugListSearchService(pageData.value);
    total.value = result.data.total;
    drugData.value = result.data.records;
}

drugList(pageData);

const barCode = ref('');
const drugName = ref('');

const barCodeQuary = async () => {
    let result = await barCodeQuaryService(barCode);
    drugName.value = '';
    total.value = '';
    drugData.value = result.data;
}

const drugNameQuary = async () => {
    let result = await drugNameQuaryService(drugName);
    barCode.value = '';
    total.value = '';
    drugData.value = result.data;
}
// 
const handleSizeChange = (val: number) => {
    pageData.value.pageSize = val;
    drugList(pageData);
}
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
    </el-form>
    <!-- :header-cell-style="{ textAlign: 'center' }"  -->
    <el-table :data="drugData" id="showTable">
        <el-table-column prop="id" label="id" width="80" />
        <el-table-column prop="drugName" label="药品名称" width="300" />
        <el-table-column prop="price" label="价格" />
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


