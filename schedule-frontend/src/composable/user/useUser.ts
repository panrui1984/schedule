import { UserType } from '@/api/user/UserType';
import { EditType } from '@/type/BaseEnum';
import { ref } from 'vue';
import { FuncList } from '@/type/BaseType';
import { deleteUserApi } from '@/api/user/user';
import useInstance from '@/hooks/useInstance';
import { message } from 'ant-design-vue';
export default function useUser(getList: FuncList) {
	const { global } = useInstance();
	//新增弹框属性
	const addRef = ref();
	//新增
	const addBtn = () => {
		addRef.value.show(EditType.ADD);
	};
	//编辑
	const editBtn = (row: UserType) => {
		addRef.value.show(EditType.EDIT, row);
	};
	//删除
	const deleteBtn = async (row: UserType) => {
		const confirm = await global.$myconfirm();
		if (confirm) {
			const res = await deleteUserApi(row.userId);
			if (res && res.code == 200) {
				message.success(res.msg);
				getList();
			}
		}
	};
	//保存
	const save = async (parm: UserType) => {
		console.log(parm);
		// let res = null;
		// if (parm.type == EditType.ADD) {
		//     res = await addUserApi(parm)
		// } else {

		// }
		// if (res && res.code == 200) {
		//     //信息提示
		//     message.success(res.msg)
		//     //刷新表格
		//     getList()
		// }
	};
	return {
		addRef,
		addBtn,
		editBtn,
		deleteBtn,
		save
	};
}
