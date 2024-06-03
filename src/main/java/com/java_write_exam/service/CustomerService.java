package com.java_write_exam.service;

import com.java_write_exam.DTO.CusScoreOpDTO;
import com.java_write_exam.DTO.CusWalletOpDTO;
import com.java_write_exam.VO.payrecordVO;
import com.java_write_exam.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> cusList();

    List<Customer> cusCharQuery(String phoneChar);

    void cusAdd(Customer customer);

    void scoreOp(CusScoreOpDTO cusScoreOpDTO);
    void walletUpdate(CusWalletOpDTO cusWalletOpDTO);
    List<payrecordVO> record_list();
}
