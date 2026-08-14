package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.entity.BorrowRecord;
import com.example.demo.service.BorrowService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    /**
     * 借书：userId 从 JWT token 中提取，前端只需传 bookId
     */
    @PostMapping("/borrow")
    public result<String> borrow(@RequestParam Integer bookId) {
        Integer userId = getCurrentUserId();
        borrowService.borrow(userId, bookId);
        return result.success("借书成功");
    }

    /**
     * 还书：userId 从 JWT token 中提取
     */
    @PostMapping("/return")
    public result<String> returnBook(@RequestParam Integer bookId) {
        Integer userId = getCurrentUserId();
        borrowService.returnBook(userId, bookId);
        return result.success("还书成功");
    }

    /**
     * 借阅历史：自动查询当前登录用户的借阅记录
     */
    @GetMapping("/history")
    public result<PageInfo<BorrowRecord>> getBorrowHistory(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Integer userId = getCurrentUserId();
        PageInfo<BorrowRecord> pageInfo = borrowService.getBorrowHistory(userId, pageNum, pageSize);
        return result.success(pageInfo);
    }

    /**
     * 从 SecurityContext 中获取当前登录用户的 userId
     * userId 在 JwtAuthenticationFilter 中已存入 Authentication 的 principal
     */
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Integer) authentication.getPrincipal();
    }
}
