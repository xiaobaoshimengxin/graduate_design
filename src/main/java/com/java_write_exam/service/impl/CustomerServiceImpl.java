package com.java_write_exam.service.impl;

import com.java_write_exam.DTO.CusScoreOpDTO;
import com.java_write_exam.DTO.CusWalletOpDTO;
import com.java_write_exam.VO.payrecordVO;
import com.java_write_exam.entity.Customer;
import com.java_write_exam.mapper.CustomerMapper;
import com.java_write_exam.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<Customer> cusList() {
        return customerMapper.cusList();
    }

    @Override
    public List<Customer> cusCharQuery(String phoneChar) {
        return customerMapper.cusByPhone(phoneChar);
    }

    @Override
    public void cusAdd(Customer customer) {
        customerMapper.insert(customer);
    }

    @Override
    public void scoreOp(CusScoreOpDTO cusScoreOpDTO) {      //积分更改操作
        int op = cusScoreOpDTO.getOp();
        if (op==1){
            customerMapper.updateScoreById(cusScoreOpDTO.getCustomerId(),cusScoreOpDTO.getScore());
        }
        else{
            customerMapper.updateScoreById(cusScoreOpDTO.getCustomerId(),  -cusScoreOpDTO.getScore());
        }
    }

    @Override
    public void walletUpdate(CusWalletOpDTO cusWalletOpDTO){        //余额充值操作
        customerMapper.updateWalletById(cusWalletOpDTO.getCustomerId(),cusWalletOpDTO.getWallet());
        String name = customerMapper.getNameById(cusWalletOpDTO.getCustomerId());
//        //插入充值数据
        //String pay_date = cusWalletOpDTO.getPay_date();
        //Integer empId = cusWalletOpDTO.getEmpId();
        //log.info(pay_date,empId);
        customerMapper.InsertAddRecord(cusWalletOpDTO.getCustomerId(),name,cusWalletOpDTO.getWallet(),cusWalletOpDTO.getPay_date(),cusWalletOpDTO.getEmpId(),cusWalletOpDTO.getNotes());
    }

    @Override
    public List<payrecordVO> record_list(){
        List<payrecordVO> payrecordVOS = customerMapper.selectPayRecord();
        return customerMapper.selectPayRecord();
    }
}
