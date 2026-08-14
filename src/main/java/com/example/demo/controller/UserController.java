package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.entity.Book;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.BookService;
import com.example.demo.validation.AddGroup;
import com.example.demo.validation.UpdateGroup;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Tag(name = "图书管理", description = "图书的增删改查接口")
public class UserController {

    @Resource
    private BookService bookService;

    @GetMapping("/list")
    @Operation(summary = "查询图书列表（分页）")
    public result<PageInfo<Book>> getbookall(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") int pageSize){
        PageInfo<Book> pageInfo = bookService.findall(pageNum, pageSize);
        return result.success(pageInfo);
    }

    @PostMapping("/add")
    @Operation(summary = "新增图书")
    public result<String> addbook(@Validated(AddGroup.class) @RequestBody Book book){
        bookService.addbook(book);
        return result.success("add success");
    }

    @PutMapping("/update")
    @Operation(summary = "修改图书信息")
    public result<String> updatebook(@Validated(UpdateGroup.class) @RequestBody Book book){
        bookService.updatebook(book);
        return result.success("update success");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除图书")
    public result<String> deletebook(@Parameter(description = "图书ID") @PathVariable("id") int id){
        bookService.deletebook(id);
        return result.success("delete success");
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询图书详情")
    public result<List<Book>> getbook(@Parameter(description = "图书ID") @PathVariable("id") int id){
        List<Book> listbook=bookService.findbook(id);
        if(listbook.isEmpty()){
            throw new BusinessException(404, "书籍不存在");
        }
        return result.success(listbook);
    }

    @GetMapping("/search")
    @Operation(summary = "按书名模糊搜索")
    public result<List<Book>> searchByTitle(@Parameter(description = "书名关键词") @RequestParam String title) {
        List<Book> book = bookService.searchbyTitle(title);
        return result.success(book);
    }
}
