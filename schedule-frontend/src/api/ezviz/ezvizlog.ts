import http from '@/http';
import { ListParm } from './EzvizLogType';

//列表查询
export const getListApi = (parm: ListParm) => {
	return http.get({
		url: '/system/ezvizLog/list',
		params: parm
	});
};