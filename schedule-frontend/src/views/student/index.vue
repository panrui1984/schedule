<template>
	<!-- 搜索栏 -->
	<a-form layout="inline">
		<a-form-item label="姓名">
			<a-input v-model:value="listParm.stuName" placeholder="请输入学生姓名"></a-input>
		</a-form-item>
		<a-form-item label="班级">
			<a-input v-model:value="listParm.className" placeholder="请输入班级名称"></a-input>
		</a-form-item>
		<a-button @click="searchBtn" style="margin-right: 15px">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<a-button @click="resetBtn" style="margin-right: 15px" type="danger">
			<template #icon>
				<close-outlined />
			</template>
			重置
		</a-button>
		<a-button v-permission="['sys:student:add']" @click="addBtn" style="margin-right: 15px" type="primary">
			<template #icon>
				<plus-outlined />
			</template>
			新增
		</a-button>
	</a-form>
	<!-- 表格 -->
	<a-table :scroll="{ y: tableHeight }" style="margin-top: 10px;" bordered :dataSource="tableList.list" :pagination="rolePage" :columns="columns">
		<template #bodyCell="{ column, record }">
			<template v-if="column.key === 'sex'">
				<a-tag color="red" v-if="record.sex == '0'">男</a-tag>
				<a-tag color="blue" v-else>女</a-tag>
			</template>
			<!-- 操作栏 -->
			<template v-if="column.key === 'action'">
				<a-button v-permission="['sys:student:edit']" @click="editBtn(record)" style="margin-right: 15px;" type="primary">
					<template #icon>
						<edit-outlined />
					</template>
					编辑
				</a-button>
				<a-button v-permission="['sys:student:delete']" @click="deleteBtn(record)" type="danger">
					<template #icon>
						<delete-outlined />
					</template>
					删除
				</a-button>
				<a-button v-permission="['sys:student:reset']" style="margin-left: 15px;" @click="resetPasswordBtn(record)" type="waring">
					<template #icon>
						<delete-outlined />
					</template>
					重置密码
				</a-button>
			</template>
		</template>
	</a-table>
	<!-- 新增、编辑 -->
	<add-student ref="addRef" @save="save"></add-student>
</template>
<script setup>
import AddStudent from './AddStudent.vue';
import useStudent from '@/composable/student/useStudent';
import useTable from '@/composable/student/useTable';
//表格
const { listParm, rolePage, columns, tableList, getList, tableHeight, searchBtn, resetBtn } = useTable()
//教师的新增、编辑
const { addBtn, editBtn, deleteBtn, addRef, save,resetPasswordBtn } = useStudent(getList)
</script>
<style scoped lang="scss"></style>
