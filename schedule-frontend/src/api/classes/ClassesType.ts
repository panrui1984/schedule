//列表查询参数
export type ListParm = {
	pageSize: number;
	currentPage: number;
	className: string;
};
//表单类型
export type ClassesModel = {
	type: string;
	classId: string;
	className: string;
	grade: string;
	classNumber: number | undefined;
	dadui: string;
	calassDesc: string;
};
