package com.java_write_exam.service.impl;

import com.java_write_exam.DTO.*;
import com.java_write_exam.VO.SaleCountVO;
import com.java_write_exam.VO.SaleHistoryVO;
import com.java_write_exam.VO.SaleMoneyVO;
import com.java_write_exam.VO.SaleNoDrugVO;
import com.java_write_exam.entity.SaleExcelOut;
import com.java_write_exam.entity.Sales;
import com.java_write_exam.mapper.CustomerMapper;
import com.java_write_exam.mapper.EmployeeMapper;
import com.java_write_exam.mapper.SaleMapper;
import com.java_write_exam.mapper.WarehouseMapper;
import com.java_write_exam.service.SaleService;
import com.java_write_exam.util.DateAndOdd;
import com.java_write_exam.util.PageResult;
import com.java_write_exam.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;


    @Override

    public PageResult salesList(SaleHistoryDTO saleHistoryDTO) {
        Integer page = saleHistoryDTO.getPage();
        Integer pageSize = saleHistoryDTO.getPageSize();
        LocalDate saleDate = saleHistoryDTO.getSaleDate();
        String drugName = saleHistoryDTO.getDrugName();
        // 先查总
        List<IdAndCount> iAC = saleMapper.getIdAndCount();
        int count = iAC.size();     // 分页查询的条数
        // 分页查询
        Integer start = (page - 1) * pageSize;
        List<SaleNoDrugVO> saleNoDrugVOS = saleMapper.getPage(start, pageSize, saleDate, drugName);
        SaleNoDrugVO[] array = saleNoDrugVOS.toArray(new SaleNoDrugVO[0]);
        SaleHistoryVO[] sV = new SaleHistoryVO[array.length];

        for (int i = 0; i < array.length; i++){
            SaleHistoryVO s = new SaleHistoryVO();
            Integer employeeId = array[i].getEmployeeId();
            Integer customerId = array[i].getCustomerId();
            BeanUtils.copyProperties(array[i], s);
            s.setCustomer(customerMapper.getNameById(customerId));
            s.setEmployee(employeeMapper.getNameById(employeeId));
            String tmp = s.getPayType();
            s.setPayType(tmp.equals("1")?"医保余额":(tmp.equals("2")?"微信支付":"现金支付"));
            String saleId = s.getSaleId();

            // 获得id和数量
            List<DrugInfo> drugInfoList = saleMapper.drugInfoById(saleId);
//            System.out.println(drugInfoList.toString());
            s.setDrugList(drugInfoList);
            // 构造drugS
            StringBuilder drugS = new StringBuilder();
            for (DrugInfo drugInfo: drugInfoList){
                drugS.append(",").append(drugInfo.getDrugName());
            }
            s.setDrugs(drugS.substring(1, drugS.length()));
            sV[i] = s;
        }
        // 封装返回
        List<SaleHistoryVO> list = Arrays.asList(sV);
        return new PageResult(count, list);
    }

    @Transactional
    @Override
    public void saleAdd(SalesAddDTO salesAddDTO) {
        Integer customerId = salesAddDTO.getId();
        // 顾客加积分，使用积分抵扣结算不可累计积分
        if (salesAddDTO.getUseScore()==0)
            {
            customerMapper.updateScoreById(customerId, (int) (salesAddDTO.getPriceAll() * 10));
        }
        // 采购时间和订单号
        DateAndOdd dateAndOdd = DateAndOdd.getDateAndOdd("yyyy-MM-dd HH:mm:ss");
        String saleDate = dateAndOdd.getDate();
        String saleId = "s"+dateAndOdd.getOdd();

        List<DrugSaleDTO> drugList = salesAddDTO.getDrugList();

        Integer useScore = salesAddDTO.getUseScore();
        float priceAll = (float) (salesAddDTO.getPriceAll()-(useScore/100.00));
        String notes = "";
        // 使用积分
        if (useScore > 0){
            notes += "使用" + String.valueOf(useScore) + "积分抵扣了" + String.valueOf(useScore/100.00) + "元"+"\t";
            customerMapper.updateScoreById(salesAddDTO.getId(), -useScore);
        }
        //记录支付手段
//        System.out.println(salesAddDTO.getPay_style());
//        System.out.println(salesAddDTO.getId());
//        System.out.println(salesAddDTO.getPriceAll());
        switch (salesAddDTO.getPayType()){
            case 1:{notes += "医保余额支付"+priceAll+"元";
                customerMapper.UpdateWallet(salesAddDTO.getId(),priceAll);         //更新余额
                break;}
            case 2:{notes += "微信支付"+salesAddDTO.getPriceAll()+"元";break;}
            case 3:{notes += "现金支付"+salesAddDTO.getPriceAll()+"元";break;}
        }
        for (DrugSaleDTO drugSale :drugList){
            // 库存减数量
            warehouseMapper.updateNum(drugSale.getId(), drugSale.getSaleCount());
            // 添加售卖订单记录
            Sales sales = new Sales();
            BeanUtils.copyProperties(drugSale, sales);
            sales.setDrugId(drugSale.getId());
            sales.setDrugCount(drugSale.getSaleCount());
            sales.setSaleDate(saleDate);
            sales.setCustomerId(customerId);
            sales.setSaleId(saleId);
            sales.setEmployeeId(salesAddDTO.getEmpId());
            sales.setNotes(notes);
            sales.setPayType(salesAddDTO.getPayType());
            saleMapper.saleAdd(sales);
        }
    }

    /**
     * 导出数据
     */
    @Override

    public void getAllsales(HttpServletResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Object> head = List.of("单号", "时间", "顾客", "出售药品", "数量", "单价", "总额", "员工", "备注");

        List<List<Object>> sheetDataList = new ArrayList<>();

        List<SaleExcelOut> saleValues = saleMapper.getAllSales();
        sheetDataList.add(head);

        String nowSaleId = "";
        for (SaleExcelOut saleData:saleValues){
            List<Object> rowDate = new ArrayList<>();
            LocalDateTime sDate = saleData.getSaleDate();

            if (nowSaleId.equals(saleData.getSaleId())){
                rowDate.add(ExcelUtils.ROW_MERGE);
                rowDate.add(ExcelUtils.ROW_MERGE);
                rowDate.add(ExcelUtils.ROW_MERGE);
                rowDate.add(saleData.getDrugName());
                rowDate.add(saleData.getDrugCount());
                rowDate.add(saleData.getPrice());
                rowDate.add(ExcelUtils.ROW_MERGE);
                rowDate.add(ExcelUtils.ROW_MERGE);
                rowDate.add(ExcelUtils.ROW_MERGE);
            }
            else{
                rowDate.add(saleData.getSaleId());
                rowDate.add(sDate.format(formatter));
                rowDate.add(saleData.getCustomer());
                rowDate.add(saleData.getDrugName());
                rowDate.add(saleData.getDrugCount());
                rowDate.add(saleData.getPrice());
                rowDate.add(saleData.getTotalPrice());
                rowDate.add(saleData.getEmployee());
                rowDate.add(saleData.getNotes());

                nowSaleId = saleData.getSaleId();
            }

            sheetDataList.add(rowDate);
        }
        ExcelUtils.export(response,"表单", sheetDataList);
    }

    @Override
    public List<SaleCountVO> getNameAndCount(SaleCountInfoDTO saleCountInfoDTO){
        LocalDate s_date = saleCountInfoDTO.getStart_term();
        LocalDate l_date = saleCountInfoDTO.getLast_term();
        //获取销售数量的数据
//        System.out.println(saleCountInfoDTO.toString());
//        System.out.println(l_date);
//        System.out.println(saleMapper.getSaleCount(s_date,l_date));
        return saleMapper.getSaleCount(s_date,l_date);
    }

    @Override
    public List<SaleMoneyVO> getDaySaleMoney(SaleCountInfoDTO saleCountInfoDTO){
        LocalDate s_date = saleCountInfoDTO.getStart_term();
        LocalDate l_date = saleCountInfoDTO.getLast_term();
        //获取日销售额
        return saleMapper.getSaleMoney(s_date,l_date);
    }
    @Override
    public List<SaleMoneyVO> getAllSaleMoney(){
//        String test = "s2024051615";
//        List<DrugInfo> drug_list = saleMapper.getDrugInfoBySaleId(test);
//        System.out.println(drug_list.get(0));
//        for (int i=0;i<drug_list.size();i++){
//            System.out.println(drug_list[i]);
//        }
        return saleMapper.getAllMoney();
    }

    @Transactional
    @Override
    public void revokeOrder(RevokeOrderDTO revokeOrderDTO){
        System.out.println("执行撤销接口实现类了");
        List<DrugInfo> drug_list = saleMapper.getDrugInfoBySaleId(revokeOrderDTO.getSaleId());
        System.out.println(drug_list);
        //先加库存
        for (int i=0;i<drug_list.size();i++){
            warehouseMapper.updateNum(drug_list.get(i).getDrugId(),-drug_list.get(i).getDrugCount());
        }
        //再加用户金额
        int id = drug_list.get(0).getCustomerId();
        customerMapper.UpdateWallet(id,-saleMapper.getTotalPriceBySaleId(revokeOrderDTO.getSaleId()));
        //最后删除订单记录
        saleMapper.deleteBySale_id(revokeOrderDTO.getSaleId());
    }
}
