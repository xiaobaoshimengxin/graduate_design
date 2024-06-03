package com.java_write_exam.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DrugAddInfo {
    private Integer id; // id
    private String drugBarCode; // 条形码
    private String drugName;    // 药品名称
    private float purPrice;     //进价
    private Float price;    // 售价
    private String specs;   // 规格
    private String category;    // 剂型
    private Integer drugCount;  // 采购数量
    private LocalDate dateOfManufacture;// 生产日期
    private LocalDate effectiveDate;    // 有效日期
    private Integer supplierId;
}
