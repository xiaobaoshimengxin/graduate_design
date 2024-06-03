package com.java_write_exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private Integer id;
    private String name;
    private LocalDate createTime;
    private String address;
    private String contactNumber;
    private Integer statue;
}
