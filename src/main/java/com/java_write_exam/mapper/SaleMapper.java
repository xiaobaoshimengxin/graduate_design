package com.java_write_exam.mapper;

import com.java_write_exam.DTO.DrugInfo;
import com.java_write_exam.DTO.IdAndCount;
import com.java_write_exam.VO.SaleCountVO;
import com.java_write_exam.VO.SaleMoneyVO;
import com.java_write_exam.VO.SaleNoDrugVO;
import com.java_write_exam.entity.SaleExcelOut;
import com.java_write_exam.entity.Sales;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SaleMapper {
    /**
     * group by id
     * @return
     */
    @Select("select count(*) from sales group by sale_id")
    List<IdAndCount> getIdAndCount();

    /**
     * 分页查询
     * @param start
     * @param pageSize
     * @param saleDate
     * @param drugName
     * @return
     */
    List<SaleNoDrugVO> getPage(Integer start, Integer pageSize, LocalDate saleDate, String drugName);

    /**
     * 根据sale_id查drug_id 和 sale_count
     * @param saleId
     * @return
     */
    @Select("select drug_id, drug_name, drug_count, price from sales where sale_id = #{saleId}")
    List<DrugInfo> drugInfoById(String saleId);

    /**
     * 添加出售记录
     * @param sales
     */
    @Insert("insert into sales(sale_date, employee_id, drug_id, drug_name, price, drug_count, customer_id, sale_id, notes,pay_type) " +
            "values (#{saleDate}, #{employeeId}, #{drugId}, #{drugName}, #{price}, #{drugCount}, #{customerId}, #{saleId}, #{notes},#{payType})")
    void saleAdd(Sales sales);

    @Select("select s.sale_date, s.sale_id, e.name employee, s.drug_name, s.price, s.drug_count, c.name customer, s.notes,ROUND(s2.total,2) as totalPrice  from employee e, sales s left join customer c on s.customer_id= c.id join (select sale_id, sum(price*drug_count) as total from sales group by sale_id) s2 on s.sale_id=s2.sale_id where s.employee_id=e.id")
    List<SaleExcelOut> getAllSales();

    /**
     * 根据日期查药品名字、销售数量
     * @param
     */
    @Select("select  sales.drug_name drug_name,SUM(drug_count) sum_counts from sales where DATE(sale_date) between #{s_Date} and #{l_Date} group by drug_name  ")
    List<SaleCountVO> getSaleCount(LocalDate s_Date,LocalDate l_Date);

    /**
     * 查相应日期的日销售额
     * @param
     */
    @Select("select DATE(sale_date) sale_date, format(sum(price*drug_count),2) sale_amount from sales where DATE(sale_date) between #{s_Date} and #{l_Date} group by DATE(sale_date)")
    List<SaleMoneyVO> getSaleMoney(LocalDate s_Date,LocalDate l_Date);

    /**
     * 查所有销售额
     * @param
     */
    @Select("select DATE(sale_date) sale_date, format(sum(price*drug_count),2) sale_amount from sales group by DATE(sale_date)")
    List<SaleMoneyVO> getAllMoney();

    /**
     * 根据sale_id撤销订单
     * @param
     */
    @Delete("delete from sales where #{saleId} = sale_id")
    void deleteBySale_id(String saleId);

    /**
     * 根据sale_id查询订单药品信息
     * @param
     */
    @Select("select drug_id,drug_name,drug_count,customer_id from sales where sale_id = #{saleId}")
    List<DrugInfo>  getDrugInfoBySaleId(String saleId);

    /**
     * 根据sale_id计算订单总额
     * @param
     */
    @Select("select distinct (ROUND(s2.total,2)) as total_price  from employee e, sales s left join customer c on s.customer_id= c.id join (select sale_id, sum(price*drug_count) as total from sales group by sale_id) s2 on s.sale_id=s2.sale_id where s.employee_id=e.id and s.sale_id = #{saleId}")
    float getTotalPriceBySaleId(String saleId);
}
