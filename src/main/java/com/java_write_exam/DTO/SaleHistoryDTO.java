package com.java_write_exam.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SaleHistoryDTO {
    private Integer page;
    private Integer pageSize;
    private LocalDate saleDate;
    private String drugName;
}
