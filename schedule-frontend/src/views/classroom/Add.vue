<template>
	<sys-dialog :title="dialog.title" :height="dialog.height" :width="dialog.width" :visible="dialog.visible" @onClose="onClose" @onConfirm="confirm">
		<template #content>
			<a-form>
				<a-form-item v-bind="validateInfos.roomName" label="教室名称">
					<a-input v-model:value="addModel.roomName" placeholder="请输入教室名称"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.roomAddress" label="教室地址">
					<a-input v-model:value="addModel.roomAddress" placeholder="请输入教室地址"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.code" label="实验场所代码">
					<a-input v-model:value="addModel.code" placeholder="请输入实验场所代码"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.isEzviz" label="电子门锁">
					<a-switch
						v-model:checked="addModel.isEzviz"
						:checkedValue="1"
						:unCheckedValue="0"
						checked-children="是"
						un-checked-children="否"
						@change="handleChange"
					/>
				</a-form-item>
				<a-form-item v-bind="validateInfos.deviceSerial" label="电子锁设备编码">
					<a-input v-model:value="addModel.deviceSerial" placeholder="请输入电子锁设备编码（如有）"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.unit" label="所属单位号">
					<a-input v-model:value="addModel.unit" placeholder="请输入所属单位号"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.unitName" label="所属单位名称">
					<a-input v-model:value="addModel.unitName" placeholder="请输入所属单位名称"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.category" label="性质">
					<a-select v-model:value="addModel.category" placeholder="请选择实验室性质" :options="options" style="width:130px;"></a-select>
				</a-form-item>
				<a-form-item v-bind="validateInfos.size" label="面积">
					<a-input v-model:value="addModel.size" placeholder="请输入实验室面积"></a-input>
				</a-form-item>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { ref, reactive } from 'vue'
import type { SelectProps } from 'ant-design-vue';
import useDialog from '@/hooks/useDialog';
import SysDialog from '@/components/SysDialog.vue';
import { EditType, Title } from '@/type/BaseEnum';
import { RoomModel } from '@/api/classroom/RoomType'
import useForm from 'ant-design-vue/es/form/useForm';
import useInstance from '@/hooks/useInstance';
//获取全局属性
const { global } = useInstance()

//获取弹框属性
const { dialog, onShow, onClose } = useDialog()

//下拉选择的数据
const options = ref<SelectProps['options']>([
  { value: '专业实验室', label: '专业实验室' },
  { value: '基础实验室', label: '基础实验室' }
]);


//表单验证规则
const rules = reactive({
    roomName: [{
        required: true,
        message: '请输入实验室名称',
        trigger: 'change'
    }],
    roomAddress: [{
        required: true,
        message: '请输入实验室地址',
        trigger: 'change'
    }],
    code: [{
        required: true,
        message: '请输入实验场所代码',
        trigger: 'change'
    }],
    category: [{
        required: true,
        message: '请选择实验室性质',
        trigger: 'change'
    }]
})

//表单绑定的对象
const addModel = reactive<RoomModel>({
    type: '',
    roomId: '',
    roomAddress: '',
    roomName: '',
    code: '',
    isEzviz: 0,
    deviceSerial: '',
    category: '',
    unit: '',
    unitName: '',
    size: '',
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

const handleChange = async () => {
    addModel.deviceSerial = '';
}

//显示弹框
const show = (type: string, row: RoomModel) => {
    //清空表单
    resetFields()
    dialog.height = 180;
    if (type === EditType.ADD) {
        dialog.title = Title.ADD
    } else {
        dialog.title = Title.EDIT
        //设置回显的数据
        global.$objCoppy(row,addModel)
    }
    addModel.type = type;
    onShow()
}

defineExpose({
    show
})
</script>
<style scoped lang="scss"></style>
