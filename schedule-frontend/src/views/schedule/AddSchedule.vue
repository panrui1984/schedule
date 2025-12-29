<template>
	<sys-dialog
		:title="dialog.title"
		:height="dialog.height"
		:width="dialog.width"
		:visible="dialog.visible"
		:on-confirm-title="dialog.onConfirmTitle"
		:on-close-title="dialog.onCloseTitle"
		@onClose="onClose"
		@onConfirm="onConfirm"
	>
		<template #content>
			<a-form>
				<a-row>
					<a-col :span="8">
						<a-form-item v-bind="validateInfos.roomId" :labelCol="{ style: 'width:80px;' }" label="实验室">
							<a-select
								v-model:value="addParm.roomId"
								show-search
								placeholder="请选择实验室"
								style="width: 200px"
								:options="roomOptions"
								:filter-option="filterRoomOption"
								@change="openChange"
							></a-select>
						</a-form-item>
					</a-col>
					<a-col :span="8">
						<a-form-item v-bind="validateInfos.courseId" :labelCol="{ style: 'width:80px;' }" label="实训项目">
							<a-select
								v-model:value="addParm.courseId"
								show-search
								placeholder="请选择实训项目"
								style="width: 200px"
								:options="courseOptions"
								:filter-option="filterCourseOption"
							></a-select>
						</a-form-item>
					</a-col>
					<a-col :span="8" v-if="isTeacher">
						<a-form-item v-bind="validateInfos.teacherId" :labelCol="{ style: 'width:80px;' }" label="教师">
							<a-select
								v-model:value="addParm.teacherId"
								show-search
								placeholder="请选择教师"
								style="width: 200px"
								:options="teacherOptions"
								:filter-option="filterTeacherOption"
							></a-select>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="8">
						<a-form-item v-bind="validateInfos.classId" :labelCol="{ style: 'width:80px;' }" label="班级">
							<a-select
								v-model:value="addParm.classId"
								show-search
								placeholder="请选择班级"
								style="width: 200px"
								:options="classOptions"
								:filter-option="filterClassOption"
							></a-select>
						</a-form-item>
					</a-col>

					<!-- 
            <a-col :span="8">
            <a-form-item v-bind="validateInfos.reason" :labelCol="{ style: 'width:80px;' }" label="实验名称">
              <a-input v-model:value="addParm.reason" placeholder="请严格准确输入"></a-input>
            </a-form-item>
          </a-col>
          -->
					<a-col :span="8">
						<a-form-item v-bind="validateInfos.classNumber" :labelCol="{ style: 'width:80px;' }" label="上课人数">
							<a-input-number
								v-model:value="addParm.classNumber"
								:min="1"
								:max="100"
								style="width: 200px"
								placeholder="请输入上课人数"
							></a-input-number>
						</a-form-item>
					</a-col>
					<a-col :span="8">
						<a-form-item v-bind="validateInfos.startDate" :labelCol="{ style: 'width:80px;' }" label="上课日期">
							<a-date-picker v-model:value="selectTime" format="YYYY-MM-DD" @change="openChange" :disabled-date="disabledDate" />
						</a-form-item>
					</a-col>
				</a-row>

				<a-form-item v-bind="validateInfos.sequences" :labelCol="{ style: 'width:80px;' }" label="选课">
					<el-checkbox-group v-bind="validateInfos.sequences" v-model="addParm.sequences" size="large">
						<el-checkbox-button
							v-for="lesson in lessonOptions"
							:key="lesson.value"
							:label="lesson.value"
							:disabled="lesson.disabled === true"
						>
							{{ lesson.label}}
						</el-checkbox-button>
					</el-checkbox-group>
				</a-form-item>
				<a-row>
					<a-col :span="16">
						<a-form-item v-bind="validateInfos.remark" :labelCol="{ style: 'width:80px;' }" label="备注">
							<a-textarea
								v-model:value="addParm.remark"
								placeholder="输入上课需用耗材，比如鞋套、现勘箱等数量"
								:auto-size="{ minRows: 4, maxRows: 6 }"
							/>
						</a-form-item>
					</a-col>
				</a-row>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { ref, reactive, onMounted  } from 'vue'
import SysDialog from '@/components/SysDialog.vue';
import useDialog from '@/hooks/useDialog';
import useSelect from '@/composable/schedule/useSelect';
import { saveScheduleApi, getScheduleListBySelectedDate } from '@/api/schedule/schedule';
import {getUserId, getUserType} from '@/utils/auth';
import dayjs, { Dayjs } from 'dayjs'
import useForm from 'ant-design-vue/es/form/useForm';
import { isContinuityNum, bubbleSort } from '@/utils/tool';
const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const userId = getUserId();
const isTeacher = ref(getUserType() === '3' ? true: false);
console.log(userId );
console.log( isTeacher );
//下拉属性
const { roomOptions, teacherOptions, courseOptions, classOptions ,filterCourseOption, filterClassOption, filterRoomOption, filterTeacherOption } = useSelect()

//获取弹框属性
const { dialog, onShow, onClose } = useDialog()


