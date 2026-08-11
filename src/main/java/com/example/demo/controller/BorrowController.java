package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.service.BorrowService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    /**
     * 借书接口
     * GET /borrow/borrow?userId=1&bookId=1
     */
    @PostMapping("/borrow")
    public result<String> borrow(@RequestParam Integer userId, @RequestParam Integer bookId) {
        borrowService.borrow(userId, bookId);
        return result.success("借书成功");
    }

    /**
     * 还书接口
     * POST /borrow/return?userId=1&bookId=1
     */
    @PostMapping("/return")
    public result<String> returnBook(@RequestParam Integer userId, @RequestParam Integer bookId) {
        borrowService.returnBook(userId, bookId);
        return result.success("还书成功");
    }
}
