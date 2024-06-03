<script setup>
import { reactive, toRefs, ref } from 'vue'
import { resetPasswordService } from '@/api/admin.js'
import { ArrowDown } from '@element-plus/icons-vue'
import { useTokenStore } from '@/stores/token.js'
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import {useInfoStore} from '@/stores/userInfo.js'
const userStore =  useInfoStore();
const router = useRouter();
const tokenstore = useTokenStore();

const state = reactive({
    circleUrl:
        'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
})
const { circleUrl } = toRefs(state)

const passwordInfo = ref({
    oldPassword: '',
    newPassword: ''
})

const resetDialog = ref(false);

const handleCommand = (command) => {
    if (command === 'logout') {
        tokenstore.clearToken();
        ElMessage.success("退出登录成功");
        router.push('/login')
    } else {
        passwordInfo.value.oldPassword = '';
        passwordInfo.value.newPassword = '';
        resetDialog.value = true;
    }
}
const resetPassword = async () => {
    await resetPasswordService(passwordInfo.value);
    ElMessage.success("修改成功,请重新登录");
    tokenstore.clearToken();
    router.push('/login');
    resetDialog.value = false;
}

</script>

<template>
    <!-- <h1>LayoutVue</h1> -->
    <div class="common-layout">
        <el-container>
            <el-aside class="aside">
                <el-menu id="asideMenu" background-color=slategrey text-color="#fff" router active-text-color="#ffd04b"
                    >
                    <el-menu-item index="/search">
                        <span>库存药品管理</span>
                    </el-menu-item>
                    <el-sub-menu index="/purchase">
                        <template #title><span>采购管理</span></template>
                        <el-menu-item index="/purchase/history">采购历史</el-menu-item>
                        <el-menu-item index="/purchase/add" :disabled="userStore.power<2">新增采购</el-menu-item>
                    </el-sub-menu>
                    <el-sub-menu index="/sales">
                        <template #title><span>销售管理</span></template>
                        <el-menu-item index="/sales/history">销售历史</el-menu-item>
                        <el-menu-item index="/sales/salecount" :disabled="userStore.power<2">销售统计</el-menu-item>
                        <el-menu-item index="/sales/add">新增销售</el-menu-item>
                    </el-sub-menu>
                    <el-sub-menu index="/manage">
                        <template #title><span>系统管理</span></template>
                        <el-menu-item index="/manage/supplier" :disabled="userStore.power<2">供应商管理</el-menu-item>
                        <el-menu-item index="/manage/employee" :disabled="userStore.power<3">员工管理</el-menu-item>
                        <el-menu-item index="/manage/customer">顾客管理</el-menu-item>
                        <el-menu-item index="/manage/record" >充值记录</el-menu-item>
                    </el-sub-menu>
                </el-menu>
            </el-aside>
            <el-container style="margin-left: 150px;">
                <el-header class="header">
                    <span id="Tit">药店管理系统</span>
                    <el-dropdown class="heahMenu" @command="handleCommand">
                        <span>
                            <el-avatar :src="circleUrl" />
                            <el-icon class="el-icon--right">
                                <arrow-down style="padding-bottom: 90%;" />
                            </el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item command="password">修改密码</el-dropdown-item>
                                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-header>
                <el-main>
                    <div class="mainContent">
                        <router-view></router-view>
                    </div>
                </el-main>
            </el-container>
        </el-container>
    </div>

    <el-dialog v-model="resetDialog" title="修改密码" width="30%" center>
        <el-form style="text-align: center;" :model="passwordInfo" :inline="true">
            <span>旧密码：</span> <el-input style="width: 50%;" size="small" v-model="passwordInfo.oldPassword" type="password"
                placeholder="旧密码"></el-input>
            <br><br><br>
            <span>新密码：</span> <el-input style="width: 50%;" size="small" v-model="passwordInfo.newPassword" type="password"
                placeholder="新密码"></el-input>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="resetDialog = false">取消</el-button>
                <el-button type="primary" @click="resetPassword">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style scoped>
html {
    margin: 0;
    padding: 0;
}

.aside {
    background-color: slategrey;
    border: none;
    height: 100vh;
    width: 150px;
    position: fixed;
    /* 固定定位 */
    top: 0;
    /* 从顶部开始显示 */
    left: 0;
    /* 左侧边界与视口左边界重合 */
}

#asideMenu {
    border-right: 1px solid slategrey;
    text-align: center;

}

.header {
    border-top: 1px solid rgb(208, 203, 203);
    /* border-bottom: 1px solid rgb(208, 203, 203); */
    background-color: rgb(255, 255, 255);
    position: fixed;
    /* 固定定位 */
    top: 0;
    /* 从顶部开始显示 */
    right: 0;
    /* 左侧边界与视口左菜单重合 */
    left: 150px;
    z-index: 999;
}

#Tit {
    display: flex inline;
    height: 100%;
    width: 70%;
    align-items: center;
    font-size: 30px;
}

.mainContent {
    margin-top: 60px;
    background-color: #fff;
}

.heahMenu {
    position: relative;
    float: right;
    top: 10px;
}

* {
    margin: 0;
    padding: 0;
}
</style>