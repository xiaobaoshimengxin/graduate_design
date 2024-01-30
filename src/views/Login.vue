<script lang = "ts" setup>
import { ref } from 'vue'
import { adminLoginService } from '@/api/admin.js'
import { ElMessage } from 'element-plus'

const ruleForm = ref({
    account: '',
    password: ''
})

import { useRouter } from 'vue-router'
import { useTokenStore } from '@/stores/token.js'
import { useInfoStore }from '@/stores/userInfo.js'
const router = useRouter();
const tokenStore = useTokenStore();
const useStore = useInfoStore();
const login = async () => {
    let result = await adminLoginService(ruleForm.value);
    ElMessage.success(result.msg ? result.msg : "登陆成功");
    // 存储token到pinia
    let jwt = result.data.jwt;
    tokenStore.setToken(jwt);
    let id = result.data.id;
    useStore.setId(id);
    let power = result.data.power;
    useStore.setPower(power);
    // 登陆成功，跳转
    router.push('/');
}

const reset = () => {
    ruleForm.value.account = '';
    ruleForm.value.password = ''
}

</script>

<template>
    <div id="loginBox">
        <form>
            <h2>登录</h2>
            <br>
            <div class="center">
                <span> username: </span> <el-input style="width: 212px;" size="small" v-model="ruleForm.account"
                    placeholder="请输入账户" type="text" />
            </div>
            <br><br>
            <div class="center">
                <span> password: </span> <el-input style="width: 212px;" size="small" v-model="ruleForm.password"
                    placeholder="请输入密码" type="password" />
            </div>
            <br><br>
            <div style="margin-left: auto;margin-right: auto;width: 70%;">
                <el-button style="width: 30%;" size="small" type="primary" @click="login">登录</el-button>

                <el-button style="width: 30%; margin-left: 40%;" size="small" @click="reset">重置</el-button>
            </div>
        </form>
    </div>
</template>

<style scoped>
.center {
    margin-left: auto;
    margin-right: auto;
    width: 70%;
}

#loginBox {
    font-family: 'Times New Roman', Times, serif;
    background-color: rgb(255, 255, 255);
    margin: 10% auto;
    width: 400px;
    padding-top: 30px;
    padding-bottom: 30px;
}

h2 {
    margin-left: auto;
    margin-right: auto;
    width: 15%;
    text-align: justify;
}
</style>