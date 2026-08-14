package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("insert into user(username, password, phone) values(#{username}, #{password}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    @Select("select * from user where username = #{username}")
    User findByUsername(String username);
}
