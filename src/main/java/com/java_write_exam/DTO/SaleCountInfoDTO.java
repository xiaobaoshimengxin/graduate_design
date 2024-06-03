package com.java_write_exam.DTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SaleCountInfoDTO {
    private LocalDate start_term;
    private LocalDate last_term;
    //private int opt;
}
