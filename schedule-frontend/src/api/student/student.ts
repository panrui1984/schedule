import http from '@/http';
import { ListParm, StudentModel } from './StudentType';
//新增
export const addStudentApi = (parm: StudentModel) => {
	return http.post({
		url: '/api/student',
		data: parm
	});
};
//列表
export const getListApi = (parm: ListParm) => {
	return http.get({
		url: '/api/student/list',
		params: parm
	});
};

//编辑
export const editApi = (parm: StudentModel) => {
	return http.put({
		url: '/api/student',
		data: parm
	});
};
//删除
export const deleteApi = (stuId: string) => {
	return http.delete({
		url: `/api/student/${stuId}`
	});
};
//根据id查询教师
export const getStuentByIdApi = (stuId: string) => {
	return http.get({
		url: '/api/student/getStudent',
		params: { stuId: stuId }
	});
};
//重置密码
export const resetPassApi = (stuId: string) => {
	return http.post({
		url: '/api/student/resetPassword',
		data: { stuId: stuId }
	});
};
