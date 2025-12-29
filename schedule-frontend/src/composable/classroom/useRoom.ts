import { MaintenanceModel, RoomModel } from '@/api/classroom/RoomType';
import { EditType } from '@/type/BaseEnum';
import { ref } from 'vue';
import { addApi, deleteApi, editApi, addMaintenanceApi } from '@/api/classroom/classroom';
import { message } from 'ant-design-vue';
import { FuncList } from '@/type/BaseType';
import useInstance from '@/hooks/useInstance';
export default function useRoom(getList: FuncList) {
	//获取全局属性
	const { global } = useInstance();
	//弹框的属性
	const addRef = ref<{ show: (type: string, row?: RoomModel) => void }>();
	//新增
	const addBtn = () => {
		addRef.value?.show(EditType.ADD);
	};
	//编辑
	const editBtn = (row: RoomModel) => {
		addRef.value?.show(EditType.EDIT, row);
	};
	//删除
	const deleteBtn = async (row: RoomModel) => {
		//信息确定
		const confirm = await global.$myconfirm();
		if (confirm) {
			const res = await deleteApi(row.roomId);
			if (res && res.code == 200) {
				//信息提示
				message.success(res.msg);
				//刷新列表
				getList();
			}
		}
	};
	//保存
	const save = async (parm: RoomModel) => {
		console.log(parm);
		let res = null;
		if (parm.type === EditType.ADD) {
			res = await addApi(parm);
		} else {
			res = await editApi(parm);
		}
		if (res && res.code == 200) {
			//信息提示
			message.success(res.msg);
			//刷新列表
			getList();
		}
	};

	const addMaintenance = async (param: MaintenanceModel) => {
		console.log('新增维护');
		const res = await addMaintenanceApi(param);

		if (res && res.code == 200) {
			//信息提示
			message.success(res.msg);
			//刷新列表
			//getList();
		}
	};

	return {
		addBtn,
		editBtn,
		deleteBtn,
		save,
		addMaintenance,
		addRef
	};
}