//定义弹框显示的方法
const show = () => {
    resetFields()
    lessonOptions.value = [
    { value: 1, label: '第一节课',  disabled: false },
    { value: 2, label: '第二节课', disabled: false },
    { value: 3, label: '第三节课', disabled: false },
    { value: 4, label: '第四节课', disabled: false },
    { value: 5, label: '第五节课', disabled: false },
    { value: 6, label: '第六节课', disabled: false },
    { value: 7, label: '第七节课', disabled: false },
    { value: 8, label: '第八节课',disabled: false },
    { value: 9, label: '第九节课', disabled: false },
    { value: 10, label: '第十节课', disabled: false },
    { value: 11, label: '第十一节课', disabled: false },
    { value: 12, label: '第十二节课', disabled: false },
];
    selectTime.value = undefined;

    //设置弹框属性
    dialog.title = '约课'
    dialog.height = 300;
    dialog.width = 900;
    dialog.onConfirmTitle = "确定"
    dialog.onCloseTitle ="关闭"
    //显示弹框
    onShow()
}
//暴露出去，给外部组件使用
defineExpose({
    show
})

//表单绑定的对象
const addParm = reactive({
    courseId: '',   //
    teacherId: '',
    classId: '', //班级
    roomId: '',
    classNumber: undefined, //上课人数
    startDate: '', //开课时间
    endDate: '',//结课时间
   // weeks: [],//周几上课
   // beginTime: '', //上课时间
   // endTime: '',//下课时间
   // reason: '', //使用目的
    remark: '', //备注
    sequences: [] as any[], //选课序列

})


//表单验证规则
const rules = reactive({
    weeks: [{
        required: true,

        message: '请选择周几上课',
        trigger: 'change'
    }],
    startDate: [{
        required: true,
        message: '请选择日期',
        trigger: 'change'
    }],
    roomId: [{
        required: true,
        message: '请选择教室',
        trigger: 'change'
    }],
    // teacherId: [{
    //     required: true,
    //     message: '请选择教师',
    //     trigger: 'change'
    // }],
    courseId: [{
        required: true,
        message: '请选择课程',
        trigger: 'change'
    }],
    classId:[{
        required: true,
        message: '请选择班级',
        trigger: 'change'
    }],
    beginTime: [{
        required: true,
        message: '请选择上课时间',
        trigger: 'change'
    }],
    reason: [{
        required: true,
        message: '请输入',
        trigger: 'change'
    }],
    sequences: [{
        required: true,
        message: '请选择预约课时',
        trigger: 'change'
    }, {
        validator: (rule:any, value: any, callback:any) =>{
          console.log(value);
            if(!isContinuityNum(value)){
                return Promise.reject('不允许跨课时预约');
            }else{
                return Promise.resolve();
            }
        },
        trigger: 'change',
        message: '不允许跨课时预约'
    }],
    remark: [{
    }],
})
const { resetFields, validate, validateInfos } = useForm(addParm, rules);
//上课日期选择
const selectTime = ref<Dayjs>()

//日期选择事件回调
const openChange = async () => {
    //判断实验室和日期是否已经选择
    console.log("roomId : " + addParm.roomId);
    if( addParm.roomId === undefined || addParm.roomId === ''){
      return;
    }
    if( selectTime.value === undefined ){
      return;
    }

    addParm.startDate = selectTime.value.format('YYYY-MM-DD');
    addParm.endDate = selectTime.value.format('YYYY-MM-DD');

    const res = await getScheduleListBySelectedDate(Number(addParm.roomId), selectTime.value.format('YYYY-MM-DD'));
        if (res && res.code == 200) {
            //首先重置可选课程状态
            lessonOptions.value.forEach( (ele)=>{
                ele.disabled = false;
            });
            //修改已选状态
            res.data.forEach((element:any) => {
                const index = element.sequence-1;
                lessonOptions.value[index].disabled = true;
            });
        }
}

const initLessonOptions = [
    { value: 1, label: '第一节课',  disabled: false },
    { value: 2, label: '第二节课', disabled: false },
    { value: 3, label: '第三节课', disabled: false },
    { value: 4, label: '第四节课', disabled: false },
    { value: 5, label: '第五节课', disabled: false },
    { value: 6, label: '第六节课', disabled: false },
    { value: 7, label: '第七节课', disabled: false },
    { value: 8, label: '第八节课',disabled: false },
    { value: 9, label: '第九节课', disabled: false },
    { value: 10, label: '第十节课', disabled: false },
    { value: 11, label: '第十一节课', disabled: false },
    { value: 12, label: '第十二节课', disabled: false },
]

const lessonOptions = ref(initLessonOptions);

//注册事件
const emit = defineEmits(['refreshList'])
//弹框确定
const onConfirm = () => {
    console.log(addParm.sequences)
    validate().then(async () => {
        if( getUserType() === '2' ){ //如果当前登录人是老师
          console.log("userId : " + userId);
          addParm.teacherId = String(userId);
        }
        console.log(isTeacher)

        console.log(addParm);
        //如果是连续选课，需要按照课时进行排序后进行提交
        addParm.sequences = bubbleSort(addParm.sequences);
        const res = await saveScheduleApi(addParm);
        console.log(res);
        if (res && res.code == 200) {
            proxy?.$modal.msgSuccess("预约成功");
            emit('refreshList')
            onClose()
        }
    })
}

const disabledDate = (current: Dayjs) => {
      // 需要至少提前三天选择
      return current && current < dayjs().add(-1, 'day')
    };

onMounted(() => {
})
</script>
<style scoped lang="scss"></style>
