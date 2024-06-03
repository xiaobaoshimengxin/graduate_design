package com.java_write_exam.service;

import com.java_write_exam.DTO.RevokeOrderDTO;
import com.java_write_exam.DTO.SaleCountInfoDTO;
import com.java_write_exam.DTO.SaleHistoryDTO;
import com.java_write_exam.DTO.SalesAddDTO;
import com.java_write_exam.VO.SaleCountVO;
import com.java_write_exam.VO.SaleMoneyVO;
import com.java_write_exam.entity.SaleExcelOut;
import com.java_write_exam.util.PageResult;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface SaleService {
    PageResult salesList(SaleHistoryDTO saleHistoryDTO);

    void saleAdd(SalesAddDTO salesAddDTO);

    void getAllsales(HttpServletResponse response);

    List<SaleCountVO> getNameAndCount(SaleCountInfoDTO saleCountInfoDTO);

    List<SaleMoneyVO> getDaySaleMoney(SaleCountInfoDTO saleCountInfoDTO);

    List<SaleMoneyVO> getAllSaleMoney();

    void revokeOrder(RevokeOrderDTO revokeOrderDTO);
}
