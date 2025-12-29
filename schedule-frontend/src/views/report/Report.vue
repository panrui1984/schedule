<template>
	<!-- 搜索栏 -->
	<a-form layout="inline">
		<a-row>
			<a-col :span="8">
				<a-form-item label="实验室">
					<a-select
						v-model:value="listParm.roomIds"
						mode="multiple"
						show-search
						placeholder="请选择实验室"
						style="width: 200px"
						:options="roomOptions"
						:filter-option="filterRoomOption"
					></a-select>
				</a-form-item>
			</a-col>
			<a-col :span="8">
				<a-form-item label="实训项目">
					<a-select
						v-model:value="listParm.courseIds"
						mode="multiple"
						show-search
						placeholder="请选择实训项目"
						style="width: 200px"
						:options="courseOptions"
						:filter-option="filterCourseOption"
					></a-select>
				</a-form-item>
			</a-col>
			<a-col :span="8">
				<a-form-item label="教师">
					<a-select
						v-model:value="listParm.teacherIds"
						mode="multiple"
						show-search
						placeholder="请选择教师"
						style="width: 200px"
						:options="teacherOptions"
						:filter-option="filterTeacherOption"
					></a-select>
				</a-form-item>
			</a-col>
		</a-row>
		<a-row style="margin-top: 10px">
			<a-col :span="12">
				<a-form-item label="时间">
					<a-range-picker
						v-model:value="listParm.dateTime"
						format="YYYY-MM-DD"
						value-format="YYYY-MM-DD"
						:placeholder="['开始时间', '结束时间']"
					/>
				</a-form-item>
			</a-col>
			<a-button type="primary" @click="searchBtn" style="margin-right: 15px">
				<template #icon>
					<search-outlined />
				</template>
				搜索
			</a-button>
			<a-button @click="resetBtn" style="margin-right: 15px" type="danger">
				<template #icon> <close-outlined /> </template>重置
			</a-button>
			<a-button @click="exportBtn()" style="margin-left: 15px" type="primary">
				<template #icon>
					<DownloadOutlined />
				</template>
				导出
			</a-button>
		</a-row>
	</a-form>
	<!-- 表格 -->
	<a-table
		style="margin-top: 10px"
		:dataSource="tableList.list"
		:columns="columns"
		:pagination="reportPage"
		:scroll="{ y: tableHeight }"
		bordered
	/>
</template>
<script setup lang="ts">
import useTable from "@/composable/report/useTable";

//表格属性
const {
    reportPage,
    listParm,
    columns,
    tableHeight,
    tableList,
    resetBtn,
    searchBtn,
    exportBtn,
    roomOptions,
    teacherOptions,
    courseOptions,
    filterCourseOption,
    filterRoomOption,
    filterTeacherOption,
} = useTable();
</script>
<style scoped lang="scss"></style>
