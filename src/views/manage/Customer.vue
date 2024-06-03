<script lang="ts" setup>
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { customerListService, customerQueryService, customerAdd, customerScoreOperationService,customerWalletOperationService } from '@/api/manage.js'
import { useInfoStore } from '@/stores/userInfo.js'

const userStore = useInfoStore();

const options = ref([
    {
        Id: 1,
        Name: "增加",
    },{
        Id: 0,
        Name: "消耗",
    }
])

const pay_options = ref([
    {
        style:1,
        style_name:'微信余额',
    },{
        style:2,
        style_name:'现金支付'
    }
])

const customerData = ref([{
    id: 0,  // 主键id
    name: '',    // 姓名
    phone: '',     // 电话
    createTime: '',  // 创建时间
    score: 0,   // 积分
    wallet:0.0  //余额
}])

const cus = ref({
    id: '',  // 主键id
    name: '',    // 姓名
    phone: '',     // 电话
    createTime: '',  // 创建时间
    wallet: 0,   // 初始余额
})
//积分
const scoreData = ref({
    customerId: 0,
    name:'',
    op: 1,
    score: 0
})
//余额
const walletData = ref({
    customerId: 0,
    name:'',
    empId:0,
    wallet: 0,
    pay_date:"",
    notes:""
})

let index = 0;

const customerChar = ref('')

// 新增顾客对话框，显示
const centerDialogVisible = ref(false)

// 积分操作对话框，显示
const ScoreVisible = ref(false)

//余额充值对话框，显示
const WalletVisible = ref(false)

// 列出所有顾客记录
const customerList = async ()=>{
    let result = await customerListService();
    customerData.value = result.data;
}
customerList();
// 关键字（电话）查询顾客
const doQuary = async ()=>{
    if (customerChar.value.length>0){
        let result = await customerQueryService(customerChar.value);
        customerData.value = result.data;
    }else{
        customerList();
    }
}
// 添加顾客按钮
const addData = () => {
    centerDialogVisible.value = true
    index = customerData.value.length;
}
// 确认添加顾客按钮
const customerDataPush = async () => {
    cus.value.createTime = new Date().toISOString().slice(0, 10);  // YYY-MM-DD格式时间    
    // 发送请求
    await customerAdd(cus.value);
    centerDialogVisible.value = false;
    for (let key in cus.value) {
        cus.value[key] = '';
    }
    ElMessage.success("添加成功");
    customerList();
}
const scoreTitle = ref('')
// 积分操作按钮
const addScore = (ind) => {
    scoreData.value.customerId = customerData.value[ind].id;
    scoreData.value.name = customerData.value[ind].name;
    scoreTitle.value = scoreData.value.name+"积分操作";
    ScoreVisible.value = true;
}
// 确认积分操作按钮
const changeScore = async ()=>{
    // 发起请求
    await customerScoreOperationService(scoreData.value)
    ScoreVisible.value = false;
    scoreTitle.value = "";
    scoreData.value.op = 1;
    scoreData.value.score = 0;
    scoreData.value.name = '';
    ElMessage.success("操作成功");
    customerList();
}

const walletTitle = ref('')
// 余额充值按钮
const addwallet = (ind) => {
    walletData.value.customerId = customerData.value[ind].id;
    walletData.value.name = customerData.value[ind].name;
    walletData.value.pay_date = new Date().toISOString().slice(0, 10);  // YYY-MM-DD格式时间
    //console.log(walletData.value.creattime);
    walletTitle.value = scoreData.value.name+"余额充值";
    walletData.value.empId = userStore.id;
    //console.log(walletData.value.emp_id);
    WalletVisible.value = true;
}
//确认余额充值按钮
const changeWallet = async ()=>{
    await customerWalletOperationService(walletData.value)
    WalletVisible.value = false;
    walletTitle.value = "";
    walletData.value.name = '';
    walletData.value.wallet = 0;
    ElMessage.success("操作成功");
    customerList();
}

</script>

<template>
    <div>
        <div style="text-align: left;">
            <el-button class="fun-but" style="margin-left: 5%; " type="primary" @click="addData"> 新增顾客 </el-button>
            <el-form :inline="true" style="float: right; padding-right: 5%; padding-top: 20px;">
                <el-form-item label="">
                    <el-input v-model="customerChar" placeholder="请输入电话后四位" ></el-input>
                </el-form-item>
                <el-form-item style="margin-left: -20px;">
                    <el-button type="primary"  @click="doQuary">查询</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table id="show-table" :data="customerData" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }">
            <el-table-column prop="id" label="序号" width="90" />
            <el-table-column prop="name" label="名字"  />
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column prop="phone" label="联系方式" />
            <el-table-column prop="wallet" label="余额" />
            <el-table-column prop="score" label="积分" />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button type="warning" size="small" @click="addwallet(scope.$index)">余额充值</el-button>
                    <el-button type="warning" size="small" @click="addScore(scope.$index)">积分操作</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <el-dialog v-model="centerDialogVisible" title="新增顾客" width="30%" center>
        <el-form id="dialog-form" :model="cus" :inline="true">
            <el-form-item label="顾客姓名:">
                <el-input v-model="cus.name" placeholder="请输入顾客姓名" size="small"></el-input>
            </el-form-item>
            <el-form-item label="电话号码:">
                <el-input v-model="cus.phone" placeholder="请输入顾客电话" size="small"></el-input>
            </el-form-item>
            <el-form-item label="首次充值金额:">
                <el-input v-model="cus.wallet" placeholder="请输入余额" size="small"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="customerDataPush">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog v-model="ScoreVisible" :title=scoreTitle width="40%" center>
        <el-form id="dialog-form" :model="scoreData" :inline="true">
            <el-form-item>
                <el-select v-model="scoreData.op" class="m-2" placeholder="请选择增加或消耗"
                    style="width: 80px;">
                    <el-option v-for="item in options" :key="item.Id" :label="item.Name"
                        :value="item.Id" />
                </el-select>
                <el-input style="width: 120px;" v-model="scoreData.score" placeholder="请输入变动的积分"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="ScoreVisible = false">取消</el-button>
                <el-button type="primary" @click="changeScore">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>


    <el-dialog v-model="WalletVisible" :title=walletTitle width="40%" center>
        <el-form id="dialog-form" :model="walletData" :inline="true">
            <el-form-item>
                <el-text class="mx-1" color="black">请输入金额：&emsp;</el-text>
                <el-input style="width: 120px;" v-model="walletData.wallet" placeholder="请输入金额"></el-input>
            </el-form-item>
            <el-form-item label="支付方式:" style="width: 73%;">
                <el-select v-model="walletData.notes" class="m-2" placeholder="支付方式" size="small">
                    <el-option v-for="item in pay_options" :key="item.style" :label="item.style_name"
                        :value="item.style" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="WalletVisible = false">取消</el-button>
                <el-button type="primary" @click="changeWallet">
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