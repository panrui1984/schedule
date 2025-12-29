import { getListForStuApi } from '@/api/classes/classes';
import type { SelectProps } from 'ant-design-vue';
import { onMounted, ref } from 'vue';
export default function useClass() {
	//角色数据
	const classList = ref<SelectProps['options']>([]);

	//获取角色数据
	const getRoleList = async () => {
		const res = await getListForStuApi();
		if (res && res.code == 200) {
			classList.value = res.data;
		}
	};
	const classfilter = (input: string, option: any) => {
		return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
	};
	onMounted(() => {
		getRoleList();
	});
	return {
		classfilter,
		classList
	};
}
