import http from '@/http';
import { ListParm, ClassesModel } from './ClassesType';
//新增
export const addClassesApi = (parm: ClassesModel) => {
	return http.post({
		url: '/api/classes',
		data: parm
	});
};
//列表
export const getListApi = (parm: ListParm) => {
	return http.get({
		url: '/api/classes/list',
		params: parm
	});
};

//编辑
export const editApi = (parm: ClassesModel) => {
	return http.put({
		url: '/api/classes',
		data: parm
	});
};
//删除
export const deleteApi = (classId: string) => {
	return http.delete({
		url: `/api/classes/${classId}`
	});
};
export const getListForStuApi = () => {
	return http.get({
		url: '/api/classes/listClass'
	});
};
