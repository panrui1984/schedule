import type { SelectProps } from 'ant-design-vue';
import { onMounted, ref } from 'vue';

import { getTeacherApi, getRoomListApi, getCourseListApi, getClassListApi } from '@/api/schedule/schedule';
export default function useSelect() {
	//教室列表数据
	const roomOptions = ref<SelectProps['options']>([]);
	//教师列表数据
	const teacherOptions = ref<SelectProps['options']>([]);
	//课程列表数据
	const courseOptions = ref<SelectProps['options']>([]);
	//班级列表数据
	const classOptions = ref<SelectProps['options']>([]);

	//获取教室列表
	const teacher = async () => {
		let res = await getTeacherApi();
		if (res && res.code == 200) {
			teacherOptions.value = res.data;
		}
	};
	//获取教师列表
	const room = async () => {
		let res = await getRoomListApi();
		if (res && res.code == 200) {
			roomOptions.value = res.data;
		}
	};
	//获取课程列表
	const course = async () => {
		let res = await getCourseListApi();
		if (res && res.code == 200) {
			courseOptions.value = res.data;
		}
	};

	//获取班级列表
	const clasz = async () => {
		let res = await getClassListApi();
		if (res && res.code == 200) {
			classOptions.value = res.data;
		}
	};

	const filterTeacherOption = (input: string, option: any) => {
		return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
	};
	const filterRoomOption = (input: string, option: any) => {
		return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
	};
	const filterCourseOption = (input: string, option: any) => {
		return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
	};
	const filterClassOption = (input: string, option: any) => {
		return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
	};

	onMounted(() => {
		teacher();
		course();
		room();
		clasz();
	});

	return {
		roomOptions,
		teacherOptions,
		courseOptions,
		filterTeacherOption,
		filterRoomOption,
		filterCourseOption,
		filterClassOption,
		classOptions
	};
}
