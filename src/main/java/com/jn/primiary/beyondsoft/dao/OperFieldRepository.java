package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.OperStandardField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OperFieldRepository extends JpaRepository <OperStandardField,Integer> {

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_schemafield(batch_no,id,fieldname,enname," +
            "fieldcode,defination,type,maxsize,rangee,required,comments,maxcontaincount," +
            "pxh,creator,createtime,fieldrange,data_type,obj_id,moudle_id,version,status) " +
            "values(:#{#of.batchNo},:#{#of.id},:#{#of.name},:#{#of.enName},:#{#of.code},:#{#of.defination}," +
            ":#{#of.type},:#{#of.maxsize},:#{#of.rangee},:#{#of.required},:#{#of.comments},:#{#of.maxContains}," +
            ":#{#of.pxh},:#{#of.createPerson},:#{#of.createTime},:#{#of.range},:#{#of.dataType},:#{#of.objId}," +
            ":#{#of.moudleId},:#{#of.version},:#{#of.status})",nativeQuery = true)
    void saveOperFeild(@Param("of") OperStandardField of);


    @Modifying
    @Query(value = "insert into oper_tb_stdgl_schemafield " +
            "(id,fieldname,enname,fieldcode,defination,type,maxsize,rangee,required,comments,maxcontaincount,pxh,creator,createtime,fieldrange,status,obj_id,moudle_id,batch_no,data_type) " +
            "select id,fieldname,enname,fieldcode,defination,type,maxsize,rangee,required,comments,maxcontaincount,pxh,creator,createtime,fieldrange,'2',obj_id,moudle_id,?1,?2 from tb_stdgl_schemafield " +
            "where moudle_id = ?3 and status='1' ",nativeQuery = true)
    void deleteFeild(String batchNo, String dataType, String id);

    @Query(value ="select * from oper_tb_stdgl_schemafield " +
            "where IF(?2 !='',obj_id=?2,1=1) and batch_no=?1 and moudle_id=?3" ,nativeQuery = true)
    List<OperStandardField> getFieldByBatchNo(String batchNo, String objId, String id);

    @Query(value = "select * from oper_tb_stdgl_schemafield where batch_no = ?1 and moudle_id = ?2",nativeQuery = true)
    List<OperStandardField> getFieldById(String batchNo, String id);

    @Query(value = "select * from oper_tb_stdgl_schemafield where moudle_id = ?1",nativeQuery = true)
    List<OperStandardField> getAllFieldByModuleId(String moduleId);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_schemafield set moudle_id = ?1,data_type = ?3 where moudle_id = ?2", nativeQuery = true)
    void updateSchemaField(String newModuleId, String oldModuleId, String dataType);

    @Modifying
    @Transactional
    @Query(value = "delete from oper_tb_stdgl_schemafield " +
            "where batch_no=?1 ",nativeQuery = true)
    void deleteFeildByBatchNo(String batchNo);

    @Override
    @Transactional
    <S extends OperStandardField> S save(S s);

    @Modifying
    @Transactional
    @Query(value = "delete from oper_tb_stdgl_schemafield where moudle_id=?1 and batch_no = ?2",nativeQuery = true)
    void deleteByIdAndBatchNo(String id, String batch_no);

    @Modifying
    @Transactional
    @Query(value = "delete from oper_tb_stdgl_schemafield where moudle_id=?1 ",nativeQuery = true)
    void deleteByModuleId(String id);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_schemafield set status = ?1 where moudle_id = ?2", nativeQuery = true)
    void updateSchemaFieldById(String status, String moduleId);
}