<template>
	<sys-dialog :title="dialog.title" :width="dialog.width" :height="dialog.height" :visible="dialog.visible" @onClose="onClose" @onConfirm="confirm">
		<template #content>
			<a-tree
				ref="assignTree"
				v-if="treeData && treeData?.length > 0"
				class="myTree"
				checkable
				defaultExpandAll
				:show-line="showLine"
				:tree-data="treeData"
				:fieldNames="fieldNames"
				v-model:checkedKeys="checkedKeys"
			></a-tree>
		</template>
	</sys-dialog>
</template>
<script setup lang="ts">
import { ref, reactive } from 'vue'
import SysDialog from '@/components/SysDialog.vue';
import useDialog from '@/hooks/useDialog';
import { RoleType } from '@/api/role/RoleType';
import useAssign from '@/composable/role/useAssign';
import { getUserId } from '@/utils/auth'
import { assignSaveApi } from '@/api/role/role'
import { message } from 'ant-design-vue';
const { assignTree, showLine, checkedKeys, fieldNames, treeData, getTreeData } = useAssign()
const { dialog, onClose, onShow } = useDialog()
const roleId = ref()
//弹框确定
const confirm = async () => {
    console.log(assignTree.value)
    console.log(assignTree.value.checkedKeys)
    console.log(assignTree.value.halfCheckedKeys)
    const ids = assignTree.value.checkedKeys.concat(assignTree.value.halfCheckedKeys)
    console.log(ids)
    let parm = {
        roleId: roleId.value,
        list: ids
    }
    let res = await assignSaveApi(parm)
    if (res && res.code == 200) {
        message.success(res.msg)
        onClose()
    }
}
//显示弹框
const show = async (row: RoleType) => {
    roleId.value = row.roleId
    dialog.title = '为【' + row.roleName + '】分配权限'
    dialog.height = 450;
    dialog.width = 300
    let parm = {
        userId: getUserId() || '',
        roleId: row.roleId
    }
    //获取树的数据
    await getTreeData(parm)
    onShow()
}
defineExpose({
    show
})
</script>
<style lang="scss">
.myTree {
    .ant-tree-switcher-noop {
        display: none !important;
    }
}
</style>
