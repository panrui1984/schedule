import http from '@/http';
import { AxiosPromise } from 'axios';
import { ProfileVO } from './type';

/**
 * 查询用户个人信息
 */
export const getUserProfile = (): AxiosPromise<ProfileVO> => {
	return http.get({
		url: '/api/user/profile',
		method: 'get'
	});
};
