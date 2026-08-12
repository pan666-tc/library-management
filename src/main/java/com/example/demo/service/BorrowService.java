package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.BorrowRecord;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.BorrowRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BorrowService {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BorrowRecordMapper borrowRecordMapper;

    /**
     * 借书（事务）
     * 两步操作：1.扣库存  2.插入借阅记录
     * 如果第2步失败，第1步自动回滚
     */
    @Transactional
    public void borrow(Integer userId, Integer bookId) {
        // 1. 校验图书是否存在
        List<Book> books = bookMapper.findbook(bookId);
        if (books.isEmpty()) {
            throw new BusinessException(404, "书籍不存在");
        }

        // 2. 扣减库存（stock > 0 才能扣，返回受影响行数）
        int rows = bookMapper.decreaseStock(bookId);
        if (rows == 0) {
            throw new BusinessException(400, "库存不足，借阅失败");
        }

        // 3. 插入借阅记录
        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        borrowRecordMapper.insert(record);

        // 如果这一步抛异常，前面的扣库存会自动回滚 ← 这就是事务的作用
    }

    /**
     * 还书（事务）
     * 两步操作：1.更新借阅记录状态为已归还  2.加回库存
     */
    @Transactional
    public void returnBook(Integer userId, Integer bookId) {
        // 1. 查找未归还的借阅记录
        BorrowRecord record = borrowRecordMapper.findActiveBorrow(userId, bookId);
        if (record == null) {
            throw new BusinessException(404, "未找到借阅记录，或已归还");
        }

        // 2. 更新借阅记录状态为已归还
        borrowRecordMapper.updateStatusToReturned(record.getId());

        // 3. 加回库存
        bookMapper.increaseStock(bookId);

        // 如果这一步抛异常，前面的更新记录会自动回滚 ← 事务保证数据一致
    }

    /**
     * 分页查询某读者的借阅历史
     */
    public PageInfo<BorrowRecord> getBorrowHistory(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BorrowRecord> list = borrowRecordMapper.findByUserId(userId);
        return new PageInfo<>(list);
    }
}
