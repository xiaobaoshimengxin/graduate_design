package com.java_write_exam.service;

import com.java_write_exam.VO.DrugNumRemindVO;
import com.java_write_exam.VO.drugListVO;
import com.java_write_exam.VO.drugperiodVO;
import com.java_write_exam.util.PageResult;

import java.util.List;

public interface DrugService {
    /**
     * 药品库存分页查询
     * @return
     */
    PageResult drugList(Integer page, Integer pageSize);

    /**
     * 通过药名查询
     * @param drugName
     * @return
     */
    List<drugListVO> drugByName(String drugName);

    /**
     * 通过条码查询
     * @param barCode
     * @return
     */
    List<drugListVO> drugByBar(String barCode);

    List<drugListVO> drugAll();

    List<drugperiodVO> getperiodInfo();

    List<DrugNumRemindVO> getRemindInfo();
}
