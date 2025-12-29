export type ReportParamBo = {
	pageSize: number; //分页
	currentPage: number;
	roomIds: string[]; //教室序列
	teacherIds: string[]; //老师序列
	courseIds: string[]; //课程课程
	dateTime: [Date | undefined, Date | undefined]; //指定日期
	params?: any;
};
