package com.java_write_exam.mapper;

import com.java_write_exam.DTO.DrugInfo;
import com.java_write_exam.DTO.IdAndCount;
import com.java_write_exam.VO.PurNoDrugVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface                                                                                  PurMapper {
    /**
     * 查询总数据
     * @return
     */
    @Select("select count(*) from purchase group by pur_id")
    List<IdAndCount> getCount();

    /**
     * 分页查询采购历史
     * @param start
     * @param pageSize
     * @return
     */
    List<PurNoDrugVO> getPage(Integer start, Integer pageSize, LocalDate purDate, String supplierName);

    /**
     * 根据purId查找采购药品的信息
     * @param purId
     * @return
     */
    @Select("select p.drug_name, drug_count, price from purchase p, medicine m where pur_id = #{purId} and p.drug_name=m.drug_name")
    List<DrugInfo> drugInfoByPurId(String purId);

    /**
     *添加采购订单记录
     * @param buyDate
     * @param drugName
     * @param drugCount
     * @param supplierName
     * @param purId
     */
    @Insert("insert into purchase(buy_date, drug_name, drug_count, supplier_name, pur_id) " +
            "values (#{buyDate}, #{drugName}, #{drugCount}, #{supplierName}, #{purId})")
    void add(String buyDate, String drugName, Integer drugCount, String supplierName, String purId);
}
