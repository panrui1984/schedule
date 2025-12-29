<template>
	<!-- 搜索栏 -->
	<a-form layout="inline">
		<a-form-item label="请求方法">
			<a-select v-model:value="listParm.method" placeholder="请选择请求方法" :options="options" style="width:130px;"></a-select>
		</a-form-item>
		<a-button @click="searchBtn" style="margin-right: 20px;">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<a-button @click="resetBtn" type="danger" style="margin-right: 20px;">
			<template #icon>
				<close-outlined />
			</template>
			重置
		</a-button>
	</a-form>
	<!-- 表格列表 -->
	<a-table :scroll="{ y: tableHeight }" style="margin-top: 10px;" bordered :dataSource="tableList.list" :pagination="rolePage" :columns="columns">
		
	</a-table>
</template>
<script setup lang="ts">
import type { SelectProps } from 'ant-design-vue';
import { ref, } from 'vue';
import useTable from '@/composable/ezviz/useTable';
//表格相关的操作
const { rolePage, tableHeight, tableList, listParm, columns, resetBtn, searchBtn, getList } = useTable()

//下拉选择的数据
const options = ref<SelectProps['options']>([
  { value: '获取临时密码列表', label: '获取临时密码列表' },
  { value: '申请临时密码', label: '申请临时密码' },
  { value: '删除临时密码', label: '删除临时密码' }
]);
</script>
<style scoped lang="scss"></style>
