<template>
	<a-config-provider :locale="locale">
		<router-view />
	</a-config-provider>
</template>
<script setup lang="ts">
//国际化配置
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
import { ref } from 'vue';
dayjs.locale('zh-cn');
const locale = ref(zhCN)
</script>
<style lang="scss">
.scheduleClass {
  height: 400px;
  overflow-y: auto;
}
/*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar {
  width: 8px;
  height: 10px;
  background-color: #f5f5f5;
}

/*定义滚动条轨道 内阴影+圆角*/
/*::-webkit-scrollbar-track {*/
/*box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);*/
/*-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);*/
/*border-radius: 10px;*/
/*background-color: #F5F5F5;*/
/*}*/

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  /*border-radius: 10px;*/
  /*box-shadow: inset 0 0 6px rgba(0, 0, 0, .1);*/
  /*-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .1);*/
  /*background-color: #c8c8c8;*/
  background-color: #d7d7d7;
}

::-webkit-scrollbar-thumb:active {
  background-color: #b1b1b1;
}
:deep(.ant-modal-body) {
  padding: 10px !important;
}
:deep(.ant-menu-vertical) {
  border-right: none !important;
}
</style>
