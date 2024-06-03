package com.java_write_exam.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;

@Mapper
public interface WarehouseMapper {
    /**
     * 添加仓库记录
     * @param drugId
     * @param drugCount
     * @param buyDate
     * @param dateOfManufacture
     * @param effectiveDate
     */
    @Insert("insert into warehouse(drug_id, num, receive_date, date_of_manufacture, effective_date) " +
            "values (#{drugId}, #{drugCount}, #{buyDate}, #{dateOfManufacture}, #{effectiveDate})")
    void add(Integer drugId, Integer drugCount, String buyDate, LocalDate dateOfManufacture, LocalDate effectiveDate);

    /**
     * 出售减少库存
     * @param id
     * @param saleCount
     */
    @Update("update warehouse set num = num - #{saleCount} where drug_id = #{id}")
    void updateNum(Integer id, Integer saleCount);

}
