<template>
	<!-- 搜索栏 -->
	<a-form layout="inline" style="margin-bottom: 25px;">
		<a-form-item>
			<a-input v-model:value="listParm.name" placeholder="请输入姓名"></a-input>
		</a-form-item>
		<a-form-item>
			<a-input v-model:value="listParm.phone" placeholder="请输入电话"></a-input>
		</a-form-item>
		<a-button @click="searchBtn" style="margin-right: 15px;">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<a-button @click="resetBtn" style="margin-right: 15px;" type="danger">
			<template #icon>
				<close-outlined />
			</template>
			重置
		</a-button>
		<a-button v-permission="['sys:user:add']" type="primary" @click="addBtn">
			<template #icon>
				<plus-outlined />
			</template>
			新增
		</a-button>
	</a-form>
	<!-- 表格 -->

	<a-table :scroll="{ y: tableHeight }" :pagination="page" :dataSource="tableList.list" :columns="columns" bordered>
		<template #bodyCell="{ column, record }">
			<template v-if="column.key === 'sex'">
				<a-tag color="red" v-if="record.sex == '0'">男</a-tag>
				<a-tag color="blue" v-else>女</a-tag>
			</template>
			<template v-if="column.key === 'action'">
				<a-button v-permission="['sys:user:edit']" @click="editBtn(record)" style="margin-right: 15px;" type="primary">
					<template #icon>
						<edit-outlined />
					</template>
					编辑
				</a-button>
				<a-button v-permission="['sys:user:delete']" @click="deleteBtn(record)" type="danger">
					<template #icon>
						<delete-outlined />
					</template>
					删除
				</a-button>
			</template>
		</template>
	</a-table>

	<!-- 弹框组件 -->
	<add-user ref="addRef" @refresh="refresh"></add-user>
</template>
<script setup lang="ts">
import AddUser from './AddUser.vue';
import useUser from '@/composable/user/useUser';
import useTable from '@/composable/user/useTable';
//表格
const { tableHeight, tableList, listParm, getList, page, columns,searchBtn,resetBtn,refresh } = useTable()

const { addBtn, editBtn, deleteBtn, save, addRef } = useUser(getList)
</script>
<style scoped lang="scss"></style>
