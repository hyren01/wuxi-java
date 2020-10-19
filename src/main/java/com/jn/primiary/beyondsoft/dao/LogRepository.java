package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.office.entity.TbStdglFiletable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<CategoryInfo,String> {
    //把当前目录的id作为其他目录的父id去查询
    @Query(value="SELECT * FROM oper_tb_stdgl_code_info WHERE create_time > ?1 ", nativeQuery = true)
    List<HashMap<String,Object>> getOperCodeInfo(String time);

    @Query(value="SELECT * FROM oper_tb_stdgl_schemafield WHERE createtime > ?1 ", nativeQuery = true)
    List<HashMap<String,Object>> getOperStandardField(String time);

    @Query(value="SELECT * FROM oper_tb_stdgl_schemamodule WHERE createtime > ?1 ", nativeQuery = true)
    List<HashMap<String,Object>> getOperStandard(String time);

    @Query(value="SELECT * FROM tb_meta_model WHERE create_time > ?1 ", nativeQuery = true)
    List<HashMap<String,Object>> getModel(String time);

    @Query(value="SELECT * FROM tb_stdgl_code_info WHERE create_time > ?1 ", nativeQuery = true)
    List<HashMap<String,Object>> getCodeInfo(String time);

    @Query(value="SELECT * FROM tb_stdgl_filetable WHERE upload_time > ?1 ", nativeQuery = true)
    List<HashMap<String,Object>> getTbStdglFiletable(String time);
}
