package com.java_write_exam.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
@AllArgsConstructor
public class DateAndOdd {
    private String date;
    private String odd;

    public static DateAndOdd getDateAndOdd(String pattern){
        // 采购时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String date = dateFormat.format(new Date());
        // 采购单号
        String bD = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        String odd = bD+String.format("%02d",randomNumber);
        Map<String, String> mr = new HashMap<>();
        mr.put("today", date);
        mr.put("odd", odd);
        return new DateAndOdd(date=date, odd=odd);
    }

}
