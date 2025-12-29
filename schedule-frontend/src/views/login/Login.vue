<template>
	<div class="login-container">
		<el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="loginForm">
			<h3 class="loginTitle">实训中心约课系统</h3>
			<el-form-item prop="username">
				<el-input v-model="loginForm.username" type="text" size="large" auto-complete="off" placeholder="账号" :prefix-icon="Avatar">
					<template #prefix><svg-icon icon-class="user" class="el-input__icon input-icon" /></template>
				</el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input
					v-model="loginForm.password"
					type="password"
					size="large"
					:show-password="true"
					auto-complete="off"
					placeholder="密码"
					@keyup.enter="handleLogin"
					:prefix-icon="CircleCheck"
				>
					<template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template>
				</el-input>
			</el-form-item>

			<el-form-item prop="code" v-if="captchaEnabled">
				<el-input
					v-model="loginForm.code"
					size="large"
					auto-complete="off"
					placeholder="验证码"
					style="width: 200px"
					@keyup.enter="handleLogin"
				>
					<template #prefix><svg-icon icon-class="validCode" class="el-input__icon input-icon" /></template>
				</el-input>

				<div class="login-code">
					<img :src="codeUrl" @click="getCode" class="login-code-img" />
				</div>
			</el-form-item>
			<el-form-item prop="userType">
				<el-radio-group v-model="loginForm.userType" class="ml-4">
					<el-radio label="2" size="large" style="color: #fff;">教师</el-radio>
					<el-radio label="3" size="large" style="color: #fff;">管理员</el-radio>
				</el-radio-group>
			</el-form-item>

			<el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;color: #fff;">记住我</el-checkbox>

			<el-form-item style="width:100%;">
				<el-button :loading="loading" size="large" type="primary" style="width:100%;" @click.prevent="handleLogin">
					<span v-if="!loading">登 录</span>
					<span v-else>登 录 中...</span>
				</el-button>
				<div style="float: right;" v-if="register">
					<router-link class="link-type" :to="'/register'">立即注册</router-link>
				</div>
			</el-form-item>
		</el-form>
	</div>
</template>
<script setup lang="ts">
import { getCodeImg } from '@/api/login';
import { useUserStore } from '@/store/user';
import { LoginData } from '@/api/types';
import { to } from 'await-to-js';
import { Avatar, CircleCheck } from '@element-plus/icons-vue'

const userStore = useUserStore();
const router = useRouter();

const loginForm = ref<LoginData>({
  username: '',
  password: '',
  rememberMe: false,
  userType: "2",
  code: '',
  uuid: ''
} as LoginData);

const loginRules: ElFormRules = {
  username: [{ required: true, trigger: 'blur', message: '请输入您的账号' }],
  password: [{ required: true, trigger: 'blur', message: '请输入您的密码' }],
  code: [{ required: true, trigger: 'change', message: '请输入验证码' }]
};

const codeUrl = ref('');
const loading = ref(false);
// 验证码开关
const captchaEnabled = ref(true);


// 注册开关
const register = ref(false);
const redirect = ref(undefined);
const loginRef = ref<ElFormInstance>();

watch(() => router.currentRoute.value, (newRoute: any) => {
  redirect.value = newRoute.query && newRoute.query.redirect;
}, { immediate: true });

const handleLogin = () => {
  loginRef.value?.validate(async (valid: boolean, fields: any) => {
    if (valid) {
      loading.value = true;
      // 勾选了需要记住密码设置在 localStorage 中设置记住用户名和密码
      if (loginForm.value.rememberMe) {
        localStorage.setItem('username', String(loginForm.value.username));
        localStorage.setItem('password', String(loginForm.value.password));
        localStorage.setItem("userType", String(loginForm.value.userType));
        localStorage.setItem('rememberMe', String(loginForm.value.rememberMe));
      } else {
        // 否则移除
        localStorage.removeItem("userType");
        localStorage.removeItem('username');
        localStorage.removeItem('password');
        localStorage.removeItem('rememberMe');
      }
      // 调用action的登录方法
      const [err] = await to(userStore.login(loginForm.value));
      if (!err) {
        await router.push({ path: redirect.value || '/' });
        loading.value = false;
      } else {
        loading.value = false;
        // 重新获取验证码
        // if (captchaEnabled.value) {
        //   await getCode();
        // }
      }
    } else {
      console.log('error submit!', fields);
    }
  });
};

/**
 * 获取验证码
 */
const getCode = async () => {
  const res = await getCodeImg();
  const { data } = res;
  captchaEnabled.value = data.captchaEnabled === undefined ? true : data.captchaEnabled;
  if (captchaEnabled.value) {
    codeUrl.value = 'data:image/gif;base64,' + data.img;
    loginForm.value.uuid = data.uuid;
  }
};

const getLoginData = () => {
  const username = localStorage.getItem('username');
  const password = localStorage.getItem('password');
  const rememberMe = localStorage.getItem('rememberMe');
  const userType = localStorage.getItem('userType');
  loginForm.value = {
    username: username === null ? String(loginForm.value.username) : username,
    password: password === null ? String(loginForm.value.password) : String(password),
    rememberMe: rememberMe === null ? false : Boolean(rememberMe),
    userType: userType === null ? '2' : '3',
  } as LoginData;
}




onMounted(() => {
  getCode();
  getLoginData();
});
</script>
<style scoped lang="scss">
.login-container {
  height: 100%;
  background: #fff;
  background-image: url("@/assets/login.png");
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;
  position: relative;
  .loginForm {
    height: 450px;
    width: 450px;
    background: rgba(51,51,51,.75);
   //background-color:  #ffffffe5;
    padding: 35px 20px;
    border-radius: 10px;
    .loginTitle {
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      font-weight: 600;
      color: #fff;
    }
     .mybtn {
      width: 100%;
    }
  }
}

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 40px;

    input {
      height: 40px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 0px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 40px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial, serif;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 40px;
  padding-left: 12px;
}
</style>
