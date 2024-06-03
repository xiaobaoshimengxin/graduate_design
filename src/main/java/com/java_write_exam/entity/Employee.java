package com.java_write_exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id; // 主键
    private String name;    // 员工名字
    private LocalDate birth;  // 出生日期
    private String account; // 账号/电话
    private String password;    // 密码
    private String address; // 住址
    private Short statue;   // 状态：1为正常
    private String power;    // 权限3~1
    private LocalDate createTime;   // 创建时间
}
