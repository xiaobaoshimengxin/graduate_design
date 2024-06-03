package com.java_write_exam.mapper;

import com.java_write_exam.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    /**
     * 根据账号密码查询
     * @param employee
     * @return
     */
    @Select("select * from employee where account = #{account} and password = #{password}")
    Employee getByAccount(Employee employee);

    /**
     * 查询密码是否正确
     * @param employee
     * @return
     */
    @Select("select password from employee where account = #{account} ")
    Boolean passwd_is_true(Employee employee);

    /**
     * 查询所有员工信息
     * @return
     */
    List<Employee> listAll();

    /**
     * 插入员工信息
     */
    @Insert("insert into employee(name, birth, account, address, power, create_time) " +
            "values (#{name}, #{birth}, #{account}, #{address}, #{power}, #{createTime})")
    void insert(Employee employee);

    /**
     * 修改员工信息
     */
    void update(Employee employee);

    /**
     * 根据id查询姓名
     * @param employeeId
     * @return
     */
    @Select("select name from employee where id = #{employeeId}")
    String getNameById(Integer employeeId);

    /**
     *
     * @param id
     * @return
     */
    @Select("select password from employee where id=#{id}")
    String getPasswordById(Integer id);

    /**
     * 重置密码
     * @param newPassword
     */
    @Update("update employee set password = #{newPassword} where id = #{id}")
    void resetPassword(String newPassword, Integer id);
}
