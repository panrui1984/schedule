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
				<a-form-item v-bind="validateInfos.className" :labelCol="{ style: 'width:80px;' }" label="班级名称">
					<a-input v-model:value="addModel.className" placeholder="请输入班级名称"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.grade" :labelCol="{ style: 'width:80px;' }" label="年级">
					<a-input v-model:value="addModel.grade" placeholder="请输入年级"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.classNumber" :labelCol="{ style: 'width:80px;' }" label="班级人数">
					<a-input-number v-model:value="addModel.classNumber" placeholder="请输入班级人数" :min="1" :max="150"></a-input-number>
				</a-form-item>
				<a-form-item v-bind="validateInfos.dadui" :labelCol="{ style: 'width:80px;' }" label="大队">
					<a-input v-model:value="addModel.dadui" placeholder="请输入大队"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.calassDesc" :labelCol="{ style: 'width:80px;' }" label="描述">
					<a-input v-model:value="addModel.calassDesc" placeholder="请输入描述"></a-input>
				</a-form-item>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import useDialog from '@/hooks/useDialog';
import SysDialog from '@/components/SysDialog.vue';
import { EditType, Title } from '@/type/BaseEnum';
import { ClassesModel } from '@/api/classes/ClassesType'
import { reactive } from 'vue';
import useForm from 'ant-design-vue/es/form/useForm';
import useInstance from '@/hooks/useInstance';
//获取全局属性
const { global } = useInstance()
//弹框属性
const { dialog, onClose, onShow } = useDialog()


//定义表单数据对象
const addModel = reactive<ClassesModel>({
    type: '',
    classId: '',
    className: '',
    grade: '',
    classNumber: undefined,
    dadui: '',
    calassDesc: ''
})
//表单验证规则
const rules = reactive({
    className: [{
        required: true,
        message: '请输入班级名称',
        trigger: 'change'
    }],
    grade: [{
        required: true,
        message: '请输入年级',
        trigger: 'change'
    }],
    classNumber: [{
        required: true,
        message: '请输入班级人数',
        trigger: 'change'
    }],
    dadui: [{
        required: true,
        message: '请输入大队',
        trigger: 'change'
    }]
})

//获取表单相关的属性
const { validate, resetFields, validateInfos } = useForm(addModel, rules)

//弹框显示
const show = (type: string, row: ClassesModel) => {
    //清空表单
    resetFields()
    dialog.height = 300;
    if (type === EditType.ADD) {
        dialog.title = Title.ADD
    } else {
        dialog.title = Title.EDIT
        //设置页面回显的数据
        global.$objCoppy(row, addModel)
    }
    addModel.type = type;
    onShow();
}

//暴露出去给外部组件使用
defineExpose({
    show
})

//注册事件
const emit = defineEmits(['save'])

//弹框确定
const onConfirm = () => {
    validate().then(() => {
        emit('save', addModel)
        onClose()
    })

}
</script>
<style scoped lang="scss"></style>
