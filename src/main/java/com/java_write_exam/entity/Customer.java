package com.java_write_exam.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {
    private Integer id;
    private String phone;
    private String name;
    private float wallet;
    private Integer score;
    private LocalDate createTime;
}
