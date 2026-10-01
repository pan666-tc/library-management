<template>
  <div class="book-list">
    <h2>📚 图书列表</h2>
    <el-table :data="books" stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="书名" min-width="180" />
      <el-table-column prop="author" label="作者" width="120" />
      <el-table-column prop="price" label="价格" width="100">
        <template #default="{ row }">¥{{ row.price }}</template>
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="100">
        <template #default="{ row }">
          <el-tag :type="row.stock > 0 ? 'success' : 'danger'">
            {{ row.stock > 0 ? '有货(' + row.stock + ')' : '缺货' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="goDetail(row.id)">详情</el-button>
          <el-button
            size="small"
            type="success"
            :disabled="row.stock <= 0"
            @click="handleBorrow(row)"
          >
            借书
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      class="pagination"
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[10, 20, 50]"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="loadBooks"
      @current-change="loadBooks"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { findAll } from '../api/book'
import { borrowBook } from '../api/borrow'

const router = useRouter()
const books = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadBooks = async () => {
  const res = await findAll(pageNum.value, pageSize.value)
  books.value = res.data.list || []
  total.value = res.data.total || 0
}

const goDetail = (id) => {
  router.push('/books/' + id)
}

const handleBorrow = async (book) => {
  try {
    await ElMessageBox.confirm(`确认要借《${book.title}》吗？`, '借书确认', { type: 'info' })
    await borrowBook(book.id)
    ElMessage.success('借书成功')
    loadBooks()
  } catch {}
}

onMounted(loadBooks)
</script>

<style scoped>
.book-list h2 {
  margin-bottom: 20px;
}
.pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>
