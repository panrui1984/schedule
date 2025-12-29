import { CourseModel, ListParm } from '@/api/course/BaseCourse';
import { selectCourseApi, returnCourseApi } from '@/api/course/course';
import { message } from 'ant-design-vue';
import { FuncList } from '@/type/BaseType';
import useInstance from '@/hooks/useInstance';
import { getUserId } from '@/utils/auth';
export default function useStudentCourse(getList: FuncList, listParm: ListParm) {
	//获取全局global
	const { global } = useInstance();

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
	//退课
	const returnBtn = async (row: CourseModel) => {
		//信息确定
		const confirm = await global.$myconfirm('确定退选该课程吗?');
		if (confirm) {
			const res = await returnCourseApi({
				courseId: row.courseId,
				teacherId: row.teacherId,
				stuId: getUserId() || ''
			});
			if (res && res.code == 200) {
				message.success(res.msg);
				getList();
			}
		}
	};
	return {
		searchBtn,
		resetBtn,
		returnBtn
	};
}
