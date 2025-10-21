<template>
  <div class="product-manage">
    <!-- 工具栏 -->
    <div class="toolbar">
      <el-input
        v-model="searchKey"
        placeholder="🔍 输入关键字搜索商品"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      >
        <template #append>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </template>
      </el-input>

      <el-button type="success" plain class="add-btn" @click="openAddDialog">
        <i class="el-icon-plus"></i> 新增商品
      </el-button>
    </div>

    <!-- 商品表格 -->
    <el-table :data="productList" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="商品名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="specModel" label="规格型号" />
      <el-table-column prop="quantity" label="库存数量" width="120" />
      <el-table-column prop="price" label="单价(¥)" width="120" />
      <el-table-column label="操作" width="260">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="openStockInDialog(row)">入库</el-button>
          <el-button size="small" type="warning" @click="openStockOutDialog(row)">出库</el-button>
          <el-button size="small" type="danger" @click="deleteProduct(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增商品弹窗 -->
    <el-dialog v-model="addDialogVisible" title="新增商品" width="500px">
      <el-form :model="form" label-width="90px" class="form">
        <el-form-item label="商品名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description" />
        </el-form-item>
        <el-form-item label="规格型号">
          <el-input v-model="form.specModel" />
        </el-form-item>
        <el-form-item label="数量">
          <el-input-number v-model="form.quantity" :min="0" />
        </el-form-item>
        <el-form-item label="单价(¥)">
          <el-input-number v-model="form.price" :min="0" :step="0.1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 入库/出库弹窗 -->
    <el-dialog v-model="stockDialogVisible" :title="stockDialogTitle" width="400px">
      <el-form :model="stockForm" label-width="90px">
        <el-form-item label="数量">
          <el-input-number v-model="stockForm.quantity" :min="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="stockDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStock">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api/reqIntercept'

const productList = ref([])
const searchKey = ref('')

// 新增
const addDialogVisible = ref(false)
const form = ref({
  name: '',
  description: '',
  specModel: '',
  quantity: 0,
  price: 0
})

// 入库/出库
const stockDialogVisible = ref(false)
const stockDialogTitle = ref('')
const stockForm = ref({ productId: null, quantity: 1 })
const stockType = ref('') // 'in' | 'out'

// 获取商品列表
const loadProductList = async () => {
  const key = searchKey.value.trim()
  const url = key ? `/product/list?key=${encodeURIComponent(key)}` : '/product/list'
  const res = await api.get(url)
  productList.value = res.data.data || []
}

// 搜索
const handleSearch = async () => {
  await loadProductList()
}

// 新增商品
const openAddDialog = () => {
  addDialogVisible.value = true
  form.value = { name: '', description: '', specModel: '', quantity: 0, price: 0 }
}

const submitForm = async () => {
  try {
    await api.post('/product', form.value)
    ElMessage.success('新增成功')
    addDialogVisible.value = false
    await loadProductList()
  } catch (e) {
    if (!e.business) ElMessage.error('新增失败')
  }
}

// 删除商品
const deleteProduct = async (id) => {
  await ElMessageBox.confirm('确认删除该商品吗？', '提示', { type: 'warning' })
  await api.delete(`/product/${id}`)
  ElMessage.success('删除成功')
  await loadProductList()
}

// 入库 / 出库
const openStockInDialog = (row) => {
  stockDialogTitle.value = `商品入库 - ${row.name}`
  stockForm.value = { productId: row.id, quantity: 1 }
  stockType.value = 'in'
  stockDialogVisible.value = true
}

const openStockOutDialog = (row) => {
  stockDialogTitle.value = `商品出库 - ${row.name}`
  stockForm.value = { productId: row.id, quantity: 1 }
  stockType.value = 'out'
  stockDialogVisible.value = true
}

const submitStock = async () => {
  try {
    const url = stockType.value === 'in' ? '/product/stock/in' : '/product/stock/out'
    await api.post(url, stockForm.value)
    ElMessage.success(stockType.value === 'in' ? '入库成功' : '出库成功')
    stockDialogVisible.value = false
    await loadProductList()
  } catch (e) {
    if (!e.business) ElMessage.error('操作失败')
  }
}

onMounted(loadProductList)
</script>

<style scoped>
.product-manage {
  padding: 20px;
}
.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 18px;
  gap: 12px;
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
