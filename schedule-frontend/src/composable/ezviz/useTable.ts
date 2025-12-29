import { nextTick, onMounted, reactive, ref } from 'vue';
import { getListApi } from '@/api/ezviz/ezvizlog';
export default function useTable() {
	//表格高度
	const tableHeight = ref(0);
	//表格数据
	const tableList = reactive({
		list: []
	});
	//表格的列
	const columns = [
		{
			title: '操作模块',
			key: 'module',
			dataIndex: 'module'
		},
		{
			title: '业务类型',
			key: 'businessType',
			dataIndex: 'businessType'
		},
		{
			title: '请求方法',
			key: 'method',
			dataIndex: 'method'
		},
		{
			title: '设备编码',
			key: 'deviceSerial',
			dataIndex: 'deviceSerial'
		},
		{
			title: '返回编码',
			key: 'code',
			dataIndex: 'code'
		},
		{
			title: '返回日志',
			key: 'message',
			dataIndex: 'message'
		},
        {
			title: '操作时间',
			key: 'operTime',
			dataIndex: 'operTime'
		},
	];

	//分页对象
	const rolePage = reactive({
		current: 1,
		pageSize: 10,
		total: 0,
		showSizeChanger: true,
		pageSizeOptions: ['10', '20', '30', '50'],
		// pageSizeOptions: ['1', '2', '3', '5'],
		showTotal: (total: number) => `共有${total}条数据`,
		//页容量改变时触发
		// onShowSizeChange: (current: number, pageSize: number) => {
		//    console.log('页容量改变时触发')
		// },
		//页数改变时触发
		onChange: (current: number, size: number) => {
			console.log('页数改变时触发');
			listParm.currentPage = current;
			listParm.pageSize = size;
			rolePage.current = current;
			rolePage.pageSize = size;
			getList();
		}
	});
	//列表查询的参数
	const listParm = reactive({
		method: '',
		currentPage: rolePage.current,
		pageSize: rolePage.pageSize
	});

	//表格数据查询
	const getList = async () => {
		const res = await getListApi(listParm);
		if (res && res.code == 200) {
			console.log(res);
			//把后端返回的数据，设置到表格数据里面
			tableList.list = res.data.records;
			//设置分页的总条数
			rolePage.total = res.data.total;
		}
	};

	//搜索
	const searchBtn = () => {
		getList();
	};
	//重置按钮
	const resetBtn = () => {
		listParm.currentPage = 1;
		listParm.method = '';
		getList();
	};


	onMounted(() => {
		//表格数据查询
		getList();
		//计算表格高度
		nextTick(() => {
			tableHeight.value = window.innerHeight - 300;
		});
	});
	return {
		tableHeight,
		tableList,
		rolePage,
		listParm,
		columns,
		resetBtn, searchBtn,
		getList
	};
}
