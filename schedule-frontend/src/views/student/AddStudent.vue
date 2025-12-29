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
				<a-form-item v-bind="validateInfos.stuName" :labelCol="{ style: 'width:80px;' }" label="学生姓名">
					<a-input v-model:value="addModel.stuName" placeholder="请输入学生姓名"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.sex" :labelCol="{ style: 'width:80px;' }" label="性别">
					<a-radio-group v-model:value="addModel.sex">
						<a-radio :value="'0'">男</a-radio>
						<a-radio :value="'1'">女</a-radio>
					</a-radio-group>
				</a-form-item>
				<a-form-item v-bind="validateInfos.roleId" :labelCol="{ style: 'width:80px;' }" label="角色">
					<a-select
						style="width: 100%"
						v-model:value="addModel.roleId"
						show-search
						placeholder="请选择角色"
						:options="roleList"
						:filter-option="filter"
					></a-select>
				</a-form-item>
				<a-form-item v-bind="validateInfos.classId" :labelCol="{ style: 'width:80px;' }" label="班级">
					<a-select
						style="width: 100%"
						v-model:value="addModel.classId"
						show-search
						placeholder="请选择班级"
						:options="classList"
						:filter-option="classfilter"
					></a-select>
				</a-form-item>
				<a-form-item v-bind="validateInfos.phone" :labelCol="{ style: 'width:80px;' }" label="电话">
					<a-input v-model:value="addModel.phone" placeholder="请输入电话"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.stuNum" :labelCol="{ style: 'width:80px;' }" label="学号">
					<a-input v-model:value="addModel.stuNum" placeholder="请输入学号"></a-input>
				</a-form-item>
				<a-form-item v-if="addModel.type == '0'" v-bind="validateInfos.password" :labelCol="{ style: 'width:80px;' }" label="密码">
					<a-input type="password" v-model:value="addModel.password" placeholder="请输入密码"></a-input>
				</a-form-item>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import useDialog from "@/hooks/useDialog";
import SysDialog from "@/components/SysDialog.vue";
import { EditType, Title } from "@/type/BaseEnum";
import { StudentModel } from "@/api/student/StudentType";
import { reactive } from "vue";
import useClass from "@/composable/student/useClass";
import useForm from "ant-design-vue/es/form/useForm";
import useInstance from "@/hooks/useInstance";
import useRole from "@/composable/user/useRole";
import { getStuentByIdApi } from "@/api/student/student";
const { roleList, filter } = useRole();
const { classList, classfilter } = useClass();
//获取全局属性
const { global } = useInstance();
//弹框属性
const { dialog, onClose, onShow } = useDialog();

//定义表单数据对象
const addModel = reactive<StudentModel>({
  type: "",
  stuId: "",
  stuName: "",
  stuNum: "",
  sex: "",
  password: "",
  classId: "",
  roleId: "",
  phone: "",
});
//表单验证规则
const rules = reactive({
  stuName: [
    {
      required: true,
      message: "请输入姓名",
      trigger: "change",
    },
  ],
  stuNum: [
    {
      required: true,
      message: "请输入学号",
      trigger: "change",
    },
  ],
  sex: [
    {
      required: true,
      message: "请选择性别",
      trigger: "change",
    },
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "change",
    },
  ],
  phone: [
    {
      required: true,
      message: "请输入电话",
      trigger: "change",
    },
  ],
  classId: [
    {
      required: true,
      message: "请选择班级",
      trigger: "change",
    },
  ],
});

//获取表单相关的属性
const { validate, resetFields, validateInfos } = useForm(addModel, rules);

//弹框显示
const show = async (type: string, row: StudentModel) => {
  //清空表单
  resetFields();
  dialog.height = 350;
  if (type === EditType.ADD) {
    dialog.title = Title.ADD;
  } else {
    dialog.title = Title.EDIT;
    let res = await getStuentByIdApi(row.stuId);
    if (res && res.code == 200) {
      //设置页面回显的数据
      global.$objCoppy(res.data, addModel);
    }
  }
  addModel.type = type;
  onShow();
};

//暴露出去给外部组件使用
defineExpose({
  show,
});

//注册事件
const emit = defineEmits(["save"]);

//弹框确定
const onConfirm = () => {
  validate().then(() => {
    emit("save", addModel);
    onClose();
  });
};
</script>
<style scoped lang="scss"></style>
