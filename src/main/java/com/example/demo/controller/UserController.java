package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class UserController {

    @Resource
    private BookService bookService;

    @GetMapping("/list")
    public result<List<Book>> getbookall(){
        List<Book> bookList=bookService.findall();
        return result.success(bookList);
    }
    @PostMapping("/add")
    public result<String> addbook(@RequestBody Book book){
        bookService.addbook(book);
        return result.success("add success");
    }
    @PutMapping("/update")
    public result<String> updatebook(@RequestBody Book book){
        bookService.updatebook(book);
        return result.success("update success");
    }
    @DeleteMapping("/{id}")
    public result<String> deletebook(@PathVariable("id") int id){
        bookService.deletebook(id);
        return result.success("delete success");
    }
    @GetMapping("/{id}")
    public result<List<Book>> getbook(@PathVariable("id") int id){
        List<Book> listbook=bookService.findbook(id);
        if(listbook.isEmpty()){
            return result.error("书籍不存在");
        }
        return result.success(listbook);
    }
    @GetMapping("/search")
    public result<List<Book>> searchByTitle(@RequestParam String title) {
        List<Book> book = bookService.searchbyTitle(title);
        return result.success(book);
    }
}
