<script lang="ts" setup>
import { ref } from 'vue';
import { ElMessage, rowProps } from 'element-plus';
import { employeeListService, empAddService, empUpdateService } from '@/api/manage.js'

const options = ref([
    {
        supplierId: 1,
        supplierName: '员工',
    }, {
        supplierId: 2,
        supplierName: '主管',
    }
])

const employeeData = ref([{
    id: '',  // 主键id
    name: '',    // 姓名
    birth: '',  // 出生日期
    account: '',     // 账号/电话
    address: '',    // 住址
    createTime: '',  // 创建时间/入职时间
    statue: 1, // 状态：1正常，0禁用
    power: '', // 权限：admin：3其他默认1
}])

let emp = ref({
    id: '',  // 主键id
    name: '',    // 姓名
    birth: '',  // 出生日期
    account: '',     // 账号
    address: '',    // 住址
    createTime: '',  // 创建时间/入职时间
    statue: 1, // 状态：1正常，0禁用
    power: '', // 权限：admin：3其他默认1
})

let index = 0;
const centerDialogVisible = ref(false)

const employeeList = async () => {
    let result = await employeeListService();
    employeeData.value = result.data;
    // employeeData.value.splice(0, 1);
}
employeeList();


const addData = () => {
    for (let key in emp.value) {
        emp.value[key] = '';
    }
    centerDialogVisible.value = true
    index = employeeData.value.length;
}

const employeeDataPush = async () => {
    if (index == employeeData.value.length) {
        // employeeData.value[index] = JSON.parse(JSON.stringify(emp.value));
        emp.value.createTime = new Date().toISOString().slice(0, 10);  // YYY-MM-DD格式时间
        // 发送添加请求
        await empAddService(emp.value);
        ElMessage.success("添加成功");
    } else {
        // 发送更新请求     
        await empUpdateService(emp.value);
        ElMessage.success("修改成功");
    }
    employeeList();
    centerDialogVisible.value = false;
    for (let key in emp.value) {
        emp.value[key] = '';
    }
}

const updataEmp = (ind) => {
    index = ind;
    emp.value = JSON.parse(JSON.stringify(employeeData.value[ind]));
    centerDialogVisible.value = true;
}

const updateStatue = async (ind) => {
    employeeData.value[ind].statue = employeeData.value[ind].statue ? 0 : 1;
    emp.value = JSON.parse(JSON.stringify(employeeData.value[ind]));
    // 发送更新请求
    await empUpdateService(emp.value);
    ElMessage.success("成功");
}

</script>

<template>
    <div>
        <div style="text-align: left;">
            <el-button class="fun-but" style="margin-left: 5%; " type="primary" @click="addData"> 新增员工 </el-button>
        </div>

        <el-table id="show-table" :data="employeeData" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }">
            <el-table-column prop="id" label="序号" width="90" />
            <el-table-column prop="name" label="名字" width="180"/>
            <el-table-column prop="birth" label="出生日期" />
            <el-table-column prop="address" label="籍贯" />
            <el-table-column prop="createTime" label="入职时间" />
            <el-table-column prop="account" label="联系方式" />
            <el-table-column prop="power" label="权限" width="180" />
            <el-table-column label="操作" width="180">
                <template #default="scope">
                    <el-button :disabled="employeeData[scope.$index].id == '1' || employeeData[scope.$index].statue === 0"
                        type="warning" size="small" @click="updataEmp(scope.$index)">修改</el-button>
                    <el-button :disabled="employeeData[scope.$index].id == '1'" type="danger" size="small"
                        @click="updateStatue(scope.$index)">{{
                            employeeData[scope.$index].statue ? "禁用" : "启用" }}</el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
    <el-dialog v-model="centerDialogVisible" title="新增员工" width="30%" center>
        <el-form id="dialog-form" :model="emp" :inline="true">
            <el-form-item label="员工姓名:">
                <el-input v-model="emp.name" placeholder="请输入员工姓名" size="small"></el-input>
            </el-form-item>
            <el-form-item label="电话号码:">
                <el-input v-model="emp.account" placeholder="请输入员工电话" size="small"></el-input>
            </el-form-item>
            <el-form-item label="员工籍贯:">
                <el-input v-model="emp.address" placeholder="请输入员工籍贯" size="small"></el-input>
            </el-form-item>
            <el-form-item label="出生日期:" style="width: 73%;">
                <el-date-picker v-model="emp.birth" type="date" placeholder="请输入日期" size="small"
                    value-format="YYYY-MM-DD" />
            </el-form-item>
            <el-form-item label="入职日期:" style="width: 73%;">
                <el-date-picker v-model="emp.createTime" type="date" placeholder="请选择入职日期" size="small"
                    value-format="YYYY-MM-DD" />
            </el-form-item>
            <el-form-item label="管理权限:" style="width: 70%;">
                <el-select v-model="emp.power" class="m-2" placeholder="权限" size="small">
                    <el-option v-for="item in options" :key="item.supplierId" :label="item.supplierName"
                        :value="item.supplierId" />
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="employeeDataPush">
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