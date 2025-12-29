//对话框数据类型
export type DialogModel = {
	title: string;
	visible: boolean;
	height: number;
	width: number;
	onConfirmTitle: string;
	onCloseTitle: string;
};

//通用函数类型
export type FuncList = () => any;
