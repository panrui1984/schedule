import { EditType } from '@/type/BaseEnum';
import { ref } from 'vue';
import { MenuModel } from '@/api/menu/MenuType';
import useInstance from '@/hooks/useInstance';
import { deleteApi } from '@/api/menu/menu';
import { message } from 'ant-design-vue';
import { FuncList } from '@/type/BaseType';
export default function useMenu(refresh: FuncList) {
	const { global } = useInstance();
	//弹框ref属性
	const addRef = ref();
	//新增
	const addBtn = () => {
		addRef.value.show(EditType.ADD);
	};
	//编辑
	const editBtn = (row: MenuModel) => {
		addRef.value.show(EditType.EDIT, row);
	};
	//删除
	const deleteBtn = async (row: MenuModel) => {
		//信息确认
		const confirm = await global.$myconfirm();
		if (confirm) {
			const res = await deleteApi(row.menuId);
			if (res && res.code == 200) {
				message.success(res.msg);
				//刷新列表
				refresh();
			}
		}
	};
	return {
		addBtn,
		editBtn,
		deleteBtn,
		addRef
	};
}
