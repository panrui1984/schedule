import { getRoleListApi } from '@/api/user/user';
import type { SelectProps } from 'ant-design-vue';
import { onMounted, ref } from 'vue';
export default function useRole() {
	//角色数据
	const roleList = ref<SelectProps['options']>([]);

	//获取角色数据
	const getRoleList = async () => {
		const res = await getRoleListApi();
		if (res && res.code == 200) {
			roleList.value = res.data;
		}
	};
	const filter = (input: string, option: any) => {
		return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
	};
	onMounted(() => {
		getRoleList();
	});
	return {
		filter,
		roleList
	};
}
