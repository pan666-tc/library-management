package com.example.demo.service;


import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookmapper;

    /**
     * 查询所有图书（分页），结果缓存 30 分钟
     * 缓存 key: bookList::1_10 (第1页, 每页10条)
     */
    @Cacheable(value = "bookList", key = "#pageNum + '_' + #pageSize")
    public PageInfo<Book> findall(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Book> bookList = bookmapper.findall();
        return new PageInfo<>(bookList);
    }

    /**
     * 根据ID查询单本图书，结果缓存 30 分钟
     * 缓存 key: books::1
     */
    @Cacheable(value = "books", key = "#id")
    public List<Book> findbook(int id){
        return bookmapper.findbook(id);
    }

    /**
     * 新增图书：清除所有图书相关缓存
     * 保证新增后，列表缓存和单本缓存不会返回旧数据
     */
    @CacheEvict(value = {"books", "bookList", "bookSearch"}, allEntries = true)
    public void addbook(Book book){
        bookmapper.addbook(book);
    }

    /**
     * 修改图书：清除所有图书相关缓存
     */
    @CacheEvict(value = {"books", "bookList", "bookSearch"}, allEntries = true)
    public void updatebook(Book book){
        bookmapper.updatebook(book);
    }

    /**
     * 删除图书：清除所有图书相关缓存
     */
    @CacheEvict(value = {"books", "bookList", "bookSearch"}, allEntries = true)
    public void deletebook(int id){
        bookmapper.deletebook(id);
    }

    /**
     * 按书名模糊搜索，结果缓存 30 分钟
     * 缓存 key: bookSearch::Java
     */
    @Cacheable(value = "bookSearch", key = "#title")
    public List<Book> searchbyTitle(String title){
        return bookmapper.searchbyTitle(title);
    }

}
