package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.OperDocStdModelObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperStdModelObjRepository extends JpaRepository<OperDocStdModelObj,Integer> {

    @Query(value="select * from oper_tb_stdgl_object where moudle_id=?1",nativeQuery = true)
    List<OperDocStdModelObj> getobjbyid (String std_id);

    @Query(value="select * from oper_tb_stdgl_object where moudle_id=?1 and status =1 and batch_no=?2",nativeQuery = true)
    List<OperDocStdModelObj> getobjbyidandstatus (String std_id,String batchno);

    @Query(value="delete * from oper_tb_stdgl_object where moudle_id=?1",nativeQuery = true)
    void delobjfromstdid(String std_id);

    @Query(value="insert into oper_tb_stdgl_object(batch_no,obj_id,version,obj_cname,obj_ename,short_name,obj_defined,obj_data_type,data_type," +
            "moudle_id,status,remark) values (:#{#obj.batch_no},:#{#obj.obj_id},:#{#obj.version},:#{#obj.cname},:#{#obj.ename},:#{#obj.code}," +
            ":#{#obj.defined},:#{#obj.data_type},:#{#obj.oper_flag}", nativeQuery = true)
    void savestdbysql(@Param("obj") OperDocStdModelObj obj);

    @Modifying
    @Query(value="delete from oper_tb_stdgl_object where obj_id=?1 and batch_no=?2", nativeQuery = true)
    void delobjbyobjidandbatchno(String obj_id,String batch_no);

    @Modifying
    @Query(value = "update oper_tb_stdgl_object set status =?1 where batch_no=?2 and obj_id=?3",nativeQuery = true)
    void update_status(int status,String batch_no,String id);
}
