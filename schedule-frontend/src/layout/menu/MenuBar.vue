<template>
	<menu-logo></menu-logo>
	<a-menu v-model:openKeys="menuData.openKeys" v-model:selectedKeys="menuData.selectedKeys" mode="inline" theme="light" @openChange="onOpenChange">
		<template v-for="item in menuList" :key="item.path">
			<template v-if="item.children && item.children.length == 0">
				<a-menu-item :key="item.path">
					<template #icon>
						<component :is="item.meta.icon" />
					</template>
					<router-link :to="item.path">{{ item.meta.title }}</router-link>
				</a-menu-item>
			</template>
			<template v-else>
				<sub-menu :key="item.path" :menu-info="item" />
			</template>
		</template>
	</a-menu>
</template>
<script setup lang="ts">
import SubMenu from './SubMenu.vue';
import { useRoute } from 'vue-router';
import MenuLogo from './MenuLogo.vue';
import { ref, reactive, onMounted, watch, computed } from 'vue'
// import { routes } from '@/router'
import { menuStore } from '@/store/menu/index'
const store = menuStore()
//菜单
const menuList = computed(()=>{
    return store.menuList as any
})
//当前路由
const route = useRoute()
const menuData = reactive({
    //相当于当前路由的path
    selectedKeys: [''],
    openKeys: ['']
})
//设置当前选中的菜单
const selectkey = () => {
    menuData.selectedKeys.push(route.path)
}
//路由发生变化的时候
watch(() => route.path, () => {
    //清空原来的数据
    menuData.selectedKeys = [''];
    selectkey()
    setMenuOpen(menuList.value)
})
onMounted(() => {
    selectkey()
    setMenuOpen(menuList.value)
})

//解决刷新之后，有下级的菜单，需要展开
const setMenuOpen = (result: any) => {
    //清空原来的数据
    menuData.openKeys = ['']
    for (let i = 0; i < result.length; i++) {
        if (result[i].children) {
            for (let y = 0; y < result[i].children.length; y++) {
                if (result[i].children[y].path === route.path) {
                    menuData.openKeys = [result[i].path]
                }
            }
        }
    }
}

//解决一次只能展开一个菜单的问题
const onOpenChange = (openKeys: string[]) => {
    console.log(openKeys)
    if (openKeys.length !== 0) {
        menuData.openKeys = [openKeys[1]]
    } else {
        menuData.openKeys = ['']
    }
}
</script>
<style scoped lang="scss"></style>
