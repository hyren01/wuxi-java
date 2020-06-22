package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.OperStandardObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OperObjectRepository extends JpaRepository <OperStandardObject,Integer> {

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_object(batch_no,obj_id,version,obj_cname," +
            "obj_ename,short_name,obj_defined,obj_data_type,data_type,moudle_id,status) " +
            "values(:#{#ob.batchNo},:#{#ob.objId},:#{#ob.version}," +
            ":#{#ob.objCname},:#{#ob.objEname},:#{#ob.shortName},:#{#ob.objDefined}," +
            ":#{#ob.objDataType},:#{#ob.dataType},:#{#ob.moudleId},:#{#ob.status})",nativeQuery = true)
    void saveOperObject(@Param("ob") OperStandardObject ob);

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_object " +
            "(obj_id,version,obj_cname,obj_ename,short_name,obj_defined,obj_data_type,moudle_id,status,batch_no,data_type) " +
            "select obj_id,version,obj_cname,obj_ename,short_name,obj_defined,obj_data_type,moudle_id,'2',?1,?2 from tb_stdgl_object " +
            "where moudle_id = ?3 and status='1' ",nativeQuery = true)
    void deleteObject(String batchNo, String dataType, String id);

    @Query(value ="select * from oper_tb_stdgl_object " +
            "where batch_no=?1 and moudle_id=?2",nativeQuery = true)
    List<OperStandardObject> getObjectByBatchNo(String batchNo, String id);

    @Modifying
    @Query(value = "delete from oper_tb_stdgl_object " +
            "where batch_no =?1  ",nativeQuery = true)
    void deleteObjectByBatchNo(String batchNo);

    @Modifying
    @Query(value = "delete from oper_tb_stdgl_object where moudle_id=?1 and batch_no = ?2",nativeQuery = true)
    void deleteByIdAndBatchNo(String id, String batch_no);

}