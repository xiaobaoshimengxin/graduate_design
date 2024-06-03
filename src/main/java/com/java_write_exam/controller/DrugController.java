package com.java_write_exam.controller;

import com.java_write_exam.DTO.UserClaim;
import com.java_write_exam.Interceptor.LoginCheckInterceptor;
import com.java_write_exam.VO.DrugNumRemindVO;
import com.java_write_exam.VO.drugListVO;
import com.java_write_exam.VO.drugperiodVO;
import com.java_write_exam.service.DrugService;
import com.java_write_exam.util.PageResult;
import com.java_write_exam.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class DrugController {
    @Autowired
    private DrugService drugService;

    @GetMapping("/drugAll")
    public Result<List<drugListVO>> drugAll(){
        log.info("请求药品列表信息");
        List<drugListVO> drugAll = drugService.drugAll();
        return Result.success(drugAll);
    }

    @GetMapping("/druglist")
    public Result<PageResult> drugListAll(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageResult pageResult = drugService.drugList(page, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping("/drugbyname/{drugName}")
    public Result<List<drugListVO>> drugByName(@PathVariable String drugName){
        log.info("按名字查询{}", drugName);
        List<drugListVO> listVOS = drugService.drugByName(drugName);
        return Result.success(listVOS);
    }

    @GetMapping("/drugbybar/{barCode}")
    public Result<List<drugListVO>> drugByBar(@PathVariable String barCode){
        List<drugListVO> listVOS = drugService.drugByBar(barCode);
        return Result.success(listVOS);
    }

    @GetMapping("/period")
    public Result<List<drugperiodVO>> getPeriodInfo (){
        List<drugperiodVO> drug_p_list = drugService.getperiodInfo();
        log.info("临期药品提示，{}",drug_p_list);
        return Result.success(drug_p_list);
    }

    @GetMapping("/remind")
    public Result<List<DrugNumRemindVO>> getRemindInfo(){
        List<DrugNumRemindVO> drug_r_list = drugService.getRemindInfo();
        log.info("库存预警提示信息，{}",drug_r_list);
        return Result.success(drug_r_list);
    }
}
