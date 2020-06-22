package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.OperFieldCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OperFieldCodeRepository extends JpaRepository <OperFieldCode, Integer> {

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_field_code(batch_no,field_id,code_id,data_type,codetable_ename,category_id)" +
            "values(:#{#ofc.batchNo},:#{#ofc.fieldId},:#{#ofc.codeId},:#{#ofc.dataType},:#{#ofc.codeName},:#{#ofc.category_id})",nativeQuery = true)
    void saveOperFieldCode(@Param("ofc") OperFieldCode ofc);

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_field_code " +
            "(batch_no,field_id,code_id,data_type,codetable_ename,category_id) " +
            "select ?1,field_id,code_id,'3',codetable_ename,category_id from tb_stdgl_field_code " +
            "where codetable_ename=?2 ",nativeQuery = true)
    void deleteOperFieldCode(String batchNo, String codeName);

    @Modifying
    @Query(value = "select distinct(field_id) from oper_tb_stdgl_field_code where batch_no=?1 and codetable_ename=?2",nativeQuery = true)
    String[] getFieldCodeByCodeName(String batchNo, String codeName);

    @Modifying
    @Query(value = "delete from oper_tb_stdgl_field_code " +
            "where batch_no=?1",nativeQuery = true)
    void deleteByBatchNo(String batchNo);

    @Modifying
    @Query(value = "delete from oper_tb_stdgl_field_code " +
            "where batch_no=?1 and codetable_ename=?2",nativeQuery = true)
    void deleteByIdAndBatchNo(String batchNo, String codeName);

}