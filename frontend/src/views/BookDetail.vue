<template>
  <div class="book-detail">
    <el-page-header @back="$router.back()" content="返回列表" style="margin-bottom: 20px" />
    <el-card v-if="book">
      <div class="detail-content">
        <div class="book-icon">📖</div>
        <div class="info">
          <h1>{{ book.title }}</h1>
          <p><strong>ID：</strong>{{ book.id }}</p>
          <p><strong>作者：</strong>{{ book.author }}</p>
          <p><strong>价格：</strong>¥{{ book.price }}</p>
          <p>
            <strong>库存：</strong>
            <el-tag :type="book.stock > 0 ? 'success' : 'danger'">
              {{ book.stock > 0 ? '有货 (' + book.stock + ')' : '缺货' }}
            </el-tag>
          </p>
          <div class="actions">
            <el-button
              type="success"
              size="large"
              :disabled="book.stock <= 0"
              @click="handleBorrow"
            >
              立即借书
            </el-button>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { findBook } from '../api/book'
import { borrowBook } from '../api/borrow'

const route = useRoute()
const book = ref(null)

const loadBook = async () => {
  const res = await findBook(route.params.id)
  book.value = res.data?.[0] || null
}

const handleBorrow = async () => {
  try {
    await ElMessageBox.confirm(`确认要借《${book.value.title}》吗？`, '借书确认', { type: 'info' })
    await borrowBook(book.value.id)
    ElMessage.success('借书成功')
    loadBook()
  } catch {}
}

onMounted(loadBook)
</script>

<style scoped>
.detail-content {
  display: flex;
  gap: 40px;
  align-items: flex-start;
}
.book-icon {
  font-size: 100px;
}
.info h1 {
  margin: 0 0 20px;
}
.info p {
  font-size: 16px;
  margin: 10px 0;
}
.actions {
  margin-top: 30px;
}
</style>
