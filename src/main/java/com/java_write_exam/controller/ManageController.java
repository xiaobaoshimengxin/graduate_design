package com.java_write_exam.controller;

import com.java_write_exam.DTO.CusScoreOpDTO;
import com.java_write_exam.DTO.CusWalletOpDTO;
import com.java_write_exam.VO.payrecordVO;
import com.java_write_exam.entity.Customer;
import com.java_write_exam.entity.Employee;
import com.java_write_exam.entity.Supplier;
import com.java_write_exam.service.CustomerService;
import com.java_write_exam.service.EmployeeService;
import com.java_write_exam.service.SupplierService;
import com.java_write_exam.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
@Slf4j
public class ManageController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/suplist")
    public Result<List<Supplier>> supList(String supWord){
        log.info("获取供应商信息:{}",supWord);
        List<Supplier> supplierList = supplierService.supList(supWord);
        return Result.success(supplierList);
    }

    @PostMapping("/supplier")
    public Result supAdd(@RequestBody Supplier supplier){
        log.info("添加供应商:{}",supplier);
        supplierService.supAdd(supplier);
        return Result.success();
    }

    @PutMapping("/supplier")
    public Result supUpdate(@RequestBody Supplier supplier){
        log.info("修改供应商信息:{}",supplier);
        supplierService.supUpdate(supplier);
        return Result.success();
    }

    @GetMapping("/employee")
    public Result<List<Employee>> employeeList(){
        log.info("查询员工信息");
        List<Employee> empList = employeeService.empList();
        return Result.success(empList);
    }

    @PostMapping("/employee")
    public Result empAdd(@RequestBody Employee employee){
        log.info("添加新员工：{}", employee);
        employeeService.empAdd(employee);
        return Result.success();
    }
    @PutMapping ("/employee")
    public Result empUpdate(@RequestBody Employee employee){
        log.info("修改员工信息员工：{}", employee);
        employeeService.empUpdate(employee);
        return Result.success();
    }

    @GetMapping("/customer")
    public Result<List<Customer>> cusList(){
        log.info("列出所有顾客信息");
        List<Customer> cusList = customerService.cusList();
        return Result.success(cusList);
    }

    @GetMapping("/customer/{phoneChar}")
    public Result<List<Customer>> cusCharQuery(@PathVariable(required = false) String phoneChar){
        log.info("电话查询:{}",phoneChar);
        List<Customer> cusList = customerService.cusCharQuery(phoneChar);
        return Result.success(cusList);
    }

    @PostMapping("/customer")
    public Result cusAdd(@RequestBody Customer customer){
        log.info("新增顾客:{}", customer);
        customerService.cusAdd(customer);
        return Result.success();
    }

    @PutMapping("/customer")
    public Result cusScoreOperation(@RequestBody CusScoreOpDTO cusScoreOpDTO){
        log.info("对顾客进行积分操作：{}", cusScoreOpDTO);
        customerService.scoreOp(cusScoreOpDTO);
        return Result.success();
    }

    @PutMapping(value = "/customer/ke")
    public Result cusWalletOperation(@RequestBody CusWalletOpDTO cusWalletOpDTO){
        log.info("对顾客进行余额充值：{}",cusWalletOpDTO);
        customerService.walletUpdate(cusWalletOpDTO);
        return Result.success();
    }

    @GetMapping("/record")
    public Result<List<payrecordVO>> record_list(){
        log.info("列出所有充值记录");
        List<payrecordVO> pay_list = customerService.record_list();
        //log.info(pay_list.toString());
        return Result.success(pay_list);
    }
}
