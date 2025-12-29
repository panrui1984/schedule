<template>
	<!-- 搜索栏 -->
	<a-form layout="inline">
		<a-form-item label="班级名称">
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
		<a-button v-permission="['sys:classes:add']" @click="addBtn" style="margin-right: 15px" type="primary">
			<template #icon>
				<plus-outlined />
			</template>
			新增
		</a-button>
	</a-form>
	<!-- 表格 -->
	<a-table :scroll="{ y: tableHeight }" style="margin-top: 10px" bordered :dataSource="tableList.list" :pagination="rolePage" :columns="columns">
		<template #bodyCell="{ column, record }">
			<!-- 操作栏 -->
			<template v-if="column.key === 'action'">
				<a-button v-permission="['sys:classes:edit']" @click="editBtn(record)" style="margin-right: 15px" type="primary">
					<template #icon>
						<edit-outlined />
					</template>
					编辑
				</a-button>
				<a-button v-permission="['sys:classes:delete']" @click="deleteBtn(record)" type="danger">
					<template #icon>
						<delete-outlined />
					</template>
					删除
				</a-button>
			</template>
		</template>
	</a-table>
	<!-- 新增、编辑 -->
	<add-class ref="addRef" @save="save"></add-class>
</template>
<script setup>
import AddClass from "./AddClass.vue";
import useClasses from "@/composable/classes/useClasses";
import useTable from "@/composable/classes/useTable";

//表格
const {
  listParm,
  rolePage,
  columns,
  tableList,
  getList,
  tableHeight,
  searchBtn,
  resetBtn,
} = useTable();
//教师的新增、编辑
const { addBtn, editBtn, deleteBtn, addRef, save } = useClasses(getList);
</script>
<style scoped lang="scss"></style>
