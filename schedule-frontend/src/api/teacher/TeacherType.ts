//列表查询参数
export type ListParm = {
	pageSize: number;
	currentPage: number;
	teacherName: string;
};

//表单类型
export type TeacherModel = {
	type: string;
	teacherId: string;
	roleId: string;
	teacherName: string;
	teacherNum: string;
	teacherDesc: string;
	password: string;
	phone: string;
	email: string;
};
