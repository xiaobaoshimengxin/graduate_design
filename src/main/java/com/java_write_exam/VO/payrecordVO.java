package com.java_write_exam.VO;
import lombok.Data;
@Data
public class payrecordVO {
    private int payId;
    private int customerId;
    private String customerName;
    private float payAmount;
    private String payDate;
    private int employeeId;
}
