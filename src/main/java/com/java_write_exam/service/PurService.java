package com.java_write_exam.service;

import com.java_write_exam.DTO.BuyDateAndSupplier;
import com.java_write_exam.DTO.PurAddFormDTO;
import com.java_write_exam.util.PageResult;

public interface PurService {
    PageResult getPurchaseHistory(BuyDateAndSupplier bAndS);

    void purAdd(PurAddFormDTO purForm);
}
