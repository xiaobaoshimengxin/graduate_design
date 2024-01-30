<script lang="ts" setup>
import { ref } from 'vue'
import { purchaseHistoryList } from '@/api/purchase.js'

const historyData = ref([{
    purId: '',
    buyDate: '',
    drugInfo: [{
        drugName: '',
        drugCount: '',
        price: ''
    }],
    drugNames: '',
    supplierName: ''
}])

const dateAndSupllier = ref({
    page:1,
    pageSize:10,
    purDate: '',    // 日期 date
    supplier: ''    // 供应商 string
})

const total = ref('');

const purchaseList = async (dateAndSupllier) => {
    let result = await purchaseHistoryList(dateAndSupllier.value);
    total.value = result.data.total;
    historyData.value = result.data.records;
}

purchaseList(dateAndSupllier);

const doQuary = () => {
    purchaseList(dateAndSupllier);
}

const showDrugs = ref([{
    drugName: '',
    drugCount: '',
    price: ''
}])

const dialogShowCon = ref(false)
const showDrugInfo = (ind) => {
    dialogShowCon.value = true;
    showDrugs.value = historyData.value[ind].drugInfo
}

const handleSizeChange = (val:number)=>{
    dateAndSupllier.value.pageSize=val;
    purchaseList(dateAndSupllier);
}
const handleCurrentChange = (val:number)=>{
    dateAndSupllier.value.page=val;
    purchaseList(dateAndSupllier);
}
</script>

<template>
    <el-form :inline="true" style="margin-left: 20%; padding-top: 30px;">
        <el-form-item label="采购日期:">
            <div class="demo-date-picker" style="width: 80%;">
                <el-date-picker v-model="dateAndSupllier.purDate" type="date" placeholder="请选择采购日期" size="small"
                    value-format="YYYY-MM-DD" />
            </div>
        </el-form-item>
        <el-form-item label="供应商:">
            <el-input v-model="dateAndSupllier.supplier" placeholder="请输入供应商关键字" size="small"></el-input>
        </el-form-item>
        <el-form-item style="margin-left: -20px;">
            <el-button type="primary" size="small" @click="doQuary">查询</el-button>
        </el-form-item>
    </el-form>
    <el-table :data="historyData" style="width: 98%; margin: auto; " :header-cell-style="{ textAlign: 'center' }"
        :cell-style="{ textAlign: 'center' }" show-overflow-tooltip>
        <el-table-column prop="purId" label="单号" width="150" />
        <el-table-column prop="buyDate" label="采购日期" width="150" />
        <el-table-column prop="drugNames" label="采购药品" />
        <el-table-column prop="supplierName" label="供应商" />
        <el-table-column label="操作" width="100" #default="scoped">
            <el-button type="" size="small" @click="showDrugInfo(scoped.$index)">查看信息</el-button>
        </el-table-column>
    </el-table>

    <div style="text-align: -webkit-center; margin-right: 10%;">
        <div class="example-pagination-block" style="width: max-content;">
            <el-pagination layout="sizes, prev, pager, next, total" :total="total" @size-change="handleSizeChange"
                @current-change="handleCurrentChange"/>
        </div>
    </div>

    <el-dialog title="采购药品信息" v-model="dialogShowCon" center width="40%">
        <el-table :data="showDrugs" :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
            <el-table-column label="药品" prop="drugName"></el-table-column>
            <el-table-column label="数量" prop="drugCount"></el-table-column>
            <el-table-column label="价格" prop="price"></el-table-column>
        </el-table>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogShowCon = false">确认</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style>
.demo-date-picker {
    display: flex;
    width: 100%;
    padding: 0;
    flex-wrap: wrap;
}

.demo-date-picker .block {
    padding: 30px 0;
    text-align: center;
    border-right: solid 1px var(--el-border-color);
    flex: 1;
}

.demo-date-picker .block:last-child {
    border-right: none;
}

.demo-date-picker .demonstration {
    display: block;
    color: var(--el-text-color-secondary);
    font-size: 14px;
    margin-bottom: 20px;
}

.example-pagination-block+.example-pagination-block {
    margin-top: 10px;
}
</style>

