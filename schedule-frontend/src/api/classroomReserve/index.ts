import http from '@/http';
import { ClassroomReserveModel } from './ClassroomReserveType';

//获取排课列表
export const getClassroomReserveListApi = (params?: ClassroomReserveModel) => {
	return http.get({
		url: '/api/classroomReserve/list',
		params: params
	});
};

//删除
export const deleteReserveApi = (id: string) => {
	return http.delete({
		url: `/api/classroomReserve/${id}`
	});
};
