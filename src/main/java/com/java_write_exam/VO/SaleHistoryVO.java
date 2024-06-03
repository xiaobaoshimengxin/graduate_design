package com.java_write_exam.VO;

import com.java_write_exam.DTO.DrugInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleHistoryVO {
    private LocalDateTime saleDate;
    private String employee;
    private String customer;
    private String saleId;
    private List<DrugInfo> drugList;
    private String drugs;
    private String payType;
}
