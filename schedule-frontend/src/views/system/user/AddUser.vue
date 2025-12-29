<template>
	<sys-dialog
		:title="dialog.title"
		:width="dialog.width"
		:height="dialog.height"
		:visible="dialog.visible"
		@onConfirm="onConfirm"
		@onClose="onClose"
	>
		<template #content>
			<a-form style="margin-right: 30px;">
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.name" :labelCol="{ style: 'width:80px;' }" label="姓名">
							<a-input placeholder="请输入姓名" v-model:value="addModel.name"></a-input>
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.phone" :labelCol="{ style: 'width:80px;' }" label="电话">
							<a-input placeholder="请输入电话" v-model:value="addModel.phone"></a-input>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="邮箱">
							<a-input placeholder="请输入姓名" v-model:value="addModel.email"></a-input>
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.sex" :labelCol="{ style: 'width:80px;' }" label="性别">
							<a-radio-group v-model:value="addModel.sex">
								<a-radio :value="'0'">男</a-radio>
								<a-radio :value="'1'">女</a-radio>
							</a-radio-group>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.roleId" :labelCol="{ style: 'width:80px;' }" label="角色">
							<a-select
								style="width: 100%;"
								v-model:value="addModel.roleId"
								show-search
								placeholder="请选择角色"
								:options="roleList"
								:filter-option="filter"
							></a-select>
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.username" :labelCol="{ style: 'width:80px;' }" label="账户">
							<a-input placeholder="请输入登录账户" v-model:value="addModel.username"></a-input>
						</a-form-item>
					</a-col>
				</a-row>
				<a-row v-if="addModel.type == '0'">
					<a-col :span="12">
						<a-form-item v-bind="validateInfos.password" :labelCol="{ style: 'width:80px;' }" label="密码">
							<a-input type="password" placeholder="请输入登录密码" v-model:value="addModel.password"></a-input>
						</a-form-item>
					</a-col>
				</a-row>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { reactive } from 'vue';
import SysDialog from '@/components/SysDialog.vue';
import useDialog from '@/hooks/useDialog';
import { EditType, Title } from '@/type/BaseEnum';
import { UserType } from '@/api/user/UserType'
import useRole from '@/composable/user/useRole';
import useForm from 'ant-design-vue/es/form/useForm';
import { addUserApi, getUserByIdApi ,editUserApi} from '@/api/user/user'
import { message } from "ant-design-vue"
import useInstance from '@/hooks/useInstance';
const { global } = useInstance()
const { roleList, filter } = useRole()
//弹框属性
const { dialog, onClose, onShow } = useDialog()

//表单数据
const addModel = reactive<UserType>({
    userId: '',
    roleId: '',
    type: '',
    name: '',
    username: '',
    password: '',
    email: '',
    phone: '',
    sex: ''
})
//表单验证规则

//表单验证规则
const rules = reactive({
    roleId: [{
        required: true,
        message: '请选择角色',
        trigger: 'change'
    }],
    name: [{
        required: true,
        message: '请填写用户名',
        trigger: 'change'
    }],
    username: [{
        required: true,
        message: '请填写登录名',
        trigger: 'change'
    }],
    password: [{
        required: true,
        message: '请填写密码',
        trigger: 'change'
    }],
    phone: [{
        required: true,
        message: '请填写电话',
        trigger: 'change'
    }],
    sex: [{
        required: true,
        message: '请选择性别',
        trigger: 'change'
    }]
})

//获取表单验证属性
const { validate, resetFields, validateInfos } = useForm(addModel, rules)

//显示弹框
const show = async (type: string, row: UserType) => {
    console.log(row)
    resetFields()
    dialog.height = 225
    if (type == EditType.ADD) {
        dialog.title = Title.ADD
    } else {
        dialog.title = Title.EDIT
        //获取编辑的数据
        let res = await getUserByIdApi(row.userId)
        if (res && res.code == 200) {
            global.$objCoppy(res.data, addModel)
            console.log('返回成功')
            console.log(addModel)
        }
    }
    addModel.type = type;
    onShow()
}

defineExpose({
    show
})
//注册事件
const emit = defineEmits(['save', 'refresh'])
//弹框确定
const onConfirm = () => {
    validate().then(async () => {
        // emit('save', addModel)
        // onClose()
        let res = null;
        if (addModel.type == EditType.ADD) {
            res = await addUserApi(addModel)
        } else {
            res = await editUserApi(addModel)
        }
        if (res && res.code == 200) {
            //信息提示
            message.success(res.msg)
            //刷新表格
            emit('refresh')
            onClose()
        }
    })
}
</script>
<style scoped lang="scss"></style>
