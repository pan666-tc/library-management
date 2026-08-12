package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.entity.BorrowRecord;
import com.example.demo.service.BorrowService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    @PostMapping("/borrow")
    public result<String> borrow(@RequestParam Integer userId, @RequestParam Integer bookId) {
        borrowService.borrow(userId, bookId);
        return result.success("借书成功");
    }

    @PostMapping("/return")
    public result<String> returnBook(@RequestParam Integer userId, @RequestParam Integer bookId) {
        borrowService.returnBook(userId, bookId);
        return result.success("还书成功");
    }

    @GetMapping("/history")
    public result<PageInfo<BorrowRecord>> getBorrowHistory(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<BorrowRecord> pageInfo = borrowService.getBorrowHistory(userId, pageNum, pageSize);
        return result.success(pageInfo);
    }
}