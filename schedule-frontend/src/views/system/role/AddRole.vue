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
			<a-form style="margin-right: 15px;">
				<a-form-item v-bind="validateInfos.roleName" :labelCol="{ style: 'width:80px;' }" label="角色名称">
					<a-input v-model:value="addModel.roleName"></a-input>
				</a-form-item>
				<a-form-item :labelCol="{ style: 'width:80px;' }" label="角色描述">
					<a-input v-model:value="addModel.roleDesc"></a-input>
				</a-form-item>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { ref, reactive } from 'vue'
import SysDialog from '@/components/SysDialog.vue';
import useDialog from '@/hooks/useDialog';
import { EditType, Title } from '@/type/BaseEnum';
import useForm from 'ant-design-vue/es/form/useForm';
import useInstance from '@/hooks/useInstance';
import { RoleType } from '@/api/role/RoleType';
const { global } = useInstance()
//弹框属性
const { dialog, onClose, onShow } = useDialog()

//绑定弹框的对象
const addModel = reactive({
    type: '',
    roleId: '',
    roleName: '',
    roleDesc: ''
})

//表单验证规则
const rules = reactive({
    roleName: [{
        required: true,
        message: '请填写角色名称',
        trigger: 'change'
    }]
})
//获取验证属性
const { validate, resetFields, validateInfos } = useForm(addModel, rules)

//注册事件
const emit = defineEmits(['save'])

//弹框确定
const onConfirm = () => {
    validate().then(() => {
        emit('save', addModel)
        onClose()
    })
}

//弹框显示
const show = (type: string, row: RoleType) => {
    resetFields();//清空表单
    dialog.height = 180;
    if (type == EditType.ADD) {
        dialog.title = Title.ADD
    } else {
        dialog.title = Title.EDIT
        //把要编辑的数据设置到表单对象
        global.$objCoppy(row, addModel)
    }
    //设置type
    addModel.type = type;
    //显示弹框
    onShow()
}

//暴露给外部使用
defineExpose({
    show
})
</script>
<style scoped lang="scss"></style>
