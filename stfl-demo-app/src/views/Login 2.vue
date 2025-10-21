<template>
    <div class="auth-page">
        <div class="card">
            <div class="brand">
                <h1>stfl-demo</h1>
                <p class="subtitle">欢迎回来 — 请登录</p>
            </div>

            <el-form :model="form" :rules="rules" ref="loginForm" label-position="top" class="login-form">
                <el-form-item prop="username">
                    <el-input v-model="form.username" placeholder="用户名" clearable />
                </el-form-item>

                <el-form-item prop="password" class="password-item">
                    <el-input v-model="form.password" :type="showPassword ? 'text' : 'password'" placeholder="密码"
                        autocomplete="off">
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

                <div class="actions">
                    <el-button type="primary" class="login-btn" @click="handleLogin" :loading="loading">登录</el-button>
                    <el-button type="text" @click="goRegister">注册</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api/auth'

export default {
    name: 'Login',
    setup() {
        const router = useRouter()
        const loginForm = ref(null)
        const form = ref({ username: '', password: '' })
        const showPassword = ref(false)
        const loading = ref(false)

        const rules = {
            username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
            password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
        }

        const toggleShowPassword = () => (showPassword.value = !showPassword.value)

        const handleLogin = () => {
            loginForm.value.validate(async valid => {
                if (!valid) return
                loading.value = true
                try {
                    const res = await login({ username: form.value.username, password: form.value.password })
                    if (res && (res.status === 200 || res.data?.success)) {
                        const token = res.data?.token ?? res.data?.data?.token
                        if (token) localStorage.setItem('token', token)
                        ElMessage.success('登录成功')
                        router.push('/home')
                    } else {
                        ElMessage.error(res.data?.message || '登录失败')
                    }
                } catch (err) {
                    // console.error(err)
                    // ElMessage.error(err?.response?.data?.message || '登录异常')
                } finally {
                    loading.value = false
                }
            })
        }

        const goRegister = () => router.push('/register')

        return { loginForm, form, rules, showPassword, toggleShowPassword, handleLogin, goRegister, loading }
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
    width: 380px;
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
