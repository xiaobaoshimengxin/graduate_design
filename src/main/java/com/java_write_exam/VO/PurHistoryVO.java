package com.java_write_exam.VO;

import com.java_write_exam.DTO.DrugInfo;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PurHistoryVO {
    private String purId;
    private LocalDate buyDate;
    private List<DrugInfo> drugInfo;
    private String drugNames;
    private String supplierName;
}
