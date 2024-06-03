package com.java_write_exam.DTO;

import com.java_write_exam.entity.DrugAddInfo;
import lombok.Data;

import java.util.List;

@Data
public class PurAddFormDTO {
    private Integer supplierId;
    private List<DrugAddInfo> drugList;
}
