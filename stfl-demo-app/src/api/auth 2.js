import api from './reqIntercept'

// const api = axios.create({
//     baseURL: 'http://localhost:8099'
//     , timeout: 5000
// });

export const login = (payload) => api.post('/auth/login', payload);
export const register = (payload) => api.post('/auth/register', payload);
export const logout = () => api.post('/auth/logout');

export default api;