import { defineStore } from 'pinia';

export interface Tabs {
	title: string; //菜单名称
	path: string; //当前路由的path
}

export interface State {
	tabsList: Array<Tabs>;
}
//定义store
export const tabsStore = defineStore('tabsStore', {
	//定义state
	state: (): State => {
		return {
			tabsList: []
		};
	},

	getters: {
		getTabs(state) {
			return state.tabsList;
		}
	},
	//定义actions
	actions: {
		//添加选项卡数据
		addTab(tab: Tabs) {
			//登录不需要放入选项卡
			if (tab.path == '/login') return;
			//判断数据是否在选项卡数据里面
			if (this.tabsList.some((item) => item.path === tab.path)) return;
			//添加到选项卡数据
			this.tabsList.push(tab);
		},
		resetTab() {
			this.tabsList = [];
		}
	}
});
