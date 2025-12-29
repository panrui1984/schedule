/**
 * 登录请求
 */
export interface LoginData {
	username?: string;
	password?: string;
	rememberMe?: boolean;
	code?: string;
	uuid?: string;
	userType: string;
}

/**
 * 登录响应
 */
export interface LoginResult {
	access_token: string;
}
