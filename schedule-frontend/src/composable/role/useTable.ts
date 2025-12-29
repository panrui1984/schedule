import { getListAPi } from '@/api/role/role';
import { RoleListParm } from '@/api/role/RoleType';
import { nextTick, onMounted, reactive, ref } from 'vue';
export default function useTable() {
	//表格的高度
	const talbleHeight = ref(0);
	//表格数据
	const tableList = reactive({
		list: []
	});
	//表格的列
	const columns = [
		{
			title: '角色名称',
			dataIndex: 'roleName',
			key: 'roleName'
		},
		{
			title: '角色描述',
			dataIndex: 'roleDesc',
			key: 'roleDesc'
		},
		{
			title: '操作',
			dataIndex: 'action',
			key: 'action',
			align: 'center',
			width: 340
		}
	];
	//列表参数
	const listParm = reactive<RoleListParm>({
		pageSize: 10,
		currentPage: 1,
		roleName: ''
	});
	//分页对象
	const rolePage = reactive({
		current: 1,
		pageSize: 10,
		total: 0,
		showSizeChanger: true,
		pageSizeOptions: ['10', '20', '30', '50'],
		showTotal: (total: number) => `共有${total}条数据`,
		//页数改变时触发
		onChange: (current: number, size: number) => {
			listParm.currentPage = current;
			listParm.pageSize = size;
			rolePage.current = current;
			rolePage.pageSize = size;
			getList();
		}
	});
	//获取列表
	const getList = async () => {
		const res = await getListAPi(listParm);
		if (res && res.code == 200) {
			//把数据设置到表格的数据
			tableList.list = res.data.records;
		}
	};
	//搜索按钮
	const searchBtn = () => {
		getList();
	};
	//重置按钮
	const resetBtn = () => {
		listParm.currentPage = 1;
		listParm.roleName = '';
		getList();
	};
	onMounted(() => {
		getList();
		nextTick(() => {
			//计算表格的高度
			talbleHeight.value = window.innerHeight - 300;
		});
	});
	return {
		talbleHeight,
		tableList,
		columns,
		listParm,
		rolePage,
		getList,
		searchBtn,
		resetBtn
	};
}
