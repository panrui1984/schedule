import type { SelectProps } from 'ant-design-vue';
import { ReportParamBo } from '@/api/report/ReportType';
import { nextTick, onMounted, reactive, ref } from 'vue';
import { getListAPI, getTeacherApi, getRoomListApi, getCourseListApi, exportApi } from '@/api/report/report';
export default function useTable() {
	//定义表格高度
	const tableHeight = ref(0);

	//教室列表数据
	const roomOptions = ref<SelectProps['options']>([]);
	//教师列表数据
	const teacherOptions = ref<SelectProps['options']>([]);
	//课程列表数据
	const courseOptions = ref<SelectProps['options']>([]);

	//获取教室列表
	const teacher = async () => {
		const res = await getTeacherApi();
		if (res && res.code == 200) {
			teacherOptions.value = res.data;
		}
	};
	//获取教师列表
	const room = async () => {
		const res = await getRoomListApi();
		if (res && res.code == 200) {
			roomOptions.value = res.data;
		}
	};
	//获取课程列表
	const course = async () => {
		const res = await getCourseListApi();
		if (res && res.code == 200) {
			courseOptions.value = res.data;
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

	//定义表格的数据
	const tableList = reactive({
		list: []
	});

	//表格列
	const columns = [
		{
			title: '实验室',
			key: 'roomName',
			dataIndex: 'roomName'
		},
		{
			title: '实训项目',
			key: 'courseName',
			dataIndex: 'courseName'
		},

		{
			title: '老师',
			key: 'teacherName',
			dataIndex: 'teacherName'
		},
		{
			title: '班级',
			key: 'className',
			dataIndex: 'className'
		},
		{
			title: '日期',
			key: 'dateTime',
			dataIndex: 'dateTime'
		},
		{
			title: '开始时间',
			key: 'beginTime',
			dataIndex: 'beginTime'
		},
		{
			title: '结束时间',
			key: 'endTime',
			dataIndex: 'endTime'
		},
		{
			title: '课时数',
			key: 'sequenceTotal',
			dataIndex: 'sequenceTotal'
		},
		{
			title: '约课人',
			key: 'createBy',
			dataIndex: 'createBy'
		},
		{
			title: '约课时间',
			key: 'createTime',
			dataIndex: 'createTime'
		}
	];

	//表格分页
	const reportPage = reactive({
		total: 0,
		current: 1,
		showSizeChanger: true,
		pageSize: 10,
		name: '', //搜索关键字
		pageSizeOptions: ['10', '20', '30', '40', '50'],
		showTotal: (total: number) => `共有 ${total} 条数据`,
		// 页数改变时触发
		onChange: (current: number, size: number) => {
			reportPage.current = current;
			reportPage.pageSize = size;
			listParm.currentPage = current;
			listParm.pageSize = size;
			getList();
		}
	});

	//列表查询的参数
	const listParm = reactive<ReportParamBo>({
		roomIds: [], //选课序列
		teacherIds: [], //老师序列
		courseIds: [], //课程
		dateTime: [undefined, undefined],
		currentPage: 1,
		pageSize: 20
	});

	//列表查询
	const getList = async () => {
		const res = await getListAPI(listParm);
		if (res && res.code == 200) {
			//设置表格数据
			tableList.list = res.data.records;
			//设置分页总条数
			reportPage.total = res.data.total;
		}
	};

	//搜索
	const searchBtn = () => {
		console.log(listParm);
		getList();
	};
	//重置按钮
	const resetBtn = () => {
		listParm.currentPage = 1;
		(listParm.pageSize = 20), (listParm.roomIds = []);
		listParm.teacherIds = [];
		listParm.courseIds = [];
		listParm.dateTime = [undefined, undefined];
		getList();
	};

	const exportBtn = async () => {
		const res: any = await exportApi(listParm);
		const url = window.URL.createObjectURL(new Blob([res]));
		const link = document.createElement('a');
		link.href = url;
		link.setAttribute('download', '导出.xlsx');
		document.body.appendChild(link);
		link.click();
	};

	onMounted(() => {
		getList();
		teacher();
		course();
		room();
		nextTick(() => {
			//计算表格高度
			tableHeight.value = window.innerHeight - 300;
		});
	});
	return {
		tableHeight,
		columns,
		reportPage,
		listParm,
		tableList,
		searchBtn,
		resetBtn,
		getList,
		roomOptions,
		teacherOptions,
		courseOptions,
		filterTeacherOption,
		filterRoomOption,
		filterCourseOption,
		exportBtn
	};
}
