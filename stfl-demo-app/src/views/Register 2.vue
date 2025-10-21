<template>
    <div class="auth-page">
        <div class="card">
            <div class="brand">
                <h1>创建账户</h1>
                <p class="subtitle">欢迎加入 — 注册一个新账号</p>
            </div>

            <el-form :model="form" :rules="rules" ref="registerForm" label-position="top" class="login-form">
                <el-form-item prop="username">
                    <el-input v-model="form.username" placeholder="用户名" clearable />
                </el-form-item>

                <el-form-item prop="password">
                    <el-input v-model="form.password" :type="showPassword ? 'text' : 'password'" placeholder="密码"
                        autocomplete="new-password">
                        <template #suffix>
                            <button type="button" class="icon-btn" @click="toggleShowPassword"
                                :aria-label="showPassword ? '隐藏密码' : '显示密码'">
                                <svg v-if="!showPassword" xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                    viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6"
                                    stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z"></path>
                                    <circle cx="12" cy="12" r="3"></circle>
                                </svg>
                                <svg v-else xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                    viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6"
                                    stroke-linecap="round" stroke-linejoin="round">
                                    <path
                                        d="M17.94 17.94A10.94 10.94 0 0 1 12 19c-7 0-11-7-11-7a19.94 19.94 0 0 1 5.06-4.94">
                                    </path>
                                    <path d="M1 1l22 22"></path>
                                    <path d="M9.88 9.88A3 3 0 0 0 14.12 14.12"></path>
                                </svg>
                            </button>
                        </template>
                    </el-input>
                </el-form-item>

                <el-form-item prop="confirm">
                    <el-input v-model="form.confirm" :type="showConfirm ? 'text' : 'password'" placeholder="重复密码"
                        autocomplete="new-password">
                        <template #suffix>
                            <button type="button" class="icon-btn" @click="toggleShowConfirm"
                                :aria-label="showConfirm ? '隐藏密码' : '显示密码'">
                                <svg v-if="!showConfirm" xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                    viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6"
                                    stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z"></path>
                                    <circle cx="12" cy="12" r="3"></circle>
                                </svg>
                                <svg v-else xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                    viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6"
                                    stroke-linecap="round" stroke-linejoin="round">
                                    <path
                                        d="M17.94 17.94A10.94 10.94 0 0 1 12 19c-7 0-11-7-11-7a19.94 19.94 0 0 1 5.06-4.94">
                                    </path>
                                    <path d="M1 1l22 22"></path>
                                    <path d="M9.88 9.88A3 3 0 0 0 14.12 14.12"></path>
                                </svg>
                            </button>
                        </template>
                    </el-input>
                </el-form-item>

                <div class="actions">
                    <el-button type="primary" class="login-btn" @click="handleRegister"
                        :loading="loading">注册</el-button>
                    <el-button type="text" @click="goLogin">已有账号，去登录</el-button>
                </div>

            </el-form>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register, login } from '../api/auth'

export default {
    name: 'Register',
    setup() {
        const router = useRouter()
        const registerForm = ref(null)
        const form = ref({ username: '', password: '', confirm: '' })
        const showPassword = ref(false)
        const showConfirm = ref(false)
        const loading = ref(false)

        const validateConfirm = (rule, value, callback) => {
            if (!value) return callback(new Error('请再次输入密码'))
            if (value !== form.value.password) return callback(new Error('两次输入密码不一致'))
            callback()
        }

        const rules = {
            username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
            password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            confirm: [{ validator: validateConfirm, trigger: 'blur' }]
        }

        const toggleShowPassword = () => (showPassword.value = !showPassword.value)
        const toggleShowConfirm = () => (showConfirm.value = !showConfirm.value)

        const handleRegister = () => {
            registerForm.value.validate(async valid => {
                if (!valid) return
                loading.value = true
                try {
                    const res = await register({ username: form.value.username, password: form.value.password })
                    if (res && (res.status === 200 || res.data?.success)) {
                        ElMessage.success('注册成功，正在登录...')
                        try {
                            const loginRes = await login({ username: form.value.username, password: form.value.password })
                            if (loginRes && (loginRes.status === 200 || loginRes.data?.success)) {
                                const token = loginRes.data?.token ?? loginRes.data?.data?.token
                                if (token) localStorage.setItem('token', token)
                                ElMessage.success('登录成功')
                                router.push('/home')
                            } else {
                                ElMessage.warning('注册成功，但自动登录失败，请手动登录')
                                router.push('/login')
                            }
                        } catch (e) {
                            console.error(e)
                            ElMessage.warning('注册成功，但自动登录异常，请手动登录')
                            router.push('/login')
                        }
                    } else {
                        ElMessage.error(res.data?.message || '注册失败')
                    }
                } catch (err) {
                    // console.error(err)
                    // ElMessage.error(err?.response?.data?.message || '注册异常')
                } finally {
                    loading.value = false
                }
            })
        }

        const goLogin = () => router.push('/login')

        return { registerForm, form, rules, showPassword, showConfirm, toggleShowPassword, toggleShowConfirm, handleRegister, goLogin, loading }
    }
}
</script>

<style scoped>
.auth-page {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #0f172a 0%, #0b3d91 60%);
    padding: 40px;
}

.card {
    width: 420px;
    background: linear-gradient(180deg, rgba(255, 255, 255, 0.02), rgba(255, 255, 255, 0.03));
    border-radius: 12px;
    padding: 28px;
    box-shadow: 0 10px 30px rgba(2, 6, 23, 0.6);
    backdrop-filter: blur(6px);
    color: #e6eef9;
    transition: transform .25s ease, box-shadow .25s ease;
}

.card:hover {
    transform: translateY(-6px);
    box-shadow: 0 18px 40px rgba(2, 6, 23, 0.7);
}

.brand h1 {
    margin: 0;
    font-size: 22px;
    font-weight: 700;
    color: #fff;
}

.subtitle {
    margin: 6px 0 18px;
    color: #cfe3ff;
    font-size: 13px;
}

.login-form {
    margin-top: 6px;
}

.el-input__inner {
    background: rgba(255, 255, 255, 0.04);
    color: #eaf3ff;
    border-radius: 8px;
    border: none;
}

.el-form-item {
    margin-bottom: 14px;
}

.icon-btn {
    background: transparent;
    border: none;
    padding: 0 6px;
    display: inline-flex;
    align-items: center;
    cursor: pointer;
    color: #9fb6ff;
}

.icon-btn svg {
    display: block
}

.actions {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 6px;
}

.login-btn {
    width: 64%;
}
</style>
