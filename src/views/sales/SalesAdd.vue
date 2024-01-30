<script lang="ts" setup>
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, ref } from 'vue';
import { drugListService } from '@/api/drug.js'
import { customerListService } from '@/api/manage.js'
import { saleAdd } from '@/api/sales.js'

const saleAddForm = ref({
    id: '',
    name: '',
    drugList: [{
        drugId: 0,
        drugName: '',
        num: 0, // 库存
        saleCount: 0,  // 售卖数量
        price: 0
    }],
    priceAll: 0
})

const customerInfo = ref('')
const customerList = ref([{
    id: '',
    phone: '',
    name: ''
}])
// 顾客信息，输入自动补齐
const querySearch = (queryString, cb) => {
    const restaurants = customerList;
    const results = queryString
        ? restaurants.value.filter((restaurant) => {
            return (restaurant.phone.toLowerCase().indexOf(queryString.toLowerCase()) != -1)
        })
        : restaurants.value
    cb(results)
}

const drugInfo = ref('')
const drugList = ref([{
    id: '',
    drugName: '',
    num: '',
    price: ''
}])

// 药品信息，输入自动补齐
const drugQuerySearch = (queryString, cb) => {
    const restaurants = drugList;
    const results = queryString
        ? restaurants.value.filter((restaurant) => {
            return (restaurant.drugName.toLowerCase().indexOf(queryString.toLowerCase()) != -1)
        })
        : restaurants.value
    // call callback function to return suggestions
    cb(results)
}

onMounted(async () => {
    saleAddForm.value.drugList.splice(0, 1);
    let result = await customerListService();
    let drugResult = await drugListService();
    customerList.value = result.data;
    drugList.value = drugResult.data;
})
// 顾客选择
const cusHandleSelect = (item) => {
    saleAddForm.value.id = item.id
}
// 药品选择
const drugHandleSelect = (item) => {
    saleAddForm.value.drugList.push(item);
}

const saleRequests=async(saleForm)=>{
    await saleAdd(saleForm.value);
}

// 确定按钮
const saleAddRequest = () => {
    let tempPrice=0;
    let tempList = saleAddForm.value.drugList
    for (let i=0;i<tempList.length;i++){
        tempPrice=tempList[i].price*tempList[i].saleCount;
    }
    
    saleAddForm.value.priceAll = tempPrice;
    // 发送请求
    saleRequests(saleAddForm);
    ElMessageBox.alert('售出总额:'+tempPrice, '成功', {
    confirmButtonText: '确认'
  })

}

</script>

<template>
    <div>
        <el-form :inline="true">
            <el-form-item id="select-supplier" label="顾客">
                <el-autocomplete v-model="customerInfo" value-key="phone" :fetch-suggestions="querySearch"
                    class="inline-input w-50" placeholder="请选择顾客" @select="cusHandleSelect" />
            </el-form-item>
            <el-form-item id="addBut" label="药品" width="200">
                <el-autocomplete v-model="drugInfo" value-key="drugName" :fetch-suggestions="drugQuerySearch"
                    class="inline-input w-50" placeholder="请选择药品" @select="drugHandleSelect" clearable/>
            </el-form-item>

            <el-table id="show-table" :data="saleAddForm.drugList" :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column prop="drugName" label="药品名称" width="250" />
                <el-table-column prop="num" label="库存" />
                <el-table-column prop="price" label="单价" />
                <el-table-column prop="saleCount" label="售卖数量" #default="scoped" width="150">
                    <el-input v-model="saleAddForm.drugList[scoped.$index].saleCount" placeholder="请输入数量"></el-input>
                </el-table-column>
                <el-table-column label="操作" #default="scoped">
                    <el-button type="danger" @click="saleAddForm.drugList.splice(scoped.$index, 1)">删除</el-button>
                </el-table-column>
            </el-table>
            <div style="text-align: center;">
                <el-button class="fun-but" type="primary" @click="saleAddRequest">确定</el-button>
            </div>
        </el-form>
    </div>
</template>


<style scoped>
#show-table {
    width: 98%;
    margin: auto;
}

#select-supplier {
    margin: 30px 0 20px 8%;
}

#addBut {
    margin: 30px 0 20px 35%;
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