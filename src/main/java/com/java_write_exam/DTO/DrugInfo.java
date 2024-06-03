package com.java_write_exam.DTO;

import lombok.Data;

@Data
public class DrugInfo {
    private Integer drugId;
    private String drugName;
    private Integer drugCount;
    private Float price;
    private int customerId;
}
