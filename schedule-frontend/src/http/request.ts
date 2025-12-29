import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import { message as Message, Modal } from 'ant-design-vue';
import { getToken } from '@/utils/auth';
import useUserStore from '@/store/user';
import errorCode from '@/utils/errorCode';
import { tansParams } from '@/utils/tool';
import cache from '@/plugins/cache';
import { HttpStatus } from '@/enums/RespEnum';
import 'ant-design-vue/es/message/style/css'; //vite只能用 ant-design-vue/es 而非 ant-design-vue/lib
//定义返回值类型
export interface Result<T = any> {
	code: number;
	msg: string;
	data: T;
}
// 是否显示重新登录
export const isRelogin = { show: false };

class request {
	//axios实例
	private instance: AxiosInstance;
	//构造函数里面初始化
	constructor(config: AxiosRequestConfig) {
		axios;
		this.instance = axios.create(config);
		//定义拦截器
		this.interceptors();
	}
	//拦截器
	private interceptors() {
		//axios发送请求之前的处理
		this.instance.interceptors.request.use(
			(config: InternalAxiosRequestConfig) => {
				const isToken = (config.headers || {}).isToken === false;
				// 是否需要防止数据重复提交
				const isRepeatSubmit = (config.headers || {}).repeatSubmit === false;
				if (getToken() && !isToken && config.headers) {
					config.headers['Authorization'] = 'Bearer ' + getToken(); // 让每个请求携带自定义token
				}
				// get请求映射params参数
				if (config.method === 'get' && config.params) {
					let url = config.url + '?' + tansParams(config.params);
					url = url.slice(0, -1);
					config.params = {};
					config.url = url;
				}
				if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
					const requestObj = {
						url: config.url,
						data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
						time: new Date().getTime()
					};
					const sessionObj = cache.session.getJSON('sessionObj');
					if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
						cache.session.setJSON('sessionObj', requestObj);
					} else {
						const s_url = sessionObj.url; // 请求地址
						const s_data = sessionObj.data; // 请求数据
						const s_time = sessionObj.time; // 请求时间
						const interval = 500; // 间隔时间(ms)，小于此时间视为重复提交
						if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
							const message = '数据正在处理，请勿重复提交';
							console.warn(`[${s_url}]: ` + message);
							return Promise.reject(new Error(message));
						} else {
							cache.session.setJSON('sessionObj', requestObj);
						}
					}
				}
				// FormData数据去请求头Content-Type
				if (config.data instanceof FormData) {
					delete config.headers['Content-Type'];
				}
				return config;
			},
			(error: any) => {
				error.data = {};
				error.data.msg = '服务器异常，请联系管理员!';
				return error;
			}
		);
		//axios请求返回之后的处理
		//请求返回之后的处理
		this.instance.interceptors.response.use(
			(res: AxiosResponse) => {
				const code = res.data.code || HttpStatus.SUCCESS;
				const msg = errorCode[code] || res.data.msg || errorCode['default'];
				// 二进制数据则直接返回
				if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
					return res.data;
				}
				if (code === 401) {
					if (!isRelogin.show) {
						isRelogin.show = true;
						ElMessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
							confirmButtonText: '重新登录',
							cancelButtonText: '取消',
							type: 'warning'
						})
							.then(() => {
								isRelogin.show = false;
								useUserStore()
									.logout()
									.then(() => {
										location.href = import.meta.env.VITE_APP_CONTEXT_PATH + 'index';
									});
							})
							.catch(() => {
								isRelogin.show = false;
							});
					}
					return Promise.reject('无效的会话，或者会话已过期，请重新登录。');
				} else if (code === HttpStatus.SERVER_ERROR) {
					console.log(code);
					console.log(msg);
					ElMessage({ message: msg, type: 'error' });
					return Promise.reject(new Error(msg));
				} else if (code === HttpStatus.WARN) {
					ElMessage({ message: msg, type: 'warning' });
					return Promise.reject(new Error(msg));
				} else if (code !== HttpStatus.SUCCESS) {
					ElNotification.error({ title: msg });
					return Promise.reject('error');
				} else {
					return Promise.resolve(res.data);
				}
			},
			(error: any) => {
				let { message } = error;
				if (message == 'Network Error') {
					message = '后端接口连接异常';
				} else if (message.includes('timeout')) {
					message = '系统接口请求超时';
				} else if (message.includes('Request failed with status code')) {
					message = '系统接口' + message.substr(message.length - 3) + '异常';
				}
				Message.error(message, 5);
				return Promise.reject(error);
			}
		);
	}

	service<T>(config: AxiosRequestConfig): Promise<T> {
		return new Promise((resolve, reject) => {
			this.instance
				.request<any, T>(config)
				.then((res) => {
					resolve(res);
				})
				.catch((error) => {
					reject(error);
					return error;
				});
		});
	}

	//get请求
	get<T = Result>(config: AxiosRequestConfig): Promise<T> {
		return this.service<T>({ ...config, method: 'GET' });
	}
	//post请求
	post<T = Result>(config: AxiosRequestConfig): Promise<T> {
		return this.service<T>({ ...config, method: 'POST' });
	}
	//delete请求
	delete<T = Result>(config: AxiosRequestConfig): Promise<T> {
		return this.service<T>({ ...config, method: 'DELETE' });
	}
	//put请求
	put<T = Result>(config: AxiosRequestConfig): Promise<T> {
		return this.service<T>({ ...config, method: 'PUT' });
	}
}

export default request;
