package com.java_write_exam.mapper;

import com.java_write_exam.DTO.CusScoreOpDTO;
import com.java_write_exam.VO.payrecordVO;
import com.java_write_exam.entity.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CustomerMapper {
    /**
     * 列出所有顾客信息
     * @return
     */
    @Select("SELECT id, concat(substring(phone, 1, 3), '****', substring(phone, -4)) as phone, " +
            "name,wallet, score, create_time FROM customer")
    List<Customer> cusList();

    /**
     * 电话关键字查询
     * @param phoneChar
     * @return
     */
    @Select("select id, concat(substring(phone, 1, 3), '****', substring(phone, -4)) as phone, " +
            "name, score, create_time from customer where phone like concat('%', #{phoneChar})")
    List<Customer> cusByPhone(String phoneChar);

    /**
     * 新增顾客信息
     * @param customer
     */
    @Insert("insert into customer(phone, name, score, wallet,create_time) " +
            "values (#{phone}, #{name}, 0, #{wallet},#{createTime})")
    void insert(Customer customer);

    /**
     * 给顾客增加积分
     * @param id
     * @param score
     */
    @Update("update customer set score = score+#{score} where id=#{id}")
    void updateScoreById(Integer id, Integer score);

    /**
     * 给顾客充值余额
     * @param id
     * @param wallet
     */
    @Update("update customer set wallet = wallet+#{wallet} where id = #{id}")
    void updateWalletById(Integer id, Float wallet);

    /**
     * 根据id查询姓名
     * @param customerId
     * @return
     */
    @Select("select name from customer where id = #{customerId}")
    String getNameById(Integer customerId);

    /**
     * 插入充值记录
     * @param customerId
     * @return
     */
    @Insert("insert into pay_record(customer_id,customer_name,pay_amount,pay_date,employee_id,notes) " +
            "values (#{customerId},#{name},#{wallet},#{pay_date},#{empId},#{notes})")
    void InsertAddRecord(Integer customerId,String name, float wallet,String pay_date,Integer empId,String notes);

    /**
     * 更新用户余额
     * @param
     * @return
     */
    @Update("update customer set wallet = wallet-#{priceAll} where id = #{id}")
    void UpdateWallet(int id,float priceAll);

    /**
     * 查充值记录
     * @param
     * @return
     */
    @Select("select pay_id,customer_id,customer_name,pay_amount,pay_date,employee_id from pay_record ")
    List<payrecordVO> selectPayRecord();
}


