/**
 * 参数处理
 * @param {*} params  参数
 */
export function tansParams(params: any) {
	let result = '';
	for (const propName of Object.keys(params)) {
		const value = params[propName];
		var part = encodeURIComponent(propName) + '=';
		if (value !== null && value !== '' && typeof value !== 'undefined') {
			if (typeof value === 'object') {
				for (const key of Object.keys(value)) {
					if (value[key] !== null && value[key] !== '' && typeof value[key] !== 'undefined') {
						let params = propName + '[' + key + ']';
						var subPart = encodeURIComponent(params) + '=';
						result += subPart + encodeURIComponent(value[key]) + '&';
					}
				}
			} else {
				result += part + encodeURIComponent(value) + '&';
			}
		}
	}
	return result;
}

/**
 * 判断数组是否是连续数字
 */

export const isContinuityNum = (num: number[]) => {
	let array: number[] = [];
	if (num instanceof Array) {
		array = [...num];
	}
	const new_array = bubbleSort(array); //排序
	console.log(new_array);
	let i = new_array[0];
	let isContinuation = true;
	for (let e in new_array) {
		if (new_array[e] != i) {
			isContinuation = false;
			break;
		}
		i++;
	}
	return isContinuation;
};

export const bubbleSort = (arr: number[]) => {
	for (var i = 0; i < arr.length - 1; i++) {
		for (var j = 0; j < arr.length - i; j++) {
			if (arr[j] > arr[j + 1]) {
				// 交换两个数的位置
				var temp = 0;
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}
	}

	return arr;
};

// 验证是否为blob格式
export const blobValidate = (data: any) => {
	return data.type !== 'application/json';
};
