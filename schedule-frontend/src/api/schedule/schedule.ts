import http from '@/http';
import { ScheduleParam, UpdateParam } from './ScheduleType';
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

//获取班级列表
export const getClassListApi = () => {
	return http.get({
		url: '/api/classes/getClassList'
	});
};

//获取指定教室和日期的已排课列表
export const getScheduleListBySelectedDate = (roomId: number, date: string) => {
	return http.get({
		url: '/api/schedule/getScheduleListBySelectedDate',
		params: {
			roomId: roomId,
			date: date
		}
	});
};

//排课
export const saveScheduleApi = (parm: ScheduleParam) => {
	return http.post({
		url: '/api/schedule/saveSchedule',
		data: parm
	});
};

//删除排课
export const removeScheduleApi = (parm: UpdateParam) => {
	return http.post({
		url: '/api/schedule/removeScheduleById',
		data: parm
	});
};
