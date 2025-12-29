//列表参数
export interface ListParm {
	currentPage: number;
	pageSize: number;
	courseName?: string;
	courseType?: string;
	stuId?: string;
	teacherId?: string;
}
//新增、编辑
export interface CourseModel {
	courseId: string;
	teacherId?: string;
	courseName: string;
	courseType: string;
	courseYear: string;
	courseColor: string;
	type: string;
}
export interface SelectCourse {
	courseId: string;
	stuId: string;
	teacherId?: string;
}
