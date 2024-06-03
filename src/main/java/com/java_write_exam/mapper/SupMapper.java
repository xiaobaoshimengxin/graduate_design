package com.java_write_exam.mapper;

import com.java_write_exam.entity.Supplier;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SupMapper {
    /**
     * 查询所有供应商信息
     */
    List<Supplier> getAll(String supWord);

    /**
     * 添加供应商信息
     */
    @Insert("insert into supplier(name, create_time, address, contact_number) " +
            "values (#{name}, #{createTime}, #{address}, #{contactNumber})")
    void supAdd(Supplier supplier);

    /**
     * 修改供应商信息
     */
    void supUpdate(Supplier supplier);

    /**
     * 通过id查name
     * @param supplierId
     * @return
     */
    @Select("select name from supplier where id=#{supplierId}")
    String getNameById(Integer supplierId);
}
