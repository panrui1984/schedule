<template>
	<!-- 搜索栏 -->
	<a-form layout="inline" style="margin-bottom: 40px;">
		<a-form-item label="实验室">
			<a-select
				v-model:value="listParams.roomIds"
				show-search
				mode="multiple"
				placeholder="请选择实验室"
				style="width: 200px"
				:options="roomOptions"
				:filter-option="filterRoomOption"
			></a-select>
		</a-form-item>
		<a-form-item label="实训项目">
			<a-select
				v-model:value="listParams.courseIds"
				show-search
				mode="multiple"
				placeholder="请选择实训项目"
				style="width: 200px"
				:options="courseOptions"
				:filter-option="filterCourseOption"
			></a-select>
		</a-form-item>
		<a-form-item label="教师">
			<a-select
				v-model:value="listParams.teacherIds"
				show-search
				mode="multiple"
				placeholder="请选择教师"
				style="width: 100px"
				:options="teacherOptions"
				:filter-option="filterTeacherOption"
			></a-select>
		</a-form-item>
		<a-button style="margin-right: 10px;" @click="searchBtn">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<a-button @click="resetBtn" style="margin-right: 10px;" type="danger">
			<template #icon> <close-outlined /> </template>重置
		</a-button>
		<a-button v-permission="['sys:schedule:add']" style="margin-right: 10px;" @click="courseBtn" type="primary">
			<template #icon>
				<plus-outlined />
			</template>
			约课
		</a-button>
	</a-form>
	<!-- 日历 -->
	<calendar-list ref="refList"></calendar-list>
	<!-- 约课界面 -->
	<add-schedule ref="addCourse" @refreshList="refreshList"></add-schedule>
</template>
<script setup lang="ts">
import CalendarList from './CalendarList.vue';
import { ref, reactive } from 'vue'
import AddSchedule from './AddSchedule.vue';
import useSelect from '@/composable/schedule/useSelect';
import { ClassroomReserveModel } from '@/api/classroomReserve/ClassroomReserveType';
//下拉属性
const { roomOptions, teacherOptions, courseOptions, filterCourseOption, filterRoomOption, filterTeacherOption } = useSelect();
const refList = ref();

//排课界面ref属性
const addCourse = ref<{show:()=>void}>();

//排课按钮
const courseBtn = ()=>{
  addCourse.value?.show()
}
const listParams = reactive<ClassroomReserveModel>({
  roomIds: [],
  teacherIds: [],
  courseIds: [],
  type : 1
  })
 //搜索
 const searchBtn = () => {
  refList.value.getList(listParams)
}
    //重置按钮
    const resetBtn = () => {
      listParams.roomIds = [];
      listParams.courseIds = [];
      listParams.teacherIds = [];
      refList.value.getList (listParams)
    }

//刷新列表
const refreshList = ()=>{
  refList.value.getList(listParams)
}
</script>
<style scoped lang="scss"></style>
