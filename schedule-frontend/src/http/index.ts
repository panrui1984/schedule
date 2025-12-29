import request from './request';
// const http = new request({
//     baseURL: '/schedule-admin',
//     timeout: 10000
// })
// const http = new request({
//     baseURL: 'http://127.0.0.1:8080',
//     timeout: 10000
// })

const http = new request({
	baseURL: import.meta.env.VITE_API_URL,
	timeout: 10000
});
export default http;
