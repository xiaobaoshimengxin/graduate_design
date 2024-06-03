package com.java_write_exam.DTO;

import lombok.Data;

@Data
public class DrugSaleDTO {
    private String drugName;
    private Integer id;
    private Integer saleCount;
    private Float price;
}
