import { CourseModel, ListParm } from '@/api/course/BaseCourse';
import { EditType } from '@/type/BaseEnum';
import { ref } from 'vue';
import { addCourseApi, editCourseApi, deleteCourseApi } from '@/api/course/course';
import { message } from 'ant-design-vue';
import { FuncList } from '@/type/BaseType';
import useInstance from '@/hooks/useInstance';
import { getUserId } from '@/utils/auth';
export default function useCourse(getList: FuncList, listParm: ListParm) {
	//获取全局global
	const { global } = useInstance();
	//新增弹框的ref属性
	const addRef = ref<{ show: (type: string, row?: CourseModel) => void }>();
	//新增
	const addBtn = () => {
		addRef.value?.show(EditType.ADD);
	};
	//编辑
	const editBtn = (row: CourseModel) => {
		addRef.value?.show(EditType.EDIT, row);
	};
	//删除
	const deleteBtn = async (row: CourseModel) => {
		//信息确定
		const confirm = await global.$myconfirm();
		if (confirm) {
			const res = await deleteCourseApi(row.courseId);
			if (res && res.code == 200) {
				//信息提示
				message.success(res.msg);
				//刷新表格
				getList();
			}
		}
	};
	//保存
	const save = async (item: CourseModel) => {
		let res = null;
		if (item.type == EditType.ADD) {
			res = await addCourseApi(item);
		} else {
			res = await editCourseApi(item);
		}
		if (res && res.code == 200) {
			//信息提示
			message.success(res.msg);
			//刷新列表数据
			getList();
		}
	};

	//搜索
	const searchBtn = () => {
		getList();
	};

	//重置按钮
	const resetBtn = () => {
		//清空搜索表单
		listParm.courseName = '';
		listParm.courseType = '';
		listParm.currentPage = 1;
		getList();
	};
	return {
		addBtn,
		editBtn,
		deleteBtn,
		save,
		addRef,
		searchBtn,
		resetBtn
	};
}
