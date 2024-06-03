package com.java_write_exam.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleNoDrugVO {
    private LocalDateTime saleDate;
    private Integer employeeId;
    private Integer customerId;
    private String saleId;
    private String payType;
}
