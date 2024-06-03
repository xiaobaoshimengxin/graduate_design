package com.java_write_exam.VO;

import java.time.LocalDate;
import lombok.Data;
@Data

public class drugperiodVO {
    private int id;
    private String drugName;
    private String barCode;
    private int num;
    private LocalDate effDate;

}
