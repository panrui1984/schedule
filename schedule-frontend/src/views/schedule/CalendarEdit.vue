<template>
	<sys-dialog
		:title="dialog.title"
		:width="dialog.width"
		:height="dialog.height"
		:visible="dialog.visible"
		@onClose="onClose"
		@onConfirm="onConfirm"
		@confirm-title="dialog.onConfirmTitle"
		@close-title="dialog.onCloseTitle"
	>
		<template #content>
			<a-form style="margin-right: 30px;">
				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="日期">
							<a-date-picker style="width: 100%;" @change="openChange" format="YYYY-MM-DD" v-model:value="dataTime" />
						</a-form-item>
					</a-col>
					<a-col :span="18">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="实验室">
							<a-select
								v-model:value="addModel.roomName"
								show-search
								placeholder="请选择教室"
								style="width: 100%;"
								:options="roomOptions"
								:filter-option="filterRoomOption"
							></a-select>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="实训项目">
							<a-input v-model:value="addModel.courseName" />
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="教师">
							<a-input v-model:value="addModel.teacherName" />
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="上课时间">
							<a-input v-model:value="addModel.beginTime" />
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="下课时间">
							<a-input v-model:value="addModel.endTime" />
						</a-form-item>
					</a-col>
				</a-row>

				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="上课人数">
							<a-input v-model:value="addModel.classNumber" />
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="班级">
							<a-input v-model:value="addModel.className" />
						</a-form-item>
					</a-col>
				</a-row>

				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="备注">
							<a-textarea v-model:value="addModel.remark" :auto-size="{ minRows: 4, maxRows: 6 }" />
						</a-form-item>
					</a-col>
				</a-row>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { ref, reactive } from 'vue'
import SysDialog from '@/components/SysDialog.vue';
import useDialog from '@/hooks/useDialog';
import { message } from 'ant-design-vue';
import { EditModel } from '@/api/schedule/ScheduleType';
import { EditType, Title } from '@/type/BaseEnum';
import useSelect from '@/composable/schedule/useSelect';
import dayjs, { Dayjs } from 'dayjs'
import useInstance from '@/hooks/useInstance';
import {deleteReserveApi as remove} from '@/api/classroomReserve';
const { global } = useInstance()
//下拉属性
const { roomOptions, teacherOptions, courseOptions, filterCourseOption, filterRoomOption, filterTeacherOption } = useSelect()

//弹框属性
const { dialog, onClose, onShow } = useDialog()

//表单绑定属性
const addModel = reactive({
    id: '',
    dateTime: '',
    roomName: '',
    teacherName: '',
    courseName: '',
    beginTime: '',
    endTime: '',
    duration: 0,
    reason: '',
    className: '',
    classNumber: 0,
    remark: '',
})

//日期数据
const dataTime = ref<Dayjs>()
//日期选择事件
const openChange = (date: string, dateString: string) => {
    console.log(date)
    console.log(dateString)
    addModel.dateTime = dateString
}



//设置编辑或新增
const editType = ref('');
//定义方法给外部组件使用
const editCalender = (type: string, data?: EditModel) => {
  console.log("编辑 " + data);
    dataTime.value = undefined;

    dialog.width = 700;
    if (type == EditType.ADD) {
        dialog.title = Title.ADD
    } else if (type == EditType.EDIT){
        dialog.title = "约课详情"
        //编辑数据回显
        global.$objCoppy(data, addModel)
        dataTime.value = dayjs(data?.dateTime, 'YYYY-MM-DD')

    }else if( type == EditType.REMOVE){
		dialog.title = '约课详情';
		dataTime.value = dayjs(data?.dateTime, 'YYYY-MM-DD');
		global.$objCoppy(data, addModel);
		dialog.height = 300;
		dialog.width = 900;
		dialog.onConfirmTitle = "取消预约"
		dialog.onCloseTitle ="关闭"
	}
    editType.value = type;
    onShow()
}

//弹框确定
const onConfirm = async () => {
    console.log(dataTime.value)
    if (editType.value == EditType.REMOVE) {

		const confirm = await global.$myconfirm("您确认取消选定的实验室预约吗");
			if (confirm) {
			let res = await remove(addModel.id);
			if (res && res.code == 200) {
				//信息提示
				message.success(res.msg);
				//刷新表格
				emit('upSuccess')
                onClose()
			}
			}
		}else if( editType.value == EditType.EDIT){
		}
}

//暴露给外部使用
defineExpose({
    editCalender
})

//注册事件
const emit = defineEmits(['upSuccess'])
</script>
<style scoped lang="scss"></style>
