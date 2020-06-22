package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.FieldCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FieldCodeRepository extends JpaRepository <FieldCode, Integer> {

    @Modifying
    @Query(value = "insert into tb_stdgl_field_code " +
            "(field_id,code_id,codetable_ename,category_id) " +
            "select field_id,code_id,codetable_ename,category_id from oper_tb_stdgl_field_code " +
            "where batch_no=?1 and codetable_ename=?2",nativeQuery = true)
    void fieldCodeInfoOperTofinal(String batchNo, String codeName);

    @Modifying
    @Query(value = "delete from tb_stdgl_field_code where codetable_ename =?1 ",nativeQuery = true)
    void deleteFieldCode(String codeName);

    @Modifying
    @Query(value = "select distinct(field_id) from tb_stdgl_field_code where codetable_ename=?1",nativeQuery = true)
    String[] getFieldCodeByCodeName(String codeName);

    @Query(value = "select distinct(codetable_ename) from tb_stdgl_field_code where field_id=?1",nativeQuery = true)
    String getCodeTableByFeildId(String field_id);

    @Query(value = "select field_id from tb_stdgl_field_code where category_id=?1",nativeQuery = true)
    List<String> getFieldCodeByCategoryId(String category_id);

}