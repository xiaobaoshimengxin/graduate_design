package com.java_write_exam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleExcelOut {
    private LocalDateTime saleDate; // 售卖时间
    private String employee; // 员工  查
    private String saleId; // 单号
    private String customer; // 顾客 查
    private String drugName; // 药品      查
    private Integer drugCount; // 数量
    private Float price; // 单价
    private String notes; // 备注
    private Float totalPrice; // 总额
}
