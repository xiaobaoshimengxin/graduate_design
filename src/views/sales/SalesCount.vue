<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { saleGetCount, saleGetAmount, saleGetAll } from '@/api/sales.js';

import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

// import {inject, onBeforeUnmount,onMounted} from 'vue';
// import { eCharts } from '@/utils/Echarts.js';

//5.13 12:58

// import { createApp } from 'vue'
// import * as echarts from 'echarts'

//5.13 14:01
// import  {use } from 'echarts/core';

const dialogTableVisible = ref(false);

//搜索日期参数
const searchInfo = ref({
    start_term: '',
    last_term: '',
    //opt:0       //按了哪个按钮
})
//药品名字、数量
const saleCount = ref([{
    drugName: '',
    sumCounts: 0,
}])
//日期、销售额
const dayAmount = ref([{
    saleDate: '',
    saleAmount: 0.0,
}])
//  药品数量列表
const sumCountList = []
//  药品名称列表
const drugNameList = []
//日销售额列表
const dayAmountList = []
//日期表
const dateList = []
//所有日销售额列表
const AllAmountList = []
//所有日期
const AllDateList = []
//更新日期、销售额列表
const pushDateAndAmountData = () => {
    //日销售额列表
    const dayAmountList = [];
    //日期表
    const dateList = [];
    for (let i = 0; i < dayAmount.value.length; i++) {
        dateList.push(dayAmount.value[i].saleDate);
        dayAmountList.push(dayAmount.value[i].saleAmount);
    }
    console.log(...dateList);
    console.log(...dayAmountList);
    updateChart2(dayAmountList,dateList);

}
//更新药品数量、名称列表
const pushSumAndNameData = () => {
    //  药品数量列表
    const sumCountList = []
    //  药品名称列表
    const drugNameList = []
    for (let i = 0; i < saleCount.value.length; i++) {
        sumCountList.push(saleCount.value[i].sumCounts);
        drugNameList.push(saleCount.value[i].drugName)
    }
    console.log(...sumCountList);
    console.log(...drugNameList);
    updateChart1(drugNameList, sumCountList);            //图标更新
}
//更新所有日销售额列表
const pushAllAmountData = () => {
    //所有日销售额列表
    const AllAmountList = []
    //所有日期
    const AllDateList = []
    for (let i = 0; i < dayAmount.value.length; i++) {
        AllAmountList.push(dayAmount.value[i].saleAmount);
        AllDateList.push(dayAmount.value[i].saleDate);
    }
    console.log(...AllAmountList);
    console.log(...AllDateList[0]);
    updateChart_index(AllDateList,AllAmountList);
}
//请求药品名称、销售数量数据
const Count_list = async () => {
    // console.log(searchInfo.value.start_term);
    // console.log(searchInfo.value.last_term);
    let result = await saleGetCount(searchInfo.value);
    saleCount.value = result.data;
    console.log(saleCount.value);
    console.log("填充药品销售数量列表");
    pushSumAndNameData();
    //  药品数量列表
    //initEchartsOneFn();
}
//请求药品日销售量数据
const Amount_list = async () => {
    let result = await saleGetAmount(searchInfo.value);
    dayAmount.value = result.data;
    console.log(dayAmount.value);
    console.log("填充日销售额列表");
    pushDateAndAmountData();
}
//请求所有日销售数据
const AllAmount_list = async () => {
    let result = await saleGetAll();
    dayAmount.value = result.data;
    console.log(dayAmount.value);
    console.log("填充所有销售额列表");
    pushAllAmountData();
}
//进入页面即执行
AllAmount_list();

//按钮变量tmp
const tmp = ref(false)
//按钮点击1
const choose1 = () => {
    tmp.value = true;
    //console.log("执行了吗？",tmp);

}
//点击按钮2
const choose2 = () => {
    tmp.value = false;
}
//5.13 16:43

//结束


onMounted(() => {

    drawChart();
    // setInterval(() => {
    //     // alert("ss")
    //     // drawChart();
    //     updateChart();
    // }, 1000);
})

//更新销售数量表
function updateChart1(sumCountList, drugNameList) {
    // console.log(sumCountList);
    // console.log(drugNameList);
    myChart.data.labels = sumCountList;
    myChart.data.datasets[0].data = drugNameList;
    myChart.update();
}
//更新日销售额表
function updateChart2(dayAmountList,dateList){
    myChart.data.labels = dateList;
    myChart.data.datasets[0].data = dayAmountList;
    myChart.update();
}
//更新首页表
function updateChart_index(AllDateList,AllAmountList){
    myChart.data.labels = AllDateList;
    myChart.data.datasets[0].data =AllAmountList;
    myChart.update();
}

var myChart;

function drawChart() {
    var salesctx = document.getElementById('salesCharts').getContext('2d');
    var data = {
        labels: sumCountList,
        datasets: [{
            label: 'Sales',
            backgroundColor: 'rgba(255, 99, 132, 0.2)', // Bar fill color
            borderColor: 'rgba(255, 99, 132, 1)', // Border color
            borderWidth: 1, // Border width
            data: drugNameList, // Sales data for each month
        }]
    };
    // var data = {
    //     labels: ['January', 'February', 'March', 'April', 'May', 'June'],
    //     datasets: [{
    //         label: 'Sales',
    //         backgroundColor: 'rgba(255, 99, 132, 0.2)', // Bar fill color
    //         borderColor: 'rgba(255, 99, 132, 1)', // Border color
    //         borderWidth: 1, // Border width
    //         data: [12, 19, 3, 5, 2, 3], // Sales data for each month
    //     }]
    // };

    // var options = {
    //     scales: {
    //         yAxes: [{
    //             ticks: {
    //                 beginAtZero: true
    //             }
    //         }]
    //     }
    // };


    myChart = new Chart(salesctx, {
        type: 'bar',
        data: data,
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }

    });
}
</script>

<template>
    <el-form :inline="true" class="top-selection">
        <el-form-item label="开始日期:">
            <div class="demo-date-picker" style="width: 80%;">
                <el-date-picker v-model="searchInfo.start_term" type="date" placeholder="请选择开始日期" size="small"
                    value-format="YYYY-MM-DD" />
            </div>
        </el-form-item>
        <el-form-item label="截止日期:">
            <div class="demo-date-picker" style="width: 80%;">
                <el-date-picker v-model="searchInfo.last_term" type="date" placeholder="请选择截至日期" size="small"
                    value-format="YYYY-MM-DD" />
            </div>
        </el-form-item>
        <el-form-item style="margin-left: -20px;">
            <el-button type="primary" size="small" @click="Count_list(), choose1()">药品销售数量</el-button>
            <el-button type="primary" size="small" @click="Amount_list(), choose2()">日销售金额统计</el-button>
            <el-button plain @click="dialogTableVisible = true">详细信息查看</el-button>

        </el-form-item>
    </el-form>

    <el-dialog v-model="dialogTableVisible" title="详细销售数据查看" width="350">
        <span class="for_explain"></span>{{ '开始日期：' + searchInfo.start_term + '&emsp;截止日期：' + searchInfo.last_term }}
        <el-table v-if="tmp === true" :data="saleCount">
            <el-table-column property="drugName" label="药品名称" width="150" />
            <el-table-column property="sumCounts" label="销售数量" width="200" />
        </el-table>
        <el-table v-if="tmp === false" :data="dayAmount">
            <el-table-column property="saleDate" label="日期" width="150" />
            <el-table-column property="saleAmount" label="销售额" width="200" />
        </el-table>
    </el-dialog>
    <div class="Echarts">
        <canvas id="salesCharts"></canvas>
    </div>
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