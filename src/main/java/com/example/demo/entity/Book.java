package com.example.demo.entity;

import com.example.demo.validation.AddGroup;
import com.example.demo.validation.UpdateGroup;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;

@Data
public class Book implements Serializable {

    @NotNull(message = "id不能为空", groups = UpdateGroup.class)
    private Integer id;

    @NotBlank(message = "书名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String title;

    @NotBlank(message = "作者不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String author;

    @NotNull(message = "价格不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @DecimalMin(value = "0.0", message = "价格不能为负数", groups = {AddGroup.class, UpdateGroup.class})
    private double price;

    @NotNull(message = "库存不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "库存不能为负数", groups = {AddGroup.class, UpdateGroup.class})
    private Integer stock;
}