import http from '@/http';
import { ListParm, TeacherModel } from './TeacherType';
//新增
export const addTeacherApi = (parm: TeacherModel) => {
	return http.post({
		url: '/api/teacher',
		data: parm
	});
};
//列表
export const getListApi = (parm: ListParm) => {
	return http.get({
		url: '/api/teacher/list',
		params: parm
	});
};

//编辑
export const editApi = (parm: TeacherModel) => {
	return http.put({
		url: '/api/teacher',
		data: parm
	});
};
//删除
export const deleteApi = (teacherId: string) => {
	return http.delete({
		url: `/api/teacher/${teacherId}`
	});
};
//根据id查询教师
export const getTeacherByIdApi = (teacherId: string) => {
	return http.get({
		url: '/api/teacher/getTeacher',
		params: { teacherId: teacherId }
	});
};
//重置密码
export const resetPassApi = (teacherId: string) => {
	return http.post({
		url: '/api/teacher/resetPassword',
		data: { teacherId: teacherId }
	});
};
