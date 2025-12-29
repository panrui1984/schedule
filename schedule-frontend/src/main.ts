import { createApp } from 'vue';
import App from './App.vue';
//引入ant design vue
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
//引入图标
import * as Icons from '@ant-design/icons-vue';
//引入路由
import router from './router';

//对象复制
import objCoppy from './utils/objCoppy';
//信息确定弹框
import myconfirm from './utils/myconfirm';
const app = createApp(App);
//引入按钮权限
import { permission } from '@/directives/permission';
// 预设动画
import animate from './animate';

import { useDict } from '@/utils/dict';
import { parseTime, addDateRange, handleTree, selectDictLabel, selectDictLabels } from '@/utils/ruoyi';
// svg图标
import 'virtual:svg-icons-register';
import ElementIcons from '@/plugins/svgicon';
//全局挂载
app.config.globalProperties.useDict = useDict;
app.config.globalProperties.$objCoppy = objCoppy;
app.config.globalProperties.$myconfirm = myconfirm;
app.config.globalProperties.animate = animate;
app.config.globalProperties.handleTree = handleTree;
app.config.globalProperties.parseTime = parseTime;
//注册图标组件
Object.keys(Icons).forEach((key) => {
	app.component(key, Icons[key as keyof typeof Icons]);
});
//引入Pinia
import { createPinia } from 'pinia';
const pinia = createPinia();

import VueTippy from 'vue-tippy';

//注册插件
import plugins from './plugins/index';

app.use(pinia)
	.use(router)
	.use(Antd)
	.use(plugins)
	.use(
		VueTippy,
		// optional
		{
			directive: 'tippy', // => v-tippy
			component: 'tippy', // => <tippy/>
			componentSingleton: 'tippy-singleton', // => <tippy-singleton/>,
			defaultProps: {
				placement: 'auto-end',
				allowHTML: true
			} // => Global default options * see all props
		}
	)
	.mount('#app');

//按钮权限
app.directive('permission', permission);
//权限验证
import './permission';
