<template>
	<sys-dialog :title="dialog.title" :height="dialog.height" :width="dialog.width" :visible="dialog.visible" @onClose="onClose" @onConfirm="confirm">
		<template #content>
			<a-form>
				<a-form-item v-bind="validateInfos.newPassword" label="新密码">
					<a-input v-model:value="addModel.newPassword" placeholder="请输入新密码"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.confirmPassword" label="确认密码">
					<a-input v-model:value="addModel.confirmPassword" placeholder="请输入确认密码"></a-input>
				</a-form-item>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { ref, reactive } from 'vue'
import useDialog from '@/hooks/useDialog';
import SysDialog from '@/components/SysDialog.vue';
import { RoomModel } from '@/api/classroom/RoomType'
import useForm from 'ant-design-vue/es/form/useForm';
import useInstance from '@/hooks/useInstance';
import { User } from '@/api/user/UserType';
//获取全局属性
const { global } = useInstance()

//获取弹框属性
const { dialog, onShow, onClose } = useDialog()

//表单验证规则
const rules = reactive({
    newPassword: [{
        required: true,
        message: '请输入实验室名称',
        trigger: 'change'
    }],
    confirmPassword: [{
        required: true,
        message: '请输入实验室地址',
        trigger: 'change'
    }],
})

//表单绑定的对象
const addModel = reactive<User>({
    userId: '',
    newPassword: '',
    confirmPassword: '',
})

//获取表单验证属性
const { validate, resetFields, validateInfos } = useForm(addModel, rules)

//注册事件
const emit = defineEmits(['save'])

//弹框确定
const confirm = () => {
    validate().then(() => {
        emit('save', addModel)
        onClose();
    })
}

//显示弹框
const show = (type: string, row: RoomModel) => {
    //清空表单
    resetFields();
    dialog.height = 180;
    dialog.title = '修改密码';
    dialog.onConfirmTitle = '确定';
    dialog.onCloseTitle = '关闭'
    onShow();
}

defineExpose({
    show
})
</script>
<style scoped lang="scss"></style>
