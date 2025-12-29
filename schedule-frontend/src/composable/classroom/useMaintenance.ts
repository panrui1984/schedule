import { MaintenanceModel, RoomModel } from '@/api/classroom/RoomType';
import { EditType } from '@/type/BaseEnum';
import { ref } from 'vue';
import { addMaintenanceApi as addAPI, removeMaintenanceApi as removeAPI } from '@/api/classroom/classroom';
import { getClassroomReserveListApi } from '@/api/classroomReserve';
import { message } from 'ant-design-vue';
import useInstance from '@/hooks/useInstance';
import { ClassroomReserveModel } from '@/api/classroomReserve/ClassroomReserveType';
export default function useRoomMaintenance(param: ClassroomReserveModel) {
	//获取全局属性
	const { global } = useInstance();
	//弹框的属性
	const addRef = ref<{ show: (type: string, row?: MaintenanceModel) => void }>();

	//弹框的属性
	const removeRef = ref<{ show: (type: string, row?: MaintenanceModel) => void }>();

	//新增
	const addBtn = () => {
		addRef.value?.show(EditType.ADD);
	};

	const removeBtn = (row: MaintenanceModel) => {
		removeRef.value?.show(EditType.REMOVE, row);
	};

	const add = async (param: MaintenanceModel) => {
		console.log('父组件接收数据');
		const res = await addAPI(param);

		if (res && res.code == 200) {
			//信息提示
			message.success(res.msg);
			//刷新列表
			getClassroomReserveListApi();
		}
	};

	const remove = async (param: MaintenanceModel) => {
		console.log('去除维护');
		//信息确认
		const confirm = await global.$myconfirm();
		if (confirm) {
			const res = await removeAPI(param);
			if (res && res.code == 200) {
				//信息提示
				message.success(res.msg);
				//刷新表格
				getClassroomReserveListApi();
			}
		}
	};

	//搜索
	const searchBtn = () => {
		getClassroomReserveListApi(param);
	};

	//重置按钮
	const resetBtn = () => {
		console.log('重置');
		//清空搜索表单
		param.roomIds = [];
		getClassroomReserveListApi(param);
	};

	//刷新表格数据
	const refresh = () => {
		console.log('刷新表格数据');
		getClassroomReserveListApi(param);
	};

	return {
		addBtn,
		add,
		removeBtn,
		remove,
		addRef,
		removeRef,
		searchBtn,
		resetBtn,
		refresh
	};
}
