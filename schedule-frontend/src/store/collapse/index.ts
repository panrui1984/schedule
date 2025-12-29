import { defineStore } from 'pinia';
export const collapseStore = defineStore('isCollapse', {
	//定义state
	state: () => {
		return {
			isCollapse: false
		};
	},
	//定义getters
	getters: {
		getCollapse: (state) => {
			return state.isCollapse;
		}
	},
	//定义actions
	actions: {
		setCollapse() {
			this.isCollapse = !this.isCollapse;
		}
	}
});
