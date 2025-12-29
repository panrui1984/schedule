export interface ListPageParm {
	currentPage: number;
	pageSize: number;
	roomId: string;
	courseId: string;
	teacherId: string;
}

export type ScheduleParam = {
	courseId: string;
	roomId: string;
	classId: string; //班级
	teacherId: string;
	startDate: string; //开课时间
	endDate: string; //结课时间
	//reason: string;
	remark: string;
	sequences: number[]; //选课序列
	classNumber: number | undefined; //上课人数
};

//列表检索参数
export type ListParams = {
	roomIdList: string[];
	courseIdList: string[];
	teacherIdList: string[];
	startDate?: string;
	endDate?: string;
	beginTime?: string;
	endTime?: string;
	duration?: number | string;
};

//列表参数
export type ListParm = {
	roomIdList: string;
	courseIdList: string;
	teacherIdList: string;
	startDate?: string;
	endDate?: string;
	beginTime?: string;
	endTime?: string;
	duration?: number | string;
};
//编辑数据类型
export type EditModel = {
	beginTime: string;
	courseColor: string;
	courseId: string;
	courseName: string;
	dateTime: string;
	duration: number | string;
	endTime: string;
	id: string | number;
	roomAddress: string;
	roomId: string;
	roomName: string;
	teacherId: string;
	teacherName: string;
};

//更新参数类型
export type UpdateParam = {
	id: string | number;
	dateTime: string;
	roomId?: string | number;
	teacherId?: string | number;
	courseId?: string | number;
	beginTime: string;
	endTime: string;
	duration?: string | number;
};

//移动日历参数类型
export type RemoveParm = {
	id: string | number;
	dateTime: string;
	beginTime: string;
	endTime: string;
};

export type ScheduleListParamBo = {
	roomIdList?: number[];
	courseIdList?: number[];
	teacherIdList?: number[];
	date?: string;
};
