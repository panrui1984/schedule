import vue from '@vitejs/plugin-vue';
import { App } from 'vue';
import cache from './cache';
import modal from './modal';

export default function installPlugin(app: App) {
	// 缓存对象
	app.config.globalProperties.$cache = cache;

	// 模态框对象
	app.config.globalProperties.$modal = modal;
}
