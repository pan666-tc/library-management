package com.example.demo.service;


import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookmapper;

    public PageInfo<Book> findall(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Book> bookList = bookmapper.findall();
        return new PageInfo<>(bookList);
    }

    @Cacheable(value="books",key="#id")
    public List<Book> findbook(int id){
        return bookmapper.findbook(id);
    }

    public void addbook(Book book){
        bookmapper.addbook(book);
    }

    public void updatebook(Book book){
        bookmapper.updatebook(book);
    }

    public void deletebook(int id){
        bookmapper.deletebook(id);
    }

    public List<Book> searchbyTitle(String title){
        return bookmapper.searchbyTitle(title);
    }

}