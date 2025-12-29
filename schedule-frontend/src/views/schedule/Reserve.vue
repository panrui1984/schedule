<template>
	<!-- 搜索栏 -->
	<a-form layout="inline" style="margin-bottom: 40px;">
		<a-form-item label="实验室">
			<a-select
				v-model:value="listParm.roomId"
				show-search
				placeholder="请选择实验室"
				style="width: 200px"
				:options="roomOptions"
				:filter-option="filterRoomOption"
			></a-select>
		</a-form-item>
		<a-form-item label="实训项目">
			<a-select
				v-model:value="listParm.courseId"
				show-search
				placeholder="请选择实训项目"
				style="width: 200px"
				:options="courseOptions"
				:filter-option="filterCourseOption"
			></a-select>
		</a-form-item>
		<a-form-item label="教师">
			<a-select
				v-model:value="listParm.teacherId"
				show-search
				placeholder="请选择教师"
				style="width: 200px"
				:options="teacherOptions"
				:filter-option="filterTeacherOption"
			></a-select>
		</a-form-item>
		<a-button style="margin-right: 15px;">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<!-- <n-button style="margin-right: 15px;">
        <template #icon>
          <search-outlined />
        </template>
        搜索
      </n-button> -->
		<!-- <n-button v-permission="['sys:schedule:add']" @click="courseBtn" type="primary">
        <template #icon>
          <plus-outlined />
        </template>
        排课
      </n-button> -->
		<a-button v-permission="['sys:schedule:add']" @click="courseBtn" type="primary">
			<template #icon>
				<plus-outlined />
			</template>
			约课
		</a-button>
	</a-form>
	<!-- 日历 -->
	<calendar-list ref="refList"></calendar-list>
	<!-- 排课界面 -->
	<!-- <add-schedule ref="addCourse" @refreshList="refreshList"></add-schedule> -->
	<add-schedule ref="addCourse" @refreshList="refreshList" />
</template>
<script setup lang="ts">
import CalendarList from './CalendarList.vue';
import { ref, reactive } from 'vue'
import AddSchedule from './AddSchedule.vue';
import useSelect from '@/composable/schedule/useSelect';

//下拉属性
const { roomOptions, teacherOptions, courseOptions, filterCourseOption, filterRoomOption, filterTeacherOption } = useSelect()
const refList = ref();
//搜索参数
const listParm = reactive({
  courseId: '',
  roomId: '',
  teacherId: ''
})
//排课界面ref属性
const addCourse = ref<{show:()=>void}>();

//排课按钮
const courseBtn = ()=>{
  addCourse.value?.show()
}
//刷新列表
const refreshList = ()=>{
  refList.value.getScheduleList()
}
</script>
<style scoped lang="scss"></style>
