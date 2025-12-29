//列表查询参数
export type ListParm = {
	pageSize: number;
	currentPage: number;
	stuName: string;
	className: string;
};
//表单类型
export type StudentModel = {
	type: string;
	stuId: string;
	roleId: string;
	classId: string;
	stuName: string;
	sex: string;
	phone: string;
	stuNum: string;
	password: string;
};
