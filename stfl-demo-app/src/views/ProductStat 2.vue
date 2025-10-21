<template>
  <div class="product-report">

    <el-table :data="reportList" border stripe style="width: 100%">
      <el-table-column prop="productId" label="商品ID" width="80" />
      <el-table-column prop="productName" label="商品名称" />
      <el-table-column prop="quantity" label="库存数量" width="120" />
      <el-table-column prop="totalAmount" label="库存总金额(¥)" width="140" />
      <el-table-column prop="stockInQuantity" label="入库数量" width="120" />
      <el-table-column prop="stockOutQuantity" label="出库数量" width="120" />
    </el-table>

    <div ref="chartRef" class="chart" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import api from '../api/reqIntercept'

const reportList = ref([])
const chartRef = ref(null)
let chartInstance = null

const loadReport = async () => {
  try {
    const res = await api.get('/stock/report')
    reportList.value = res.data.data || []
  } catch (e) {
    if (!e.business) console.error('加载商品统计失败', e)
  }
}

// 初始化图表
const initChart = () => {
  if (!chartRef.value) return
  chartInstance = echarts.init(chartRef.value)

  const option = {
    title: {
      text: '商品库存与金额概览',
      left: 'center',
      textStyle: { color: '#e6eef9' },
      top:5
    },
    tooltip: { trigger: 'axis' },
    legend: {
      data: ['库存', '入库', '出库', '总金额'],
      textStyle: { color: '#cfe3ff' },
      top: 30
    },
    xAxis: {
      type: 'category',
      data: reportList.value.map(item => item.productName),
      axisLabel: { color: '#cfe3ff', rotate: 30 }
    },
    yAxis: [
      {
        type: 'value',
        name: '数量',
        position: 'left',
        axisLabel: { color: '#cfe3ff' }
      },
      {
        type: 'value',
        name: '总金额(¥)',
        position: 'right',
        axisLabel: { color: '#cfe3ff' }
      }
    ],
    series: [
      {
        name: '库存',
        type: 'bar',
        stack: '数量',
        data: reportList.value.map(item => item.quantity),
        itemStyle: { color: '#4b8bf4' }
      },
      {
        name: '入库',
        type: 'bar',
        stack: '数量',
        data: reportList.value.map(item => item.stockInQuantity),
        itemStyle: { color: '#27c24c' }
      },
      {
        name: '出库',
        type: 'bar',
        stack: '数量',
        data: reportList.value.map(item => item.stockOutQuantity),
        itemStyle: { color: '#f59c1a' }
      },
      {
        name: '总金额',
        type: 'line',
        yAxisIndex: 1,
        data: reportList.value.map(item => item.totalAmount),
        itemStyle: { color: '#f5e63c' },
        smooth: true
      }
    ]
  }

  chartInstance.setOption(option)
}

// 监听数据更新刷新图表
watch(reportList, () => {
  if (chartInstance) initChart()
})

onMounted(async () => {
  await loadReport()
  initChart()
  window.addEventListener('resize', () => chartInstance?.resize())
})
</script>

<style scoped>
.product-report {
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.02);
  border-radius: 8px;
}

.el-table th {
  background: rgba(255, 255, 255, 0.08);
  color: #cfe3ff;
}

.chart {
  width: 100%;
  height: 400px;
  margin-top: 20px;
  background-color: rgba(255,255,255,0.02);
  border-radius: 8px;
}
</style>
