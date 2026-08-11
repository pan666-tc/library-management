package com.example.demo.mapper;

import com.example.demo.entity.BorrowRecord;
import org.apache.ibatis.annotations.*;

public interface BorrowRecordMapper {

    @Insert("insert into borrow_record(user_id, book_id, status) values(#{userId}, #{bookId}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(BorrowRecord record);

    @Update("update borrow_record set status = 1, return_date = NOW() where id = #{id}")
    void updateStatusToReturned(Integer id);

    @Select("select * from borrow_record where user_id = #{userId} and book_id = #{bookId} and status = 0")
    BorrowRecord findActiveBorrow(Integer userId, Integer bookId);

    @Select("select * from borrow_record where id = #{id}")
    BorrowRecord findById(Integer id);
}
