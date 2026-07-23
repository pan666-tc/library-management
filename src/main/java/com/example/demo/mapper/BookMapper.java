package com.example.demo.mapper;
import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {
    @Select("select * from book")
    List<Book> findall();

    @Insert("insert into book(id,title,author,price,stock) values (#{id},#{title},#{author},#{price},#{stock})")
    void addbook(Book book);

    @Update("update book set title=#{title},author=#{author},price=#{price},stock=#{stock} where id=#{id}")
    void updatebook(Book book);

    @Delete("delete from book where id=#{id}")
    void deletebook(int id);

    @Select("select * from book where id=#{id}")
    List<Book> findbook(int id);

    @Select("select * from book where title like concat('%',#{title},'%')")
    List<Book> searchbyTitle(String title);
}