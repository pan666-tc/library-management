package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.entity.BorrowRecord;
import com.example.demo.service.BorrowService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
@Tag(name = "借阅管理", description = "借书、还书、借阅历史接口")
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    @PostMapping("/borrow")
    @Operation(summary = "借书", description = "userId 从 JWT token 中提取，前端只需传 bookId")
    public result<String> borrow(@Parameter(description = "图书ID") @RequestParam Integer bookId) {
        Integer userId = getCurrentUserId();
        borrowService.borrow(userId, bookId);
        return result.success("借书成功");
    }

    @PostMapping("/return")
    @Operation(summary = "还书", description = "userId 从 JWT token 中提取")
    public result<String> returnBook(@Parameter(description = "图书ID") @RequestParam Integer bookId) {
        Integer userId = getCurrentUserId();
        borrowService.returnBook(userId, bookId);
        return result.success("还书成功");
    }

    @GetMapping("/history")
    @Operation(summary = "查询借阅历史（分页）")
    public result<PageInfo<BorrowRecord>> getBorrowHistory(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") int pageSize) {
        Integer userId = getCurrentUserId();
        PageInfo<BorrowRecord> pageInfo = borrowService.getBorrowHistory(userId, pageNum, pageSize);
        return result.success(pageInfo);
    }

    /**
     * 从 SecurityContext 中获取当前登录用户的 userId
     */
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Integer) authentication.getPrincipal();
    }
}
