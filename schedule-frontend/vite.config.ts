import { UserConfig, ConfigEnv, loadEnv, defineConfig } from 'vite';

import createPlugins from './vite/plugins';

import path from 'path';

// https://vitejs.dev/config/
export default defineConfig(({ mode, command }: ConfigEnv): UserConfig => {
	const env = loadEnv(mode, process.cwd());
	return {
		resolve: {
			alias: {
				'~': path.resolve(__dirname, './'),
				'@': path.resolve(__dirname, './src')
			},
			extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
		},
		plugins: createPlugins(env, command === 'build'),

		server: {
			port: 80,
			host: '0.0.0.0',
			open: true,
			proxy: {
				// https://cn.vitejs.dev/config/#server-proxy
				'/schedule-admin': {
					target: 'http://localhost:8080',
					changeOrigin: true,
					rewrite: (p) => p.replace(/^\/schedule-admin/, '')
				}
			}
		},

		// css: {
		//   preprocessorOptions: {
		//     scss: {
		//       javascriptEnabled: true
		//     }
		//   },
		//   postcss: {
		//     plugins: [
		//       {
		//         postcssPlugin: 'internal:charset-removal',
		//         AtRule: {
		//           charset: (atRule) => {
		//             if (atRule.name === 'charset') {
		//               atRule.remove();
		//             }
		//           }
		//         }
		//       }
		//     ]
		//   }
		// },
		// 预编译
		optimizeDeps: {
			include: ['vue', 'vue-router', 'pinia', 'axios', '@vueuse/core']
		}
	};
});
