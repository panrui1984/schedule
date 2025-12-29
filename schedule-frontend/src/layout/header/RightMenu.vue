<template>
	<!-- <a-dropdown placement="bottomLeft">
    <a class="ant-dropdown-link">
      <img class="right-img" src="@/assets/user.png" />
    </a>
    <template #overlay>
      <a-menu>
        <a-menu-item key="1" @click="resetPwdShow">修改密码</a-menu-item>
        <a-menu-item key="2" @click.native="loingout">退出登录</a-menu-item>
      </a-menu>
    </template> 
    
  </a-dropdown> -->
	<el-dropdown @command="handleCommand" class="right-menu-item hover-effect" trigger="click">
		<div class="avatar-wrapper">
			<img src="@/assets/user.png" class="right-img" />
			<el-icon><caret-bottom /></el-icon>
		</div>
		<template #dropdown>
			<el-dropdown-menu>
				<router-link to="/users/profile">
					<el-dropdown-item>个人中心</el-dropdown-item>
				</router-link>
				<el-dropdown-item divided command="logout">
					<span>退出登录</span>
				</el-dropdown-item>
			</el-dropdown-menu>
		</template>
	</el-dropdown>
	<!-- 个人中心 -->
	<reset-pwd ref="resetPwd"></reset-pwd>
</template>
<script setup lang="ts">
import { ref, reactive } from 'vue';
import useUserStore from '@/store/user';
import ResetPwd from '@/views/system/user/ResetPwd.vue';

const logout = async () => {
    await ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await userStore.logout();
    location.href = import.meta.env.VITE_APP_CONTEXT_PATH;
}

// 修改密码
const resetPwdShow = ()=>{
    resetPwd.value?.show()
}


// 定义Command方法对象 通过key直接调用方法
const commandMap: {[key: string]: any} = {
    resetPwdShow,
    logout
};
const handleCommand = (command: string) => {
    // 判断是否存在该方法
    if (commandMap[command]) {
        commandMap[command]();
    }
}



//排课界面ref属性
const resetPwd = ref<{show:()=>void}>();


const userStore = useUserStore();
</script>
<style scoped lang="scss">
.right-img {
    width: 30px;
    height: 30px;
}
</style>
