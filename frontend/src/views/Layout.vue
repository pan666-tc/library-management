<template>
  <el-container class="layout">
    <el-header class="header">
      <div class="logo">📚 图书管理系统</div>
      <el-menu
        mode="horizontal"
        :default-active="$route.path"
        class="nav-menu"
        @select="handleSelect"
      >
        <el-menu-item index="/books">图书列表</el-menu-item>
        <el-menu-item index="/search">图书搜索</el-menu-item>
        <el-menu-item index="/history">借阅历史</el-menu-item>
      </el-menu>
      <div class="user-area">
        <span>{{ username }}</span>
        <el-button type="danger" size="small" @click="logout">退出登录</el-button>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const username = ref(localStorage.getItem('username') || '用户')

const handleSelect = (path) => {
  router.push(path)
}

const logout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', { type: 'warning' })
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    ElMessage.success('已退出登录')
    router.push('/login')
  } catch {}
}
</script>

<style scoped>
.layout {
  height: 100vh;
}
.header {
  display: flex;
  align-items: center;
  background: #409eff;
  color: white;
  padding: 0 20px;
}
.logo {
  font-size: 18px;
  font-weight: bold;
  margin-right: 40px;
}
.nav-menu {
  flex: 1;
  background: transparent;
  border-bottom: none;
}
.nav-menu .el-menu-item {
  color: white;
}
.nav-menu .el-menu-item:hover {
  background: rgba(255, 255, 255, 0.2);
}
.nav-menu .el-menu-item.is-active {
  background: rgba(255, 255, 255, 0.3);
}
.user-area {
  display: flex;
  align-items: center;
  gap: 15px;
}
.main {
  background: #f5f7fa;
}
</style>
