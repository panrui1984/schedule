<template>
	<sys-dialog
		:title="dialog.title"
		:width="dialog.width"
		:height="dialog.height"
		:visible="dialog.visible"
		@onClose="onClose"
		@onConfirm="onConfirm"
	>
		<template #content>
			<a-form labelAlign="right">
				<a-form-item v-bind="validateInfos.courseName" :labelCol="{ style: 'width:80px;' }" label="实训项目">
					<a-input v-model:value="addModel.courseName" placeholder="请填写实训项目"></a-input>
				</a-form-item>
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.courseYear" :labelCol="{ style: 'width:80px;' }" label="学年">
							<a-date-picker
								placeholder="请选择学年"
								@openChange="openChange"
								style="width:100%;"
								v-model:value="inputYear"
								picker="year"
							/>
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.courseType" :labelCol="{ style: 'width:80px;' }" label="学期">
							<a-radio-group v-model:value="addModel.courseType">
								<a-radio :value="'0'">春季</a-radio>
								<a-radio :value="'1'">秋季</a-radio>
							</a-radio-group>
						</a-form-item>
					</a-col>
				</a-row>
				<a-form-item v-bind="validateInfos.courseColor" :labelCol="{ style: 'width:80px;' }" label="背景颜色">
					<sketch-picker v-model="addModel.courseColor" placeholder="请填写背景颜色" />
					<!-- <a-input v-model:value="addModel.courseColor" placeholder="请填写背景颜色"></a-input> -->
					<pick-colors v-model:value="addModel.courseColor" width="100" />
				</a-form-item>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue'
import SysDialog from '@/components/SysDialog.vue';
import dayjs, { Dayjs } from 'dayjs';
import PickColors from 'vue-pick-colors'
import { useForm } from 'ant-design-vue/lib/form';
import { CourseModel } from '@/api/course/BaseCourse';
import { EditType, Title } from '@/type/BaseEnum';
import useDialog from '@/hooks/useDialog';
import useInstance from '@/hooks/useInstance';
//获取全局属性
const { global } = useInstance()
//弹框属性
const { dialog, onClose, onShow } = useDialog()
//年份数据
const inputYear = ref<Dayjs>()

//表单验证规则
const rules = reactive({
    courseName: [{
        required: true,
        message: '请输入实训项目',
        trigger: 'change'
    }],
    courseType: [{
        required: true,
        message: '请选择学期',
        trigger: 'change'
    }],
    courseYear: [{
        required: true,
        message: '请选择年份',
        trigger: 'change'
    }],
    courseColor: [{
        required: true,
        message: '请填写背景色',
        trigger: 'change'
    }],
})
//表单绑定的数据
const addModel = reactive<CourseModel>({
    courseId: '',
    courseName: '',
    courseType: '',
    courseYear: '',
    courseColor: '',
    type: ''
})

//获取表单验证相关的属性
const { resetFields, validate, validateInfos } = useForm(addModel, rules)
//日历事件
const openChange = () => {
    addModel.courseYear = dayjs(inputYear.value).format('YYYY')
}
//注册事件
const emits = defineEmits(['save'])
//弹框确定事件
const onConfirm = () => {
    validate().then(async () => {
        emits('save',addModel)
        onClose();
        // let res = await addCourseApi(addModel)
        // if (res && res.code == 200) {
        //     message.success(res.msg)
        //     dialog.visible = false;
        // }
    })

}

//显示弹框
const show = (type: string, row: CourseModel) => {
    //清空表单
    resetFields()
    //判断是新增还是编辑
    if (type === EditType.ADD) {
        inputYear.value = undefined;
        dialog.title = Title.ADD
    } else {
        dialog.title = Title.EDIT
        //编辑回显，把当前编辑的数据，设置到表单绑定的数据对象addModel里面
        global.$objCoppy(row, addModel)
        //设置回显时间
        inputYear.value = dayjs(row.courseYear)
    }
    //设置编辑的类型
    addModel.type = type;
    //弹框显示
    onShow();
}

//暴露方法给外部使用
defineExpose({
    show
})
</script>
<style scoped lang="scss"></style>
