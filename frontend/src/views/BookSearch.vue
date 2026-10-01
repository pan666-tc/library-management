<template>
  <div class="book-search">
    <h2>🔍 图书搜索</h2>
    <el-input
      v-model="keyword"
      placeholder="输入书名搜索..."
      size="large"
      style="width: 400px"
      @keyup.enter="handleSearch"
    >
      <template #append>
        <el-button @click="handleSearch">搜索</el-button>
      </template>
    </el-input>

    <el-table v-if="results.length" :data="results" stripe style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="书名" min-width="180" />
      <el-table-column prop="author" label="作者" width="120" />
      <el-table-column prop="price" label="价格" width="100">
        <template #default="{ row }">¥{{ row.price }}</template>
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="100">
        <template #default="{ row }">
          <el-tag :type="row.stock > 0 ? 'success' : 'danger'">
            {{ row.stock > 0 ? '有货' : '缺货' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="goDetail(row.id)">详情</el-button>
          <el-button size="small" type="success" :disabled="row.stock <= 0" @click="handleBorrow(row)">借书</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-empty v-else-if="searched" description="没有找到匹配的图书" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { searchByTitle } from '../api/book'
import { borrowBook } from '../api/borrow'

const router = useRouter()
const keyword = ref('')
const results = ref([])
const searched = ref(false)

const handleSearch = async () => {
  if (!keyword.value.trim()) {
    ElMessage.warning('请输入搜索关键词')
    return
  }
  searched.value = true
  const res = await searchByTitle(keyword.value.trim())
  results.value = res.data || []
}

const goDetail = (id) => {
  router.push('/books/' + id)
}

const handleBorrow = async (book) => {
  try {
    await ElMessageBox.confirm(`确认要借《${book.title}》吗？`, '借书确认', { type: 'info' })
    await borrowBook(book.id)
    ElMessage.success('借书成功')
  } catch {}
}
</script>

<style scoped>
.book-search h2 {
  margin-bottom: 20px;
}
</style>
