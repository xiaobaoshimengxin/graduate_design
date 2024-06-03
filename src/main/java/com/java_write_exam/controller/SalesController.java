package com.java_write_exam.controller;

import com.java_write_exam.DTO.RevokeOrderDTO;
import com.java_write_exam.DTO.SaleCountInfoDTO;
import com.java_write_exam.DTO.SaleHistoryDTO;
import com.java_write_exam.DTO.SalesAddDTO;
import com.java_write_exam.VO.SaleCountVO;
import com.java_write_exam.VO.SaleMoneyVO;
import com.java_write_exam.entity.SaleExcelOut;
import com.java_write_exam.service.SaleService;
import com.java_write_exam.util.PageResult;
import com.java_write_exam.util.Result;
import com.java_write_exam.util.excel.ExcelUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sales")
@Slf4j
public class SalesController {
    @Autowired
    private SaleService saleService;

    @PostMapping("/history")
    public Result<PageResult> salesHistory(@RequestBody SaleHistoryDTO saleHistoryDTO){
        log.info("售卖历史查询:{}", saleHistoryDTO);
        PageResult pageResult = saleService.salesList(saleHistoryDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/saleAdd")
    public Result saleAdd(@RequestBody SalesAddDTO salesAddDTO){
        log.info("新增出售：{}", salesAddDTO);
        saleService.saleAdd(salesAddDTO);
        return Result.success();
    }

    @GetMapping("/exports")
    public void exportExcel(HttpServletResponse response){
        log.info("文件导出");
        saleService.getAllsales(response);
    }

    @PutMapping("/salecount/count")
    public Result<List<SaleCountVO>> getCount(@RequestBody SaleCountInfoDTO saleCountInfoDTO){
        List<SaleCountVO> SC_list = saleService.getNameAndCount(saleCountInfoDTO);
        log.info("获取药品销售数量{}",SC_list.toString());
        return Result.success(SC_list);
    }

    @PutMapping("/salecount/amount")
    public Result<List<SaleMoneyVO>> getMoney(@RequestBody SaleCountInfoDTO saleCountInfoDTO){
        List<SaleMoneyVO> SM_list = saleService.getDaySaleMoney(saleCountInfoDTO);
        log.info("获取日销售金额数据，{}",SM_list);
        return Result.success(SM_list);
    }

    @GetMapping("/salecount")
    public Result<List<SaleMoneyVO>> getAllMoney (){
        List<SaleMoneyVO> all_list = saleService.getAllSaleMoney();
        log.info("获取全部日销售额数据，{}",all_list);
        return Result.success(all_list);
    }

    @PutMapping("/history/revoke")
    public Result saleRevoke(@RequestBody RevokeOrderDTO revokeOrderDTO){
        log.info("订单撤销操作，{}",revokeOrderDTO);
        saleService.revokeOrder(revokeOrderDTO);
        return Result.success();
    }
}
