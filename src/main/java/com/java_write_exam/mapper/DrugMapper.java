package com.java_write_exam.mapper;

import com.java_write_exam.VO.DrugNumRemindVO;
import com.java_write_exam.VO.drugperiodVO;
import com.java_write_exam.entity.DrugAddInfo;
import com.java_write_exam.VO.drugListVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DrugMapper {

    /**
     * 总记录数
     * @return
     */
    @Select("SELECT count(*) from medicine m, warehouse w where m.id = w.drug_id")
    public Long count();
    /**
     * 分页查询获取列表数据
     * @return
     */
    @Select("select m.id, m.drug_name, m.pur_price,m.price, m.specs, " +
            "w.num, w.date_of_manufacture, w.effective_date " +
            "from medicine m, warehouse w " +
            "where m.id = w.drug_id limit #{start}, #{pageSize};")
    List<drugListVO> drugList(Integer start, Integer pageSize);

    /**
     * 通过药名关键字查找
     * @param drugName
     * @return
     */
    @Select("select m.id, m.drug_name, m.price, m.specs, " +
            "w.num, w.date_of_manufacture, w.effective_date " +
            "from medicine m, warehouse w where m.id = w.drug_id " +
            "and m.drug_name like concat('%',#{drugName},'%');")
    List<drugListVO> searchByName(String drugName);

    /**
     * 通过条形码查找
     * @param barCode
     * @return
     */
    @Select("select m.id, m.drug_name, m.price, m.specs, " +
            "w.num, w.date_of_manufacture, w.effective_date " +
            "from medicine m, warehouse w where m.id = w.drug_id " +
            "and m.bar_code = #{barCode};")
    List<drugListVO> searchByBar(String barCode);

    /**
     * 添加药品信息
     * @return
     */
    Integer add(DrugAddInfo drugAddInfo);

    /**
     * 查询所有药品信息
     * @return
     */
    @Select("select id, drug_name, price, num from medicine m, warehouse w where m.id=w.drug_id")
    List<drugListVO> getAll();

    /**
     * 查询临期药品信息
     * @return
     */
    @Select("select m.id id ,m.drug_name drug_name, m.bar_code bar_code, w.num num, w.effective_date eff_date from medicine as m ,warehouse as w where w.drug_id = m.id and w.effective_date <= CURRENT_DATE + INTERVAL 3 month")
    List<drugperiodVO> getDrugPeriodInfo();

    /**
     * 查询药品预警库存信息
     * @return
     */
    @Select("select m.drug_name drug_name,m.bar_code bar_code,w.num num from warehouse as w,medicine as m  where m.id = w.drug_id and w.num<20")
    List<DrugNumRemindVO> getDrugRemindInfo();
}
