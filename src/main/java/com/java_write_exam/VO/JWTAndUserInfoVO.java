package com.java_write_exam.VO;

import lombok.Data;

@Data
public class JWTAndUserInfoVO {
    private String jwt;
    private Integer id;
    private String name;
    private String power;
}
