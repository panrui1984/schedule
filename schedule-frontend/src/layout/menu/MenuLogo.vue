<template>
	<div class="logo">
		<img src="@/assets/logos.jpeg" />
		<span v-show="show" class="title">实训中心约课</span>
	</div>
</template>
<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { collapseStore } from '@/store/collapse';

//获取store
const store = collapseStore()

//collapsed
const collapse = computed(() => {
    return store.getCollapse
})

//定义show
const show = ref(true)

//监听collapsed值，做延时处理
//解决标题闪动的问题
watch(() => collapse.value, (collapsed: boolean) => {
    if (!collapsed) {
        setTimeout(() => {
            show.value = !collapsed
        }, 250)
    } else {
        show.value = !collapsed
    }
})
</script>
<style scoped lang="scss">
.logo {
    // background-color: #2b2f3a;
    background-color: #fff;
    height: 48px;
    border: none;
    line-height: 48px;
    display: flex;
    align-items: center;
    padding-left: 20px;
    color: #000;
    // border-right: 1px solid #e5e5e5;
    img {
        width: 36px;
        height: 36px;
        margin-right: 12px;
    }
    .title {
        height: 48px;
        background-color: #fff;
        text-align: center;
        color: #000;
        box-sizing: border-box;
        font-weight: 600;
        line-height: 48px;
        font-size: 20px;
        vertical-align: middle;
        overflow: hidden;
    }
}
</style>
