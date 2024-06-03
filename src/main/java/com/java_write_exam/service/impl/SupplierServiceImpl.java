package com.java_write_exam.service.impl;

import com.java_write_exam.entity.Supplier;
import com.java_write_exam.mapper.SupMapper;
import com.java_write_exam.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupMapper supMapper;
    @Override
    public List<Supplier> supList(String supWord) {
        return supMapper.getAll(supWord);
    }

    @Override
    public void supAdd(Supplier supplier) {
        supMapper.supAdd(supplier);
    }

    @Override
    public void supUpdate(Supplier supplier) {
        supMapper.supUpdate(supplier);
    }
}
