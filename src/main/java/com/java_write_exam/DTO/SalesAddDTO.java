package com.java_write_exam.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SalesAddDTO {
    private Integer id; // 顾客id
    private Integer empId;   // 员工id
    private Float priceAll;
    private Integer useScore;   // 使用积分
    private List<DrugSaleDTO> drugList;
    private Integer payType;
    private float wallet;
}
