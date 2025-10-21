import axios from 'axios'
import { ElMessage } from 'element-plus'
const api = axios.create({
    baseURL: 'http://localhost:8099',
    timeout: 5000
})

api.interceptors.response.use(
    response => {
        const { code, msg } = response.data
        if (code == 3999) {
            ElMessage.error(msg || '业务异常')
            return Promise.reject({ business: true, data: response.data })
        }
        return response
    },
    error => {
        ElMessage.error('服务器错误，请稍后重试')
        return Promise.reject(error)
    }
)

export default api