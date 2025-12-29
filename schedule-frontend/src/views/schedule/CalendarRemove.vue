<template>
	<sys-dialog :title="dialog.title" :width="dialog.width" :height="dialog.height" :visible="dialog.visible" @onClose="onClose" @onConfirm="onClose">
		<template #content>
			<a-form style="margin-right: 30px;">
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.dateTime" :labelCol="{ style: 'width:80px;' }" label="日期">
							<a-date-picker style="width: 100%;" @change="openChange" format="YYYY-MM-DD" v-model:value="dataTime" />
						</a-form-item>
					</a-col>
					<a-col :span="18">
						<a-form-item v-bind="validateInfos.roomId" :labelCol="{ style: 'width:80px;' }" label="教室">
							<a-select
								v-model:value="addModel.roomId"
								style="width: 100%;"
								:options="roomOptions"
								:filter-option="filterRoomOption"
							></a-select>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.courseId" :labelCol="{ style: 'width:80px;' }" label="课程">
							<a-select
								style="width: 100%;"
								v-model:value="addModel.courseId"
								show-search
								placeholder="请选择课程"
								:options="courseOptions"
								:filter-option="filterCourseOption"
							></a-select>
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.teacherId" :labelCol="{ style: 'width:80px;' }" label="教师">
							<a-select
								v-model:value="addModel.teacherId"
								show-search
								placeholder="请选择教师"
								style="width: 100%;"
								:options="teacherOptions"
								:filter-option="filterTeacherOption"
							></a-select>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.beginTime" :labelCol="{ style: 'width:80px;' }" label="上课时间">
							<a-time-picker
								style="width: 100%;"
								:minuteStep="15"
								@change="beginTimeChange"
								v-model:value="beginTime1"
								format="HH:mm"
							/>
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="课程时长">
							<a-input-number style="width: 100%;" v-model:value="inputNumber"></a-input-number>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="下课时间">
							<a-time-picker style="width: 100%;" v-model:value="endTime1" format="HH:mm" />
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.classNumber" :labelCol="{ style: 'width:80px;' }" label="上课人数">
							<a-input v-model:value="addModel.classNumber" />
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.reason" :labelCol="{ style: 'width:80px;' }" label="实验名称">
							<a-input v-model:value="addModel.reason" />
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.remark" :labelCol="{ style: 'width:80px;' }" label="备注">
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
import { Modal } from 'ant-design-vue';
import { EditModel } from '@/api/schedule/ScheduleType';
import { EditType, Title } from '@/type/BaseEnum';
import useSelect from '@/composable/schedule/useSelect';
import dayjs, { Dayjs } from 'dayjs'
import useInstance from '@/hooks/useInstance';
import useForm from 'ant-design-vue/es/form/useForm';
import { removeScheduleApi } from '@/api/schedule/schedule'
const { global } = useInstance()
//下拉属性
const { roomOptions, teacherOptions, courseOptions, filterCourseOption, filterRoomOption, filterTeacherOption } = useSelect()

//弹框属性
const { dialog, onClose, onShow, } = useDialog()

//表单绑定属性
const addModel = reactive({
    id: '',
    dateTime: '',
    roomId: '',
    teacherId: '',
    courseId: '',
    beginTime: '',
    endTime: '',
    duration: 0,
    classNumber: 0,
    reason: '',
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

//上课时间
const beginTime1 = ref<Dayjs>()
//上课时间点击事件
const beginTimeChange = () => {
    getEndTime()
}
//课程时长
const inputNumber = ref<number>(45)

//下课时间
const endTime1 = ref<Dayjs>()

//设置时间
const getEndTime = () => {
    //上课的时间，加上45分钟= 下课时间
    endTime1.value = dayjs(beginTime1.value, "HH:mm").add(inputNumber.value, 'minute')
    addModel.beginTime = dayjs(beginTime1.value, "HH:mm").format("HH:mm")
    addModel.endTime = dayjs(endTime1.value, "HH:mm").format("HH:mm")
    addModel.duration = inputNumber.value
}
//表单验证

//表单验证规则
const rules = reactive({
    dateTime: [{
        required: true,
        message: '请选择日期',
        trigger: 'change'
    }],
    roomId: [{
        required: true,
        message: '请选择教室',
        trigger: 'change'
    }],
    teacherId: [{
        required: true,
        message: '请选择教师',
        trigger: 'change'
    }],
    courseId: [{
        required: true,
        message: '请选择课程',
        trigger: 'change'
    }],
    beginTime: [{
        required: true,
        message: '请选择上课时间',
        trigger: 'change'
    }]
})
const { resetFields, validate, validateInfos } = useForm(addModel, rules);
//设置编辑或新增
const editType = ref('');

//定义方法给外部组件使用
const removeCalender = (type: string, data?: EditModel) => {
    //清空表单
    resetFields()
    dataTime.value = undefined;
    beginTime1.value = undefined;
    endTime1.value = undefined;
    dialog.width = 700;
    if (type == EditType.ADD) {
        dialog.title = Title.ADD
    } else if (type == EditType.EDIT){
        dialog.title = Title.EDIT
        //编辑数据回显
        global.$objCoppy(data, addModel)
        dataTime.value = dayjs(data?.dateTime, 'YYYY-MM-DD')
        beginTime1.value = dayjs(data?.beginTime, 'HH:mm')
        endTime1.value = dayjs(data?.endTime, 'HH:mm')
    }else if (type == EditType.REMOVE){
        dialog.title = Title.EDIT
        //编辑数据回显
        global.$objCoppy(data, addModel)
        dataTime.value = dayjs(data?.dateTime, 'YYYY-MM-DD')
        beginTime1.value = dayjs(data?.beginTime, 'HH:mm')
        endTime1.value = dayjs(data?.endTime, 'HH:mm')
        dialog.onCloseTitle= '关闭';
        dialog.onConfirmTitle = '取消预约';
    }
    editType.value = type;
    onShow()
}
//暴露给外部使用
defineExpose({
    removeCalender
})

//注册事件
const emit = defineEmits(['upSuccess'])

//弹框确定
const onConfirm = () => {
    console.log(dataTime.value)
    validate().then( async() => {
        let res:any = null;
        if (editType.value == EditType.REMOVE) {
            console.log("删除");
            Modal.confirm({
                title: '系统提示',
                okText: '确定',
                content: '确实取消此项预约吗?',
                okType: 'danger',
                cancelText: '取消',
                onOk:async () => {
                    res =  await removeScheduleApi(addModel);
                    console.log(res)
                    if (res && res?.code == 200) {
                        console.log("取消成功");
                        //刷新表格数据
                        emit('upSuccess')
                        onClose()
                    }
                },
                onCancel() {

                }
            })

        } else {
           // res = await updateCalendarApi(addModel)
         //  res = await removeScheduleApi(addModel);
        }


    })
}
</script>
<style scoped lang="scss"></style>
