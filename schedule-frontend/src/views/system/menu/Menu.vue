<template>
	<a-button v-permission="['sys:menu:add']" style="margin-bottom: 20px;" @click="addBtn" type="primary">
		<template #icon>
			<plus-outlined />
		</template>
		新增
	</a-button>
	<!-- 表格 -->
	<a-table
		rowKey="menuId"
		:expandIcon="expandIcon"
		:pagination="false"
		v-if="tableList.list && tableList.list.length > 0"
		:defaultExpandAllRows="true"
		bordered
		:scroll="{ y: tableHeight }"
		:columns="columns"
		:data-source="tableList.list"
	>
		<template #bodyCell="{ column, record }">
			<template v-if="column.key === 'type'">
				<a-tag color="red" v-if="record.type == '0'">目录</a-tag>
				<a-tag color="blue" v-if="record.type == '1'">菜单</a-tag>
				<a-tag color="cyan" v-if="record.type == '2'">按钮</a-tag>
			</template>
			<template v-if="column.key === 'icon'">
				<component :is="record.icon"></component>
			</template>
			<template v-if="column.key === 'action'">
				<a-button v-permission="['sys:menu:edit']" @click="editBtn(record)" style="margin-right: 15px;" type="primary">
					<template #icon>
						<edit-outlined />
					</template>
					编辑
				</a-button>
				<a-button v-permission="['sys:menu:detete']" @click="deleteBtn(record)" type="danger">
					<template #icon>
						<delete-outlined />
					</template>
					删除
				</a-button>
			</template>
		</template>
	</a-table>
	<!-- 弹框组件 -->
	<add-menu ref="addRef" @refresh="refresh"></add-menu>
</template>
<script setup lang="ts">
import AddMenu from './AddMenu.vue';
import useMenu from '@/composable/menu/useMenu'
import useTable from '@/composable/menu/useTable';
const { tableHeight, columns, tableList, expandIcon, refresh } = useTable()
const { addBtn, editBtn, deleteBtn, addRef } = useMenu(refresh)
</script>
<style scoped lang="scss">
:deep(.iconClass) {
  padding-right: 10px;
  color: #666;
  font-size: 12px;
}
</style>
