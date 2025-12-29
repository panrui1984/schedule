<template>
	<div class="header-bg">
		<div class="leftHeader">
			<Collapse></Collapse>
			<Bred></Bred>
		</div>
		<div>
			<right-menu></right-menu>
		</div>
	</div>
</template>
<script setup lang="ts">
import Bred from './Bred.vue';
import RightMenu from './RightMenu.vue';
import Collapse from './Collapse.vue';
</script>
<style scoped lang="scss">
.header-bg {
  color: #000;
  display: flex;
  flex-grow: 1;
  align-items: center;
  justify-content: space-between;
  .leftHeader{
    display: flex;
  }
}
</style>
