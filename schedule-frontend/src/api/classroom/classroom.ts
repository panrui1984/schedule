import http from '@/http';
import { ListParm, MaintenanceModel, RoomModel } from './RoomType';
//新增
export const addApi = (parm: RoomModel) => {
	return http.post({
		url: '/api/classroom',
		data: parm
	});
};
//列表查询
export const getListApi = (parm: ListParm) => {
	return http.get({
		url: '/api/classroom/list',
		params: parm
	});
};

export const getAll = () => {
	return http.get({
		url: '/api/classroom/all'
	});
};

//编辑
export const editApi = (parm: RoomModel) => {
	return http.put({
		url: '/api/classroom',
		data: parm
	});
};

//删除
export const deleteApi = (roomId: string) => {
	return http.delete({
		url: `/api/classroom/${roomId}`
	});
};

export const addMaintenanceApi = (param: MaintenanceModel) => {
	return http.post({
		url: '/api/classroom/addMaintenance',
		data: param
	});
};

export const removeMaintenanceApi = (param: MaintenanceModel) => {
	return http.post({
		url: '/api/classroom/removeMaintenance',
		data: param
	});
};
