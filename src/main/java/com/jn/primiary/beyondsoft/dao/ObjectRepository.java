package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.StandardObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ObjectRepository extends JpaRepository <StandardObject,Integer> {

    @Query(value = "select * from tb_stdgl_object " +
            "where moudle_id=?1 and status='1'", nativeQuery = true)
    List<StandardObject> getObjectById(String stdId);

    @Query(value = "select * from tb_stdgl_object " +
            "where moudle_id=?1 and version=?2", nativeQuery = true)
    List<StandardObject> getObjectByIdVersion(String stdId, String Version);

    @Query(value = "select a.obj_id as objId,a.version,a.obj_cname as objCname,a.obj_ename as ObjEname," +
            "a.short_name as shortName,a.obj_defined as objDefined,a.obj_data_type as objDataType," +
            "a.moudle_id as moudleId,a.status,b.fieldname,b.enname,b.fieldcode,b.defination,b.type,b.maxsize," +
            "b.rangee,b.required,b.comments,b.maxcontaincount,b.fieldrange " +
            " from tb_stdgl_object a " +
            "left join tb_stdgl_schemafield b on a.obj_id=b.obj_id " +
            "where a.moudle_id=?1 and a.status='1' and b.moudle_id=?1 and b.status='1'", nativeQuery = true)
    List<Object[]> getObject(String stdId);

    @Modifying
    @Query(value = "insert into tb_stdgl_object " +
            "(obj_id,version,obj_cname,obj_ename,short_name,obj_defined,obj_data_type,moudle_id,status) " +
            "select obj_id,?2,obj_cname,obj_ename,short_name,obj_defined,obj_data_type,moudle_id,status from oper_tb_stdgl_object " +
            "where batch_no=?1 and status='1' and moudle_id=?3", nativeQuery = true)
    void objectOperTofinal(String batchNo, int version, String id);

    @Modifying
    @Query(value = "update tb_stdgl_object set status='2' " +
            "where moudle_id=?1 and version=?2", nativeQuery = true)
    void updateStatus(String moudle_id, String version);

    @Modifying
    @Query(value = "update tb_stdgl_object set status='1' " +
            "where moudle_id=?1 and version=?2", nativeQuery = true)
    void udpateStatusByIdAndVersion(String moudle_id, String version);

    @Query(value = "select * from tb_stdgl_object where obj_id = ?1 and status='1'", nativeQuery = true)
    StandardObject getObjectByObjid(String objid);

    @Query(value = "select * from tb_stdgl_object " +
            "where status='1' order by moudle_id desc", nativeQuery = true)
    List<StandardObject> getAllObject();

    //add by wld 2020.04.09
    @Query(value = "select * from tb_stdgl_object " +
            "where short_name=?1", nativeQuery = true)
    List<StandardObject> findByShortName(String short_name);
}