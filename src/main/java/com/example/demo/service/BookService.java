package com.example.demo.service;


import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookmapper;

    public List<Book> findall(){
        return bookmapper.findall();
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
