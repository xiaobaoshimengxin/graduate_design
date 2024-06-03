package com.java_write_exam.VO;

import com.java_write_exam.DTO.DrugInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurNoDrugVO {
    private String purId;
    private LocalDate buyDate;
    private String supplierName;
}
