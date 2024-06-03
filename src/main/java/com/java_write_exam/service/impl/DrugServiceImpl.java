package com.java_write_exam.service.impl;

import com.java_write_exam.VO.DrugNumRemindVO;
import com.java_write_exam.VO.drugListVO;
import com.java_write_exam.VO.drugperiodVO;
import com.java_write_exam.mapper.DrugMapper;
import com.java_write_exam.service.DrugService;
import com.java_write_exam.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DrugServiceImpl implements DrugService {
    @Autowired
    private DrugMapper drugMapper;
    @Override
    public PageResult drugList(Integer page, Integer pageSize) {
        log.info("分页查询:{},{}", page, pageSize);
        // 获取总记录数
        Long count = drugMapper.count();
        // 获取分页查询结果列表
        Integer start = (page - 1) * pageSize;
        List<drugListVO> list = drugMapper.drugList(start, pageSize);
        // 封装对象并返回
        return new PageResult(count, list);
    }

    @Override
    public List<drugListVO> drugByName(String drugName) {
        List<drugListVO> drugListVOS = drugMapper.searchByName(drugName);
        log.info("按名称查询：result:{}",drugListVOS.toString());
        return drugListVOS;
    }

    @Override
    public List<drugListVO> drugByBar(String barCode) {
        return drugMapper.searchByBar(barCode);
    }

    @Override
    public List<drugListVO> drugAll() {
        return drugMapper.getAll();
    }

    @Override
    public List<drugperiodVO> getperiodInfo (){
        return drugMapper.getDrugPeriodInfo();
    }

    @Override
    public List<DrugNumRemindVO> getRemindInfo(){
        return drugMapper.getDrugRemindInfo();
    }
}
