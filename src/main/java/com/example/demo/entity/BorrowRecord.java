package com.example.demo.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BorrowRecord implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    /** 状态：0=借阅中，1=已归还 */
    private Integer status;
}
