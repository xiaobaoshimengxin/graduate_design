package com.java_write_exam.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class drugListVO {
    private Integer id;
    private String drugName;
    private Float purPrice;     //进价
    private Float price;
    private String specs;
    private Integer num;
    private LocalDate dateOfManufacture;
    private LocalDate effectiveDate;
}
