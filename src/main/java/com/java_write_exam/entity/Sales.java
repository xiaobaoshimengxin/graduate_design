package com.java_write_exam.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sales {
    private String saleDate;
    private Integer employeeId;
    private Integer drugId;
    private String drugName;
    private Float price;
    private Integer drugCount;
    private Integer customerId;
    private String saleId;
    private String notes;
    private int payType;
}
