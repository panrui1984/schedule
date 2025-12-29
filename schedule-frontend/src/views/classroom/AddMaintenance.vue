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
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.roomId" :labelCol="{ style: 'width:80px;' }" label="实验室">
							<a-select
								v-model:value="addParm.roomId"
								show-search
								placeholder="请选择实验室"
								style="width: 300px"
								:options="roomOptions"
								:filter-option="filterRoomOption"
							></a-select>
						</a-form-item>
					</a-col>
				</a-row>
				<a-form-item v-bind="validateInfos.startDate" :labelCol="{ style: 'width:80px;' }" label="日期">
					<a-date-picker v-model:value="selectTime" format="YYYY-MM-DD" @change="openChange" />
				</a-form-item>
				<a-form-item v-bind="validateInfos.sequences" :labelCol="{ style: 'width:80px;' }" label="课时">
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
					<a-col :span="8">
						<a-form-item v-bind="validateInfos.remark" :labelCol="{ style: 'width:80px;' }" label="备注">
							<a-textarea v-model:value="addParm.remark" placeholder="" :auto-size="{ minRows: 4, maxRows: 6 }" />
						</a-form-item>
					</a-col>
				</a-row>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { ref, reactive, onMounted, nextTick, createVNode } from "vue";
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import useSelect from "@/composable/schedule/useSelect";
import { getScheduleListBySelectedDate } from "@/api/schedule/schedule";
import { addMaintenanceApi as addAPI, removeMaintenanceApi as removeAPI } from '@/api/classroom/classroom';
import dayjs, { Dayjs } from "dayjs";
import useForm from "ant-design-vue/es/form/useForm";
import { MaintenanceModel } from "@/api/classroom/RoomType";
import { message } from 'ant-design-vue';
//下拉属性
const { roomOptions, filterRoomOption,} = useSelect();

//获取弹框属性
const { dialog, onShow, onClose } = useDialog();

//定义弹框显示的方法
const show = (model: any) => {
    resetFields();
    selectTime.value = undefined;
    //设置弹框属性
    dialog.title = "实验室维护";
    dialog.height = 300;
    dialog.width = 900;
    dialog.onConfirmTitle = "确定";
    dialog.onCloseTitle = "关闭";
    //显示弹框
    onShow();
};


//表单绑定的对象
const addParm = reactive<Partial<MaintenanceModel>>({
  //  roomId: null,
    startDate: "", //开课时间
    endDate: "", //结课时间
    remark: "", //备注
    sequences: [], //选课序列
});

const rules = reactive({
    startDate: [
        {
            required: true,
            message: "请选择日期",
            trigger: "change",
        },
    ],
    roomId: [
        {
            required: true,
            message: "请选择实验室",
            trigger: "change",
        },
    ],
    sequences: [
        {
            required: true,
            message: "请选择维护的课时",
            trigger: "change",
        },
    ],
});
const { resetFields, validate, validateInfos } = useForm(addParm, rules);
//上课日期选择
const selectTime = ref<Dayjs>();

//日期选择事件回调
const openChange = async (data: string, dateString: string) => {
    console.log("roomId : " + addParm.roomId);
    console.log(dateString);
    addParm.startDate = dateString;
    addParm.endDate = dateString;
    if( addParm.roomId == undefined)
        return;
    const res = await getScheduleListBySelectedDate(addParm.roomId, dateString);
    if (res && res.code == 200) {
        //首先重置可选课程状态
        lessonOptions.value.forEach((ele) => {
            ele.disabled = false;
        });
        //修改已选状态
        res.data.forEach((element: any) => {
            const index = element.sequence - 1;
            lessonOptions.value[index].disabled = true;
        });
    }

};


const lessonOptions = ref([
    { value: 1, label: "第一节课", disabled: false },
    { value: 2, label: "第二节课", disabled: false },
    { value: 3, label: "第三节课", disabled: false },
    { value: 4, label: "第四节课", disabled: false },
    { value: 5, label: "第五节课", disabled: false },
    { value: 6, label: "第六节课", disabled: false },
    { value: 7, label: "第七节课", disabled: false },
    { value: 8, label: "第八节课", disabled: false },
    { value: 9, label: "第九节课", disabled: false },
    { value: 10, label: "第十节课", disabled: false },
    { value: 11, label: "第十一节课", disabled: false },
    { value: 12, label: "第十二节课", disabled: false },
]);

//注册事件
const emit = defineEmits(["add", "refreshList"]);

//弹框确定
const onConfirm = () => {
  validate().then(async () => {
        let res  = await addAPI(addParm)
        if (res && res.code == 200) {
            //信息提示
            message.success(res.msg)
            //刷新表格
            emit("refreshList")
            onClose();
        }
    })
}


//暴露出去，给外部组件使用
defineExpose({
    show,
});
</script>
<style scoped lang="scss"></style>
