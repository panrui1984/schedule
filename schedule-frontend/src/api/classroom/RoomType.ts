export type RoomModel = {
	type: string;
	roomId: string;
	roomName: string;
	roomAddress: string;
	code: string;
	isEzviz: number;
	deviceSerial: string;
	category: string;
	unit: string;
	unitName: string;
	size: string;
};

export type ListParm = {
	pageSize: number;
	currentPage: number;
	roomName: string;
};

export type MaintenanceModel = {
	id?: number | null;
	roomId?: number;
	startDate?: string; //开课时间
	endDate?: string; //结课时间
	sequences?: number[]; //选课序列
	remark?: string;
};
