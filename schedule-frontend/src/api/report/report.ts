import http from '@/http';
import { ReportParamBo } from './ReportType';

//查询列表
export const getListAPI = (bo: ReportParamBo) => {
	bo.params = {};
	if (null != bo.dateTime && undefined != bo.dateTime && bo.dateTime.length >= 2) {
		bo.params['startDate'] = bo.dateTime[0];
		bo.params['endDate'] = bo.dateTime[1];
	}
	const parameter = { ...bo };
	parameter.dateTime = [undefined, undefined];
	return http.get({
		url: '/api/report/list',
		params: parameter
	});
};

//导出
export const exportApi = (bo: ReportParamBo) => {
	bo.params = {};
	if (null != bo.dateTime && undefined != bo.dateTime && bo.dateTime.length >= 2) {
		bo.params['startDate'] = bo.dateTime[0];
		bo.params['endDate'] = bo.dateTime[1];
	}
	return http.post({
		url: '/api/report/export',
		responseType: 'blob',
		headers: {
			responseType: 'blob'
		},
		params: bo
	});
};

//获取教室列表
export const getRoomListApi = () => {
	return http.get({
		url: '/api/classroom/getRoomList'
	});
};

//获取教师列表
export const getTeacherApi = () => {
	return http.get({
		url: '/api/teacher/getTeachers'
	});
};

//获取课程列表
export const getCourseListApi = () => {
	return http.get({
		url: '/api/course/getCourseList'
	});
};
