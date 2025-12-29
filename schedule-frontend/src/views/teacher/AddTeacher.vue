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
				<a-form-item v-bind="validateInfos.teacherName" :labelCol="{ style: 'width:80px;' }" label="教师姓名">
					<a-input v-model:value="addModel.teacherName" placeholder="请输入教师姓名"></a-input>
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
				<a-form-item v-bind="validateInfos.teacherNum" :labelCol="{ style: 'width:80px;' }" label="教师编号">
					<a-input v-model:value="addModel.teacherNum" placeholder="请输入教师编号"></a-input>
				</a-form-item>
				<a-form-item v-if="addModel.type == '0'" v-bind="validateInfos.password" :labelCol="{ style: 'width:80px;' }" label="密码">
					<a-input type="password" v-model:value="addModel.password" placeholder="请输入密码"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.phone" :labelCol="{ style: 'width:80px;' }" label="手机号码">
					<a-input v-model:value="addModel.phone" placeholder="请输入手机号码"></a-input>
				</a-form-item>
				<a-form-item v-bind="validateInfos.email" :labelCol="{ style: 'width:80px;' }" label="电子邮箱">
					<a-input v-model:value="addModel.email" placeholder="请输入电子邮箱"></a-input>
				</a-form-item>
				<a-form-item :labelCol="{ style: 'width:80px;' }" label="描述">
					<a-input v-model:value="addModel.teacherDesc" placeholder="请输入描述"></a-input>
				</a-form-item>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import useDialog from "@/hooks/useDialog";
import SysDialog from "@/components/SysDialog.vue";
import { EditType, Title } from "@/type/BaseEnum";
import { TeacherModel } from "@/api/teacher/TeacherType";
import { reactive } from "vue";
import useForm from "ant-design-vue/es/form/useForm";
import useRole from "@/composable/user/useRole";
import useInstance from "@/hooks/useInstance";
import { getTeacherByIdApi } from "@/api/teacher/teacher";
const { roleList, filter } = useRole();
//获取全局属性
const { global } = useInstance();
//弹框属性
const { dialog, onClose, onShow } = useDialog();

//定义表单数据对象
const addModel = reactive<TeacherModel>({
  type: "",
  teacherId: "",
  roleId: "",
  teacherName: "",
  teacherNum: "",
  teacherDesc: "",
  password: "",
  phone: "",
  email: "",
});
//表单验证规则
const rules = reactive({
  teacherName: [
    {
      required: true,
      message: "请输入教师名称",
      trigger: "change",
    },
  ],
  teacherNum: [
    {
      required: true,
      message: "请输入教师编号",
      trigger: "change",
    },
  ],
  roleId: [
    {
      required: true,
      message: "请选择角色",
      trigger: "change",
    },
  ],
  email: [
  {
      required: true,
      type: "email",
      message: "请输入正确的邮箱地址",
      trigger: ["blur", "change"]
    },
  ],
  phone: [
  {
      required: true,
      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
      message: "请输入正确的手机号码",
      trigger: "change",
    },
  ]
});

//获取表单相关的属性
const { validate, resetFields, validateInfos } = useForm(addModel, rules);

//弹框显示
const show = async (type: string, row: TeacherModel) => {
  //清空表单
  resetFields();
  dialog.height = 280;
  if (type === EditType.ADD) {
    dialog.title = Title.ADD;
  } else {
    dialog.title = Title.EDIT;
    //设置页面回显的数据
    let res = await getTeacherByIdApi(row.teacherId);
    if (res && res.code == 200) {
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
