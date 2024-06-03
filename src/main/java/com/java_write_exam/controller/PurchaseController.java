package com.java_write_exam.controller;

import com.java_write_exam.DTO.BuyDateAndSupplier;
import com.java_write_exam.DTO.PurAddFormDTO;
import com.java_write_exam.service.PurService;
import com.java_write_exam.util.PageResult;
import com.java_write_exam.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurService purchaseService;

    @PostMapping("/history")
    public Result<PageResult> purHistory(@RequestBody BuyDateAndSupplier bAndS){
        log.info("采购历史查询：{}，{}", bAndS.getPurDate(), bAndS.getSupplierName());
        log.info("采购历史查询：page:{}, pageSize:{}", bAndS.getPage(), bAndS.getPageSize());
        PageResult pageResult = purchaseService.getPurchaseHistory(bAndS);
        return Result.success(pageResult);
    }

    @PostMapping("/purchaseadd")
    public Result purchaseAdd(@RequestBody PurAddFormDTO purForm){
        log.info("添加采购信息:{}", purForm.toString());
        purchaseService.purAdd(purForm);
        return Result.success();
    }
}
