import http from '@/http';
import { LoginParm } from './LoginType';
import { getUserId, getUserType } from '@/utils/auth';
//登录
export const loginApi = (parm: LoginParm) => {
	return http.post({
		url: '/api/login/login',
		headers: {
			isToken: false
		},
		method: 'post',
		data: parm
	});
};
//退出
export const logoutApi = () => {
	return http.post({
		url: '/api/login/logout',
		method: 'post'
	});
};
//获取菜单数据
export const getMenuListApi = () => {
	return http.get({
		url: '/api/login/getMenuList',
		params: {
			userId: getUserId(),
			userType: getUserType()
		}
	});
};
