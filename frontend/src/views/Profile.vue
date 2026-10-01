<template>
  <div class="profile">
    <el-card>
      <h2>👤 个人中心</h2>
      <el-descriptions :column="1" border style="max-width: 400px; margin-top: 20px">
        <el-descriptions-item label="用户名">{{ username }}</el-descriptions-item>
        <el-descriptions-item label="登录状态">
          <el-tag type="success">已登录</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="Token">
          <span style="color: #999; font-size: 12px">已存储（{{ token.length }}位）</span>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const username = ref(localStorage.getItem('username') || '未知用户')
const token = ref(localStorage.getItem('token') || '')
</script>
