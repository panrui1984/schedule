//路由
import router from './router';
//引入token
import { getToken, clearSession } from '@/utils/auth';
//引入store
import { menuStore } from '@/store/menu/index';
//白名单
const whiteList = ['/login', '/teach'];
//全局守卫路由处理
router.beforeEach(async (to, from, next) => {
	//注意，词句不能放到外面，会报
	// getActivePinia was called with no active Pinia的错误
	const store = menuStore();
	//获取token
	const token = getToken();
	if (token) {
		//token存在
		if (to.path === '/login' || to.path === '/' || to.path === '/teach') {
			next({ path: '/' });
		} else {
			//从store里面获取菜单数据
			const hasRoles = store.menuList && store.menuList.length > 0;
			if (hasRoles) {
				next();
			} else {
				try {
					//从服务器获取菜单数据
					await store.getMenuList(router);
					//等待路由全部挂载
					next({ ...to, replace: true });
				} catch (error) {
					clearSession();
					next({ path: '/login' });
				}
			}
		}
	} else {
		//token不存在
		//判断是否在白名单中
		if (whiteList.indexOf(to.path) !== -1) {
			next();
		} else {
			next({ path: '/login' });
		}
	}
});
