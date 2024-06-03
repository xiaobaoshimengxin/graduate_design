<script setup>
import { ref } from 'vue'
import { adminLoginService } from '@/api/admin.js'
import { ElMessage } from 'element-plus'

const ruleForm = ref({
    account: '',
    password: ''
})

import { useRouter } from 'vue-router'
import { useTokenStore } from '@/stores/token.js'
import { useInfoStore } from '@/stores/userInfo.js'
const router = useRouter();
const tokenStore = useTokenStore();
const useStore = useInfoStore();
const login = async () => {
    let result = await adminLoginService(ruleForm.value);
    ElMessage.success(result.msg ? result.msg : "登录成功");
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
    <div class="bg-pic">
        <div id="loginBox">
            <form>
                <h2>登录</h2>
                <br>
                <div class="center">
                    <span> 用户名: </span> <el-input style="width: 212px;" size="small" v-model="ruleForm.account"
                        placeholder="请输入账户" type="text" />
                </div>
                <br><br>
                <div class="center">
                    <span> 密&emsp;码: </span> <el-input style="width: 212px;" size="small" v-model="ruleForm.password"
                        placeholder="请输入密码" type="password" />
                </div>
                <br><br>
                <div style="margin-left: auto;margin-right: auto;width: 70%;padding-bottom: 40px;">
                    <el-button style="width: 30%;" size="small" type="primary" @click="login">登录</el-button>

                    <el-button style="width: 30%; margin-left: 40%;" size="small" @click="reset">重置</el-button>
                </div>
            </form>
        </div>
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
    width: 400px;
    position: relative;
    float: left;
    margin-top: 200px;
    left: 40%;
}

h2 {
    padding-top: 30px;
    margin-left: auto;
    margin-right: auto;
    width: 15%;
    text-align: justify;
}

.bg-pic {
    width: 100vvw;
    height: 100vh;
    background-image: url('../media/background.png');
    background-size: cover; /* 背景图片覆盖整个元素 */
    background-position: center; /* 背景图片居中 */
    position: relative;
    text-align: center;
}
</style>