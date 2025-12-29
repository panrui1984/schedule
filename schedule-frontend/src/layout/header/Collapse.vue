<template>
	<component @click="clickBtn" :is="isCollapse ? MenuUnfoldOutlined : MenuFoldOutlined" class="icons" />
</template>
<script setup lang="ts">
import { MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons-vue';
import { computed } from 'vue'
import { collapseStore } from '@/store/collapse';
//获取store
const store = collapseStore();

//获取isCollapse
const isCollapse = computed(() => {
    return store.getCollapse
})

//切换按钮点击事件
const clickBtn = () => {
    store.setCollapse()
}
</script>
<style scoped lang="scss">
.icons {
    font-size: 21px;
}
</style>
