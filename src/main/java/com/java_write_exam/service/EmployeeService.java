package com.java_write_exam.service;

import com.java_write_exam.DTO.PasswordDTO;
import com.java_write_exam.entity.Employee;

import java.util.List;

public interface EmployeeService {
    /**
     * 根据账号密码查询数据
     * @param employee
     * @return
     */
    Employee login(Employee employee);

    List<Employee> empList();

    void empAdd(Employee employee);

    void empUpdate(Employee employee);

    Boolean resetPassword(PasswordDTO passwordDTO);
}
