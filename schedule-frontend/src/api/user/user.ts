import http from '@/http';
import { ListParm, UserType, User } from '@/api/user/UserType';
//获取角色
export const getRoleListApi = () => {
	return http.get({
		url: '/api/user/role'
	});
};
//新增
export const addUserApi = (parm: UserType) => {
	return http.post({
		url: '/api/user',
		data: parm
	});
};
//列表
export const getListApi = (parm: ListParm) => {
	return http.get({
		url: '/api/user/list',
		params: parm
	});
};
//根据id查询用户
export const getUserByIdApi = (useId: string) => {
	return http.get({
		url: '/api/user/getUser',
		params: { userId: useId }
	});
};

//编辑
export const editUserApi = (parm: UserType) => {
	return http.put({
		url: '/api/user',
		data: parm
	});
};
//删除
export const deleteUserApi = (userId: string) => {
	return http.delete({
		url: `/api/user/${userId}`
	});
};
