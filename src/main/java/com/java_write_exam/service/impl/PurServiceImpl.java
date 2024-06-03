package com.java_write_exam.service.impl;

import com.java_write_exam.DTO.*;
import com.java_write_exam.VO.PurHistoryVO;
import com.java_write_exam.VO.PurNoDrugVO;
import com.java_write_exam.entity.DrugAddInfo;
import com.java_write_exam.mapper.DrugMapper;
import com.java_write_exam.mapper.PurMapper;
import com.java_write_exam.mapper.SupMapper;
import com.java_write_exam.mapper.WarehouseMapper;
import com.java_write_exam.service.PurService;
import com.java_write_exam.util.DateAndOdd;
import com.java_write_exam.util.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PurServiceImpl implements PurService {
    @Autowired
    private PurMapper purMapper;
    @Autowired
    private SupMapper supMapper;
    @Autowired
    private DrugMapper drugMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Override
    public PageResult getPurchaseHistory(BuyDateAndSupplier bAndS) {
        Integer page = bAndS.getPage();             //页码信息
        Integer pageSize = bAndS.getPageSize();     //页面大小
        Integer start = (page-1)*pageSize;
        // 先查总
        List<IdAndCount> pAC = purMapper.getCount();
        int count = pAC.size();
        // 再分页查询数据库
        List<PurNoDrugVO> purVOList = purMapper.getPage(start, pageSize, bAndS.getPurDate(), bAndS.getSupplierName());
        PurNoDrugVO[] array = purVOList.toArray(new PurNoDrugVO[0]);
        PurHistoryVO[] pL = new PurHistoryVO[array.length];

        for (int i = 0; i < array.length; i++){
            PurHistoryVO p = new PurHistoryVO();
            BeanUtils.copyProperties(array[i], p);
            String purId = array[i].getPurId();
            List<DrugInfo> drugInfoList = purMapper.drugInfoByPurId(purId);
            p.setDrugInfo(drugInfoList);
            StringBuilder drugS = new StringBuilder();
            for (DrugInfo drugInfo:drugInfoList){
                drugS.append(",").append(drugInfo.getDrugName());
            }
            p.setDrugNames(drugS.substring(1, drugS.length()));
            pL[i] = p;
        }
        List<PurHistoryVO> list = Arrays.asList(pL);
        // 封装返回
        return new PageResult(count, list);
    }

    @Transactional  // spring事务管理
    @Override
    public void purAdd(PurAddFormDTO purForm) {         //新增采购
        Integer supplierId = purForm.getSupplierId();
        // 供应商
        String supplierName = supMapper.getNameById(supplierId);
        // 采购时间和订单号
        DateAndOdd dateAndOdd = DateAndOdd.getDateAndOdd("yyyy-MM-dd");
        String buyDate = dateAndOdd.getDate();
        String purId = "p"+dateAndOdd.getOdd();

        //System.out.println(purId);
        List<DrugAddInfo> drugList = purForm.getDrugList();
        for (DrugAddInfo drug: drugList){
            // 添加药品
            drug.setSupplierId(supplierId);
            drugMapper.add(drug);
            // 添加采购订单记录
            purMapper.add(buyDate, drug.getDrugName(), drug.getDrugCount(), supplierName, purId);
            // 添加仓库记录
            Integer drugId = drug.getId();
            warehouseMapper.add(drugId, drug.getDrugCount(), buyDate, drug.getDateOfManufacture(), drug.getEffectiveDate());
        }
    }
}
