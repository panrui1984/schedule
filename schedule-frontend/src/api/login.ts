import http from '@/http';
/**
 * 获取验证码
 */
export function getCodeImg() {
	return http.get({
		url: '/api/auth/code',
		headers: {
			isToken: false
		},
		method: 'get',
		timeout: 20000
	});
}
