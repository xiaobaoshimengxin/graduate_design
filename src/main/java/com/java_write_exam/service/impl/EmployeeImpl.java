package com.java_write_exam.service.impl;

import com.java_write_exam.DTO.PasswordDTO;
import com.java_write_exam.DTO.UserClaim;
import com.java_write_exam.Exce.GlobalExceptionHandler;
import com.java_write_exam.entity.Employee;
import com.java_write_exam.mapper.EmployeeMapper;
import com.java_write_exam.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper empMapper;
    @Override
    //登录
    public Employee login(Employee employee) {
        // 通过账号/密码查询数据库
        return empMapper.getByAccount(employee);
    }

    @Override
    //列出所有员工
    public List<Employee> empList() {
        List<Employee> el = empMapper.listAll();
        for (Employee e : el){
            e.setPower(e.getPower().equals("1")?"员工":(e.getPower().equals("2")?"主管":"管理员"));
        }
        return el;
    }

    @Override
    //添加员工
    public void empAdd(Employee employee) {
        empMapper.insert(employee);
    }

    @Override
    //员工信息更新
    public void empUpdate(Employee employee) {
        empMapper.update(employee);
    }

    @Override
    public Boolean resetPassword(PasswordDTO passwordDTO) {
        String oldPassword = empMapper.getPasswordById(UserClaim.id);
        if (passwordDTO.getOldPassword().equals(oldPassword)){
            empMapper.resetPassword(passwordDTO.getNewPassword(), UserClaim.id);
            return true;
        }else{
            return false;
        }
    }
}
