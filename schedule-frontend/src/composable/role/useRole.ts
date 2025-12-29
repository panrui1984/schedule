import { RoleType } from '@/api/role/RoleType';
import { EditType } from '@/type/BaseEnum';
import { ref } from 'vue';
import { addApi, editApi, deleteApi } from '@/api/role/role';
import { message } from 'ant-design-vue';
import { FuncList } from '@/type/BaseType';
import useInstance from '@/hooks/useInstance';
export default function useRole(getList: FuncList) {
	//分配权限的ref属性
	const assignRef = ref();
	const { global } = useInstance();
	//弹框的ref属性
	const addRef = ref();
	//新增
	const addBtn = () => {
		addRef.value.show(EditType.ADD);
	};
	//编辑
	const editBtn = (row: RoleType) => {
		addRef.value.show(EditType.EDIT, row);
	};
	//删除
	const deleteBtn = async (row: RoleType) => {
		//信息确定
		const confirm = await global.$myconfirm();
		if (confirm) {
			const res = await deleteApi(row.roleId);
			if (res && res.code == 200) {
				message.success(res.msg);
				//刷新表格
				getList();
			}
		}
	};
	//保存
	const save = async (data: RoleType) => {
		console.log(data);
		let res = null;
		if (data.type == EditType.ADD) {
			res = await addApi(data);
		} else {
			res = await editApi(data);
		}
		if (res && res.code == 200) {
			message.success(res.msg);
			//刷新表格
			getList();
		}
	};
	//分配权限按钮
	const assignBtn = (row: RoleType) => {
		assignRef.value.show(row);
	};
	return {
		addBtn,
		editBtn,
		deleteBtn,
		save,
		addRef,
		assignRef,
		assignBtn
	};
}
