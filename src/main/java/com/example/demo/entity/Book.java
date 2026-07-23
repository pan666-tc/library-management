package com.example.demo.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class Book implements Serializable {
    private Integer id;
    private String title;
    private String author;
    private double price;
    private Integer stock;
}