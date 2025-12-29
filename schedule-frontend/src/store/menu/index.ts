import { defineStore } from 'pinia';
import { getMenuListApi } from '@/api/login/login';
import { RouteRecordRaw } from 'vue-router';
import Layout from '@/layout/index.vue';
import Center from '@/layout/center/center.vue';
const modules = import.meta.glob('../../views/**/*.vue');
export const menuStore = defineStore('menuStore', {
	//定义state
	state: () => {
		return {
			//菜单、路由数据
			menuList: [],
			//权限字段
			authList: []
		};
	},
	//定义getters
	getters: {
		getMenu: (state) => {
			return state.menuList;
		},
		getAuth: (state) => {
			return state.authList;
		}
	},
	//定义actions
	actions: {
		async getMenuList(router: any) {
			//请求菜单数据
			const res = await getMenuListApi();
			let accessedRoutes = [] as any;
			if (res && res.code == 200) {
				//生成路由数据
				accessedRoutes = filterAsyncRoutes(res.data.menuList, router);
				//存储权限字段
				this.authList = res.data.authList;
			}
			if (accessedRoutes.length > 0) {
				const desk = [
					{
						path: '/dashboard',
						component: 'Layout',
						meta: {
							title: '我的约课',
							icon: 'HomeFilled',
							roles: ['sys:manage']
						},
						children: []
					}
				] as any;
				this.menuList = this.menuList.concat(desk).concat(accessedRoutes);
			}
		},
		async resetMenuList() {
			console.log('清理menu store');
			//菜单、路由数据
			(this.menuList = []),
				//权限字段
				(this.authList = []);
		}
	}
});
//生成动态路由
export function filterAsyncRoutes(routes: RouteRecordRaw[], router: any) {
	//定义接收生成的路由数据
	const res: Array<RouteRecordRaw> = [];
	//循环处理路由
	routes.forEach((route: any) => {
		//解构，把每一个路由放到新的 tmp对象
		const tmp = { ...route };
		const component = tmp.component;
		if (route.component) {
			if (component == 'Layout') {
				//一级菜单
				tmp.component = Layout;
			} else {
				tmp.component = modules[`../../views${component}`];
			}
		}
		//有下级
		if (tmp.children && tmp.children.length > 0) {
			if (route.component != 'Layout') {
				tmp.component = Center;
			}
			//递归下级
			tmp.children = filterAsyncRoutes(tmp.children, router);
		}
		//添加路由
		router.addRoute(tmp);
		res.push(tmp);
	});
	return res;
}
