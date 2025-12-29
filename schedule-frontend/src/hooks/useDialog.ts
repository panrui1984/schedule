import { DialogModel } from '@/type/BaseType';
import { reactive } from 'vue';

export default function useDialog() {
	//弹框属性
	const dialog = reactive<DialogModel>({
		title: '',
		visible: false,
		width: 650,
		height: 250,
		onConfirmTitle: '确认',
		onCloseTitle: '关闭'
		//  okText: '确认',
		//  cancelText: '关闭'
	});

	//确定事件
	const onConfirm = () => {
		dialog.visible = false;
	};

	//取消
	const onClose = () => {
		dialog.visible = false;
	};

	//弹框显示
	const onShow = () => {
		dialog.visible = true;
	};

	return {
		dialog,
		onClose,
		onConfirm,
		onShow
	};
}
