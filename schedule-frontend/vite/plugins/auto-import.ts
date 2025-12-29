import AutoImport from 'unplugin-auto-import/vite';
import IconsResolver from 'unplugin-icons/resolver';
import { AntDesignVueResolver, ElementPlusResolver } from 'unplugin-vue-components/resolvers';

export default (path: any) => {
	return AutoImport({
		// 自动导入 Vue 相关函数
		imports: ['vue', 'vue-router', 'pinia'],
		eslintrc: {
			enabled: false,
			filepath: './.eslintrc-auto-import.json',
			globalsPropValue: true
		},
		resolvers: [
			// 自动导入 Element Plus 相关函数ElMessage, ElMessageBox... (带样式)
			AntDesignVueResolver(),
			ElementPlusResolver(),
			IconsResolver({
				prefix: 'Icon'
			})
		],
		vueTemplate: true, // 是否在 vue 模板中自动导入
		dts: path.resolve(path.resolve(__dirname, '../../src'), 'types', 'auto-imports.d.ts')
	});
};
