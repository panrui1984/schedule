import http from '@/http';

/**
 * 用户密码修改
 * @param oldPassword 旧密码
 * @param newPassword 新密码
 */
export const updateUserPwd = (oldPassword: string, newPassword: string) => {
	const data = {
		oldPassword,
		newPassword
	};
	return http.put({
		url: '/api/user/profile/updatePwd',
		data: data
	});
};
