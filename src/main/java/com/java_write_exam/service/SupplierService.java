package com.java_write_exam.service;

import com.java_write_exam.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> supList(String supWord);

    void supAdd(Supplier supplier);

    void supUpdate(Supplier supplier);
}
