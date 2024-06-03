<script setup>
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, ref } from 'vue';
import { drugListService } from '@/api/drug.js'
import { customerListService } from '@/api/manage.js'
import { saleAdd } from '@/api/sales.js'
import { useInfoStore } from '@/stores/userInfo.js'
import customerData from '../manage/Customer.vue';

const userStore = useInfoStore();
//支付方式界面，显示
const payStyleVisible = ref(false);

const options = ref([
    {
        style:1,
        style_name:'医保余额',
    },{
        style:2,
        style_name:'微信支付'
    },{
        style:3,
        style_name:'现金支付'
    }
])

const saleAddForm = ref({
    id: '', // 顾客id
    empId: '',  // 员工id
    name: '',
    score: '',
    useScore: 0,
    drugList: [{
        id: 0,
        drugName: '',
        num: 0, // 库存
        saleCount: 0,  // 售卖数量
        price: 0
    }],
    priceAll: 0.0,
    payType:0,
    wallet:0.0
})

const tmp = ref({
    name:'',
    pey_style:0,
})

const customerInfo = ref('')
const customerList = ref([{
    id: '',
    phone: '',
    name: '',
    score: 0,
    wallet:0.0
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
    console.log('选择顾客', item);
    saleAddForm.value.id = item.id;
    saleAddForm.value.name = item.name;
    saleAddForm.value.score = item.score;
    saleAddForm.value.wallet = item.wallet;
    console.log('saleAdd: ', saleAddForm.value);
}

// 计算总价
const sumPrice = () => {
    let tempPrice = 0;
    for (let i = 0; i < saleAddForm.value.drugList.length; i++) {
        tempPrice += saleAddForm.value.drugList[i].price * saleAddForm.value.drugList[i].saleCount;
    }
    saleAddForm.value.priceAll = tempPrice.toFixed(2);
}

// 药品选择
const drugHandleSelect = (item) => {
    console.log(item);
    item.saleCount = 1;
    for (let i = 0; i < saleAddForm.value.drugList.length; i++) {
        if (item.id === saleAddForm.value.drugList[i].id) {
            ElMessage.error("已选择此药品");
            return;
        }
    }
    saleAddForm.value.drugList.push(item);
    drugInfo.value = '';
    sumPrice();
}
//售卖请求发送
const saleRequests = async (saleForm) => {
    await saleAdd(saleForm.value);
}
const handleChange = (count, price) => {
    sumPrice();
}
const deleteThatDrug = (index) => {
    saleAddForm.value.drugList.splice(index, 1);
    sumPrice();
}

//微信支付二维码框
const WeiXinPayInfo = ref(false);

// 确定按钮
const saleAddRequest = () => {
    //判断是否选择药品
    if (saleAddForm.value.drugList.length ==0){
        ElMessage.error("药品不能为空,请选择药品");
        return;
    }
    if(saleAddForm.value.id == '' && saleAddForm.value.payType == 1){
        ElMessage.error("如要使用医保支付，请先选择顾客");
        return;
    }
    if(saleAddForm.value.priceAll > saleAddForm.value.wallet && saleAddForm.value.payType == 1){
        ElMessage.error("用户余额不足");
        return;
    }
    //选择微信支付
    if(saleAddForm.value.payType ==2){
        WeiXinPayInfo.value = true;
        console.log("执行了吗");
        //设置时延5s
        setTimeout(() => {
            console.log("执行了吗1");
            WeiXinPayInfo.value = false;
            saleAddForm.value.empId = userStore.id;
        saleRequests(saleAddForm);
        let rt = saleAddForm.value.priceAll - saleAddForm.value.useScore/500;
        ElMessageBox.alert('售出总额:' + rt.toFixed(2), '成功', {
        confirmButtonText: '确认'
    })
        }, 5000);
    }
    //payStyleVisible.value = true;
    else
        {
        saleAddForm.value.empId = userStore.id;
        saleRequests(saleAddForm);
        let rt = saleAddForm.value.priceAll - saleAddForm.value.useScore/500;
        ElMessageBox.alert('售出总额:' + rt.toFixed(2), '成功', {
            confirmButtonText: '确认'
        })}
}
//选择支付按钮
const choosePayStyle = () => {
    payStyleVisible.value = true;

}
const payStyleRequest = () =>{
    payStyleVisible.value = false;
    //console.log("111111");
}

const selectedOption = ref(1);

//收款码图片
const url='/src/media/pay.png'

</script>

<template>
    <div style="position: relative;">
        <el-form :inline="true">
            <div class="head-function">
                <el-form-item id="select-supplier" label="顾客">
                    <el-autocomplete v-model="customerInfo" value-key="phone" :fetch-suggestions="querySearch"
                        class="inline-input w-50" placeholder="请选择顾客" @select="cusHandleSelect" />
                </el-form-item>

                <span v-if="saleAddForm.id !== ''" class="vip-o">{{ '顾客：' + saleAddForm.name + ' &emsp;可用积分：' +
            saleAddForm.score + '&emsp;会员余额：'+ saleAddForm.wallet
                    }}</span>

                <el-form-item id="addBut" label="药品" width="200">
                    <el-autocomplete v-model="drugInfo" value-key="drugName" :fetch-suggestions="drugQuerySearch"
                        class="inline-input w-50" placeholder="请选择药品" @select="drugHandleSelect" clearable />
                </el-form-item>
            </div>

            <el-table id="show-table" :data="saleAddForm.drugList" :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }">
                <el-table-column prop="drugName" label="药品名称" width="250" />
                <el-table-column prop="num" label="库存" />
                <el-table-column prop="price" label="单价" />
                <el-table-column prop="saleCount" label="售卖数量" #default="scoped" width="180">
                    <el-input-number v-model="saleAddForm.drugList[scoped.$index].saleCount" :min="1"
                        :max="saleAddForm.drugList[scoped.$index].num"
                        @change="handleChange(saleAddForm.drugList[scoped.$index].saleCount, saleAddForm.drugList[scoped.$index].price)" />
                </el-table-column>
                <el-table-column label="操作" #default="scoped">
                    <el-button type="danger" @click="deleteThatDrug(scoped.$index)">删除</el-button>
                </el-table-column>
            </el-table>

            <div style="position: relative; height: 90px;">
                <div class="total-price">总价:&emsp;{{ saleAddForm.priceAll }}</div>
                <div class="secf" v-if="saleAddForm.id !== ''">
                    <input type="radio" value="1" v-model="selectedOption">不适用积分
                    <input type="radio" value="2" v-model="selectedOption">使用积分抵扣(最多20%)
                </div>
                <el-input-number v-if="selectedOption === '2'" class="score-input" v-model="saleAddForm.useScore" :min="0"
                    :max="Math.min(saleAddForm.score, saleAddForm.priceAll*100)" :step="500"
                    />
                <span class="dikou" v-if="selectedOption === '2'">可抵扣：{{ Math.round(saleAddForm.useScore/5)/100 }}元</span>

                <el-button class="fun-but" type="primary" @click="choosePayStyle">确认支付信息</el-button>
            </div>
        </el-form>
    </div>


    <template>
  <el-button plain @click="outerVisible = true">
    Open the outer Dialog
  </el-button>

  <el-dialog v-model="outerVisible" title="Outer Dialog" width="800">
    <span>This is the outer Dialog</span>
    <el-dialog
      v-model="innerVisible"
      width="500"
      title="Inner Dialog"
      append-to-body
    >
      <span>This is the inner Dialog</span>
    </el-dialog>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="outerVisible = false">Cancel</el-button>
        <el-button type="primary" @click="innerVisible = true">
          Open the inner Dialog
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<el-dialog v-model="payStyleVisible" title="选择支付方式" width="30%" center>
        <el-form id="dialog-form" :model="saleAddForm" :inline="true">
            <el-form-item label="总价:">
                <div class="total-priceinfo">{{ saleAddForm.priceAll }}</div>
            </el-form-item>
            <el-form-item label="支付方式:" style="width: 73%;">
                <el-select v-model="saleAddForm.payType" class="m-2" placeholder="支付方式" size="small">
                    <el-option v-for="item in options" :key="item.style" :label="item.style_name"
                        :value="item.style" />
                </el-select>
                
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="payStyleVisible = false">取消</el-button>
                <el-button type="primary" @click="saleAddRequest">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog title="请扫描此二维码进行支付" v-model="WeiXinPayInfo" center width="20%">
        <el-image  :src="url"  />
    </el-dialog>
</template>


<style scoped>
.total-price {
    font-size: large;
    margin: 20px 0 0 100px;
}

.head-function {
    height: 80px;
}

.vip-o {
    position: relative;
    left: 120px;
    top: 35px;
}
.dikou{
    position: relative;
    float:left;
    left: 200px;
    top: 25px;
}

#show-table {
    width: 98%;
    margin: auto;
}

#select-supplier {
    position: relative;
    float: left;
    left: 100px;
    top: 30px;
}

#addBut {
    position: relative;
    float: right;
    right: 100px;
    top: 30px;
}

.secf {
    /* display: inline; */
    position: relative;
    float: left;
    left: 100px;
    top: 25px;
}

.score-input {
    position: relative;
    float: left;
    left: 150px;
    top: 20px;
}

.fun-but {
    position: relative;
    float: right;
    right: 300px;
    margin-top: 1pc;
    margin-bottom: 1pc;
}

#dialog-form {
    text-align: center;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}
.total-priceinfo{
    margin:-15px 144px -11px 100px;
}
</style>