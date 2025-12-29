import { defineStore } from 'pinia';
import { setToken, getToken, clearSession, removeUserId, setUserId, setUserType } from '@/utils/auth';
import { loginApi, logoutApi } from '@/api/login/login';
import store from '@/store';
import { menuStore } from './menu';
import { tabsStore } from './tabs';
import { to } from 'await-to-js';

export const useUserStore = defineStore('user', () => {
	const token = ref(getToken());
	const userId = ref<string | number>('');
	const userName = ref('');
	const userType = ref<string | number>('');

	const login = async (userInfo: any) => {
		const [err, res] = await to(loginApi(userInfo));
		if (res) {
			const data = res.data;
			setToken(data.token);
			setUserId(data.userId);
			setUserType(data.userType);
			token.value = data.token;
			userName.value = data.username;
			userType.value = data.userType;
			userId.value = data.userId;
			return Promise.resolve();
		}
		return Promise.reject(err);
	};

	const logout = async (): Promise<void> => {
		await logoutApi();
		token.value = '';
		userName.value = '';
		userType.value = '';
		userId.value = '';
		const { resetMenuList } = menuStore();
		const { resetTab } = tabsStore();
		clearSession();
		resetMenuList();
		resetTab();
	};

	return {
		userId,
		userName,
		userType,
		login,
		logout
	};
});

export default useUserStore;
// 非setup
export function useUserStoreHook() {
	return useUserStore(store);
}

// function resetMenuList() {
// 	throw new Error('Function not implemented.');
// }

// function resetTab() {
// 	throw new Error('Function not implemented.');
// }
// const useUserStore = defineStore('user', {
//   state: () => ({
//     token: getToken(),
//     id: '',
//     name: ''
//   }),

//   actions: {
//     login(userInfo) {
//       console.log(userInfo);
//       const username = userInfo.username.trim();
//       const password = userInfo.password;
//       const userType = userInfo.userType;
//       const loginParm = {
//         username: username,
//         password: password,
//         userType: userType
//       };
//       return new Promise((resolve, reject) => {
//         loginApi(loginParm)
//           .then((res) => {
//             setToken(res.data.token);
//             setUserId(res.data.userId);
//             setUserType(res.data.userType);
//             this.token = res.data.token;
//             resolve();
//           })
//           .catch((error) => {
//             reject(error);
//           });
//       });
//     },
//     // 退出系统
//     logOut() {
//       const { resetMenuList } = menuStore();
//       const { resetTab } = tabsStore();

//       return new Promise((resolve, reject) => {
//         logoutApi()
//           .then(() => {
//             console.log('清理存储信息');
//             clearSession();
//             resetMenuList();
//             resetTab();
//             resolve();
//           })
//           .catch((error) => {
//             reject(error);
//           });
//       });
//     }
//   }
// });

// export default useUserStore;
