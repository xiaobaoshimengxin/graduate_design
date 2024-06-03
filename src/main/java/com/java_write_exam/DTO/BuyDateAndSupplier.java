package com.java_write_exam.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyDateAndSupplier {
    private Integer page;            //页码
    private Integer pageSize;       //页面大小
    private LocalDate purDate;      //采购日期
    private String supplierName;   //供应商名字
}
