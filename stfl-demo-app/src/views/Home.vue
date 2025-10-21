<template>
  <div class="home-layout">
    <!-- 顶部导航 -->
    <header class="top-nav">
      <div class="logo">stfl-app</div>
      <el-menu mode="horizontal" :default-active="activeNav" class="nav-menu">
        <el-menu-item index="home" @click="goHome">首页</el-menu-item>
        <el-menu-item index="manage" @click="goManage">管理</el-menu-item>
      </el-menu>
    </header>

    <div class="main-container">
      <!-- 左侧侧边栏 -->
      <aside class="side-bar">
        <el-menu class="side-menu" default-active="factory-manage" :collapse="collapsed" background-color="transparent"
          text-color="#cfe3ff" active-text-color="#fff" router>
          <el-sub-menu index="factory">
            <template #title>工厂</template>
            <el-menu-item index="/home/factory/manage">工厂管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="product">
            <template #title>商品</template>
            <el-menu-item index="/home/product/manage">商品管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="stat">
            <template #title>统计</template>
            <el-menu-item index="/home/stat/product">商品统计</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </aside>

      <!-- 主内容 -->
      <section class="content-area">
        <router-view />
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeNav = ref('home')
const collapsed = ref(false)

const goHome = () => {
  activeNav.value = 'home'
  router.push('/home/factory/manage')
}

const goManage = () => {
  activeNav.value = 'manage'
  router.push('/manage')
}

</script>

<style scoped>
.home-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background: linear-gradient(135deg, #0f172a 0%, #0b3d91 60%);
  color: #e6eef9;
}

/* 顶部导航 */
.top-nav {
  display: flex;
  align-items: center;
  height: 60px;
  padding: 0 24px;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(6px);
  box-shadow: 0 4px 12px rgba(2, 6, 23, 0.5);
}

.logo {
  font-size: 18px;
  font-weight: 700;
  color: #fff;
  margin-right: 40px;
}

.nav-menu {
  background: transparent;
  color: #cfe3ff;
  flex: 1;
}

.nav-menu .el-menu-item {
  font-weight: 500;
  color: #cfe3ff;
}

.nav-menu .el-menu-item.is-active {
  color: #fff;
  border-bottom: 2px solid #fff;
}

/* 主体布局 */
.main-container {
  display: flex;
  flex: 1;
  overflow: hidden;
}

/* 左侧菜单 */
.side-bar {
  width: 200px;
  background: rgba(255, 255, 255, 0.02);
  backdrop-filter: blur(6px);
  padding-top: 20px;
}

.side-menu {
  border-right: none;
}

.side-menu .el-sub-menu__title {
  color: #cfe3ff;
}

.side-menu .el-menu-item.is-active,
.side-menu .el-sub-menu.is-active .el-sub-menu__title {
  color: #fff;
}

/* 内容区域 */
.content-area {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}
</style>
