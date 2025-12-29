<template>
	<sys-dialog
		:title="dialog.title"
		:width="dialog.width"
		:height="dialog.height"
		:visible="dialog.visible"
		:on-confirm-title="dialog.onConfirmTitle"
		:on-close-title="dialog.onCloseTitle"
		@onClose="onClose"
		@onConfirm="onConfirm"
	>
		<template #content>
			<a-form style="margin-right: 30px;">
				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="日期">
							<a-input v-model:value="model.dateTime" />
						</a-form-item>
					</a-col>
					<a-col :span="18">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="教室">
							<a-input v-model:value="model.roomName" />
						</a-form-item>
					</a-col>
				</a-row>
				<a-row>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="开始时间">
							<a-input v-model:value="model.beginTime" />
						</a-form-item>
					</a-col>
					<a-col :span="12">
						<a-form-item :labelCol="{ style: 'width:80px;' }" label="结束时间">
							<a-input v-model:value="model.endTime" />
						</a-form-item>
					</a-col>
				</a-row>
			</a-form>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { reactive } from 'vue'
import SysDialog from '@/components/SysDialog.vue';
import useDialog from '@/hooks/useDialog';
import { Modal } from 'ant-design-vue';
import { MaintenanceModel } from "@/api/classroom/RoomType";
import useRoomMaintenance from '@/composable/classroom/useMaintenance';
import { removeMaintenanceApi as removeAPI } from '@/api/classroom/classroom';
import { message } from 'ant-design-vue';
import useInstance from '@/hooks/useInstance';
import { pa } from 'element-plus/es/locale';
//弹框属性
const { dialog, onClose, onShow, } = useDialog()
//获取全局global
const { global } = useInstance();
//表单绑定属性
const model = reactive({
    id: undefined,
    dateTime: undefined,
    roomName: undefined,
    beginTime: '',
    endTime: '',
})

//定义弹框显示的方法
const show = (params:any) => {
    //设置弹框属性
    dialog.title = "实验室维护";
    dialog.height = 200;
    dialog.width = 600;
    dialog.onConfirmTitle = "取消";
    dialog.onCloseTitle = "关闭";
    model.id = params.id;
    model.dateTime = params.dateTime;
    model.roomName = params.roomName;
    model.beginTime = params.beginTime;
    model.endTime = params.endTime;
    //显示弹框
    onShow();
};

//注册事件
const emit = defineEmits(['remove', "refreshList"])
//弹框确定
const onConfirm = async () => {
  const params: MaintenanceModel = {
    id : model.id
  }
  console.log("#####");
  console.log(params);
  //信息确定
  const confirm = await global.$myconfirm("您确认取消选定的实验室维护吗");
    if (confirm) {
      let res = await removeAPI(params);
      if (res && res.code == 200) {
        //信息提示
        message.success(res.msg);
        //刷新表格
        emit("refreshList")
        onClose();
      }
    }
}

//暴露出去，给外部组件使用
defineExpose({
    show,
});
</script>
<style scoped lang="scss"></style>
