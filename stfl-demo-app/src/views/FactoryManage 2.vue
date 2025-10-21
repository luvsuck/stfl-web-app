<template>
    <div class="factory-manage">
        <div class="toolbar">
            <el-input v-model="searchKey" placeholder="🔍 输入关键字搜索工厂" clearable class="search-input"
                @keyup.enter.native="handleSearch">
                <template #append>
                    <el-button type="primary" @click="handleSearch">搜索</el-button>
                </template>
            </el-input>

            <el-button type="success" plain class="add-btn" @click="openAddDialog">
                <i class="el-icon-plus"></i> 新增工厂
            </el-button>
        </div>


        <!-- 工厂列表 -->
        <el-table :data="factoryList" border stripe style="width: 100%" header-cell-class-name="table-header">
            <el-table-column prop="id" label="ID" width="80" align="center" />
            <el-table-column prop="name" label="工厂名称" align="center" />
            <el-table-column prop="location" label="工厂位置" align="center" />
            <el-table-column prop="level" label="工厂等级" align="center" />
            <el-table-column prop="description" label="描述" align="center" />
            <el-table-column label="操作" align="center" width="200">
                <template #default="scope">
                    <el-button type="text" @click="openEditDialog(scope.row)">编辑</el-button>
                    <el-button type="text" style="color: #f56c6c" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 新增/编辑弹窗 -->
        <el-dialog v-model="dialogVisible" :title="dialogTitle" width="400px">
            <el-form :model="formData" label-width="90px" class="form">
                <el-form-item label="工厂名称">
                    <el-input v-model="formData.name" placeholder="请输入工厂名称" />
                </el-form-item>
                <el-form-item label="工厂位置">
                    <el-input v-model="formData.location" placeholder="请输入工厂位置" />
                </el-form-item>
                <el-form-item label="工厂等级">
                    <el-input-number v-model="formData.level" :min="1" />
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="formData.description" placeholder="请输入描述" />
                </el-form-item>
            </el-form>

            <template #footer>
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSave">确定</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import api from '../api/reqIntercept'
const searchKey = ref('')
const factoryList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formData = reactive({
    id: null,
    name: '',
    location: '',
    level: 1,
    description: ''
})

// 获取列表
const fetchFactoryList = async () => {
    try {
        const res = await api.get('/factory/list')
        factoryList.value = res.data.data || []
    } catch (err) {
        if (!err.business) console.error('列表加载失败:', err)
    }
}

// 搜索
const handleSearch = async () => {
    const key = searchKey.value.trim()
    const url = key ? `/factory/list?key=${encodeURIComponent(key)}` : '/factory/list'
    const res = await api.get(url)
    factoryList.value = res.data.data || []
}

// 新增
const openAddDialog = () => {
    dialogTitle.value = '新增工厂'
    Object.assign(formData, { id: null, name: '', location: '', level: 1, description: '' })
    dialogVisible.value = true
}

// 编辑
const openEditDialog = (row) => {
    dialogTitle.value = '编辑工厂'
    Object.assign(formData, row)
    dialogVisible.value = true
}
// 保存
const handleSave = async () => {
    const payload = {
        name: formData.name,
        location: formData.location,
        level: formData.level,
        description: formData.description
    }

    try {
        if (formData.id) {
            await api.put('/factory', { id: formData.id, ...payload })
            ElMessage.success('工厂已更新')
        } else {
            await api.post('/factory', payload)
            ElMessage.success('工厂已新增')
        }
        dialogVisible.value = false
        fetchFactoryList()
    } catch (err) {
        if (!err.business) console.error('保存失败:', err)
    }
}

// 删除
const handleDelete = async (row) => {
    try {
        await ElMessageBox.confirm(`确认删除工厂「${row.name}」吗？`, '提示', { type: 'warning' })
        await api.delete(`/factory/${row.id}`)
        ElMessage.success('工厂已删除')
        fetchFactoryList()
    } catch (err) {
        if (!err.business && err !== 'cancel') console.error('删除失败:', err)
    }
}

onMounted(fetchFactoryList)
</script>

<style scoped>
.factory-manage {
    background: rgba(255, 255, 255, 0.02);
    padding: 20px;
    border-radius: 8px;
    color: #e6eef9;
    height: 100%;
}

.toolbar {
    margin-bottom: 15px;
}

.table-header {
    background: rgba(255, 255, 255, 0.08) !important;
    color: #cfe3ff;
}

.el-dialog {
    background-color: #f7f9fb;
    /* 浅灰背景，改善对比度 */
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.4);
}

.el-dialog__title {
    color: #333;
    font-weight: 600;
}

.form {
    padding-top: 10px;
}

/* 输入框整体浅色，细边框，暗主题下清晰 */
.form :deep(.el-input__wrapper),
.form :deep(.el-input-number__inner),
.form :deep(.el-textarea__inner) {
    background-color: #fff;
    color: #333;
    border: 1px solid #dcdfe6;
    border-radius: 6px;
    transition: all 0.2s;
}

.form :deep(.el-input__wrapper:hover),
.form :deep(.el-input-number__inner:hover),
.form :deep(.el-textarea__inner:hover) {
    border-color: #409eff;
}

.form :deep(.el-form-item__label) {
    color: #333;
}

.el-button--primary {
    background-color: #4b8bf4;
    border-color: #4b8bf4;
}

.el-button--primary:hover {
    background-color: #5c9cff;
    border-color: #5c9cff;
}

.toolbar {
    display: flex;
    align-items: center;
    margin-bottom: 18px;
    gap: 12px;
    justify-content: space-between;
}

.search-input {
    width: 360px;
}

.add-btn {
    transition: all 0.3s ease;
}

.add-btn:hover {
    transform: translateY(-1px);
    box-shadow: 0 2px 6px rgba(64, 158, 255, 0.2);
}
</style>
