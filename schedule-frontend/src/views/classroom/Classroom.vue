<template>
	<!-- 搜索栏 -->
	<a-form layout="inline">
		<a-form-item>
			<a-input v-model:value="listParm.roomName" placeholder="请输入教室名称"></a-input>
		</a-form-item>
		<a-button @click="searchBtn" style="margin-right: 15px;">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<a-button @click="resetBtn" style="margin-right: 15px;" type="danger">
			<template #icon> <close-outlined /> </template>重置
		</a-button>
		<a-button v-permission="['sys:room:add']" @click="addBtn" style="margin-right: 15px;" type="primary">
			<template #icon> <plus-outlined /> </template>新增
		</a-button>
	</a-form>
	<!-- 表格 -->
	<a-table style="margin-top: 10px;" :dataSource="tableList.list" :columns="columns" :pagination="rolePage" :scroll="{ y: tableHeight }" bordered>
		<!-- 操作按钮 -->
		<template #bodyCell="{ column, record }">
			<template v-if="column.dataIndex ==='isEzviz' ">
				<span>{{ record.isEzviz == 0 ? '否':'是' }}</span>
			</template>

			<template v-if="column.key === 'action'">
				<a-button v-permission="['sys:room:edit']" type="primary" @click.stop="editBtn(record)">
					<template #icon>
						<edit-outlined />
					</template>
					<span>编辑</span>
				</a-button>
				<a-button v-permission="['sys:room:delete']" type="danger" style="margin-left: 10px" @click.stop="deleteBtn(record)">
					<template #icon>
						<close-outlined />
					</template>
					<span>删除</span>
				</a-button>
			</template>
			<template v-else-if="column.key === 'isEzviz'">
				<span>
					<!-- <span v-if="isEzviz === 1">是</span>
            <span v-else-if="isEzviz === 0">否</span> -->
				</span>
			</template>
		</template> </a-table
	>>
	<!-- 弹框组件 -->
	<Add ref="addRef" @save="save"></Add>
</template>
<script setup lang="ts">
import useRoom from '@/composable/classroom/useRoom';
import Add from './Add.vue';
import useTable from '@/composable/classroom/useTable';

//表格属性
const { rolePage, listParm, columns, tableHeight, tableList, resetBtn, searchBtn ,getList} = useTable()
//新增、编辑
const { addBtn, editBtn, deleteBtn, addRef, save } = useRoom(getList)
</script>
<style scoped lang="scss"></style>
