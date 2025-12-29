import http from '@/http/index';
import { ListParm, CourseModel, SelectCourse } from './BaseCourse';
//新增课程
export const addCourseApi = (parm: CourseModel) => {
	return http.post({
		url: '/api/course',
		data: parm
	});
};
//列表查询
export const getListApi = (parm: ListParm) => {
	return http.get({
		url: '/api/course/list',
		params: parm
	});
};
//编辑
export const editCourseApi = (parm: CourseModel) => {
	return http.put({
		url: '/api/course',
		data: parm
	});
};
//删除
export const deleteCourseApi = (courseId: string) => {
	return http.delete({
		url: `/api/course/${courseId}`
	});
};
//选课
export const selectCourseApi = (select: SelectCourse) => {
	return http.post({
		url: '/api/course/selectCourse',
		data: select
	});
};
//退课
export const returnCourseApi = (select: SelectCourse) => {
	return http.post({
		url: '/api/course/returnCourse',
		data: select
	});
};
//选课列表查询
export const getSelectCourseListApi = (parm: ListParm) => {
	return http.get({
		url: '/api/course/getSelectCourse',
		params: parm
	});
};
//学生已选选课列表查询
export const getgetStuCourseApi = (parm: ListParm) => {
	return http.get({
		url: '/api/course/getStuCourse',
		params: parm
	});
};
//教师课程列表
export const getTeacherCourseApi = (parm: ListParm) => {
	return http.get({
		url: '/api/course/getTeacherCourse',
		params: parm
	});
};
