import vue from '@vitejs/plugin-vue';
import path from 'path';

import createAutoImport from './auto-import';
import createComponents from './components';
import createCompression from './compression';
import createSetupExtend from './setup-extend';
import createSvgIconsPlugin from './svg-icon';

export default (viteEnv: any, isBuild = false): [] => {
	const vitePlusgins: any = [];
	vitePlusgins.push(vue());
	vitePlusgins.push(createAutoImport(path));
	vitePlusgins.push(createComponents(path));
	vitePlusgins.push(createCompression(viteEnv));

	vitePlusgins.push(createSvgIconsPlugin(path, isBuild));
	vitePlusgins.push(createSetupExtend());
	return vitePlusgins;
};
