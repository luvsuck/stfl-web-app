<template>
    <div class="manage-chart">
        <div ref="chartRef" class="chart" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import api from '../api/reqIntercept'

const chartRef = ref(null)
let chartInstance = null

const loadData = async () => {
    try {
        const res = await api.get('/stock/report')
        const data = res.data.data || []

        const option = {
            title: {
                text: '商品库存与金额概览',
                left: 'center',
                textStyle: { color: '#e6eef9' }, top: 5
            },
            tooltip: { trigger: 'axis' },
            legend: {
                data: ['库存', '入库', '出库', '总金额'],
                textStyle: { color: '#cfe3ff' },
                top: 30
            },
            xAxis: {
                type: 'category',
                data: data.map(item => item.productName),
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
                    data: data.map(item => item.quantity),
                    itemStyle: { color: '#4b8bf4' }
                },
                {
                    name: '入库',
                    type: 'bar',
                    stack: '数量',
                    data: data.map(item => item.stockInQuantity),
                    itemStyle: { color: '#27c24c' }
                },
                {
                    name: '出库',
                    type: 'bar',
                    stack: '数量',
                    data: data.map(item => item.stockOutQuantity),
                    itemStyle: { color: '#f59c1a' }
                },
                {
                    name: '总金额',
                    type: 'line',
                    yAxisIndex: 1,
                    data: data.map(item => item.totalAmount),
                    itemStyle: { color: '#f5e63c' },
                    smooth: true
                }
            ]
        }

        chartInstance.setOption(option)
    } catch (e) {
        console.error('加载管理图表失败', e)
    }
}

onMounted(() => {
    chartInstance = echarts.init(chartRef.value)
    loadData()
    window.addEventListener('resize', () => chartInstance?.resize())
})
</script>

<style scoped>
.manage-chart {
    padding: 24px;
}

.chart {
    width: 100%;
    height: 500px;
    background-color: rgba(255, 255, 255, 0.02);
    border-radius: 8px;
}
</style>
