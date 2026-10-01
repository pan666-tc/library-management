<template>
  <div class="borrow-history">
    <h2>📖 我的借阅历史</h2>
    <el-table :data="records" stripe style="width: 100%">
      <el-table-column prop="id" label="记录ID" width="100" />
      <el-table-column prop="bookId" label="图书ID" width="100" />
      <el-table-column prop="borrowDate" label="借阅日期" width="180" />
      <el-table-column prop="returnDate" label="归还日期" width="180">
        <template #default="{ row }">
          <span v-if="row.returnDate">{{ row.returnDate }}</span>
          <el-tag v-else type="warning">未归还</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="120">
        <template #default="{ row }">
          <el-tag :type="row.returnDate ? 'success' : 'warning'">
            {{ row.returnDate ? '已归还' : '借阅中' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button
            v-if="!row.returnDate"
            size="small"
            type="primary"
            @click="handleReturn(row)"
          >
            还书
          </el-button>
          <span v-else style="color: #999">—</span>
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
      @size-change="loadHistory"
      @current-change="loadHistory"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getHistory, returnBook } from '../api/borrow'

const records = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadHistory = async () => {
  const res = await getHistory(pageNum.value, pageSize.value)
  records.value = res.data.list || []
  total.value = res.data.total || 0
}

const handleReturn = async (record) => {
  try {
    await ElMessageBox.confirm('确认归还这本书吗？', '还书确认', { type: 'info' })
    await returnBook(record.bookId)
    ElMessage.success('还书成功')
    loadHistory()
  } catch {}
}

onMounted(loadHistory)
</script>

<style scoped>
.borrow-history h2 {
  margin-bottom: 20px;
}
.pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>
