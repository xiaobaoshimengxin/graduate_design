package com.java_write_exam.VO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SaleMoneyVO {
    private LocalDate saleDate;
    private float saleAmount;
}
