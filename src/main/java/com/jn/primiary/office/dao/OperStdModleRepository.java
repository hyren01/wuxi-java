package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.OperDocStdModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OperStdModleRepository extends JpaRepository <OperDocStdModel,Integer>{

	@Query(value="select * from oper_tb_stdgl_schemamodule where file_id=?1", nativeQuery = true)
	List<OperDocStdModel> get_std_mess(String file_id);

	@Query(value="select * from oper_tb_stdgl_schemamodule where file_id=?1 and status = 1 order by createtime desc", nativeQuery = true)
	List<OperDocStdModel> get_std_mess_status(String file_id);

	@Query(value="select * from oper_tb_stdgl_schemamodule where id=?1", nativeQuery = true)
	OperDocStdModel get_one_std_mess(String std_id);

	@Query(value="select * from oper_tb_stdgl_schemamodule where id=?1 and status=1", nativeQuery = true)
	OperDocStdModel get_std_by_idandstatus(String std_id);

	@Query(value="delete * from oper_tb_stdgl_schemamodule where file_id=?1", nativeQuery = true)
	void delstdbyfileid(String file_id);

	@Query(value="select * from oper_tb_stdgl_schemamodule where id!=?1", nativeQuery = true)
	List<OperDocStdModel> get_except_std_mess(String std_id);

	@Modifying
	@Query(value="insert into oper_tb_stdgl_schemamodule(batch_no,id,schemaname,enname,schemacode,description,datasource,version,type," +
			"creator,createtime,update_time,update_person,is_auth,auth_remark,categoryid,file_id,db_id,data_type,status) " +
			"values (:#{#std.batch_no},:#{#std.std_id},:#{#std.cname},:#{#std.ename},:#{#std.code},:#{#std.definded}," +
			":#{#std.datasource},:#{#std.version},:#{#std.obj_type},:#{#std.creator},:#{#std.create_time},:#{#std.update_time}," +
			":#{#std.update_person},:#{#std.auth_status},:#{#std.auth_remark},:#{#std.category_id},:#{#std.file_id},:#{#std.db_id}," +
			":#{#std.oper_flag},:#{#std.status})", nativeQuery = true)
	void savestdbysql(@Param("std") OperDocStdModel std);

	@Modifying
	@Query(value = "delete from oper_tb_stdgl_schemamodule where id=?1 and batch_no=?2",nativeQuery = true)
	void delstdbyidandbatch(String std_id,String batch_no);

	@Modifying
	@Query(value = "update oper_tb_stdgl_schemamodule set is_auth =?1 where batch_no=?2 and id=?3",nativeQuery = true)
	void update_authtype(int is_auth,String batch_no,String id);

	@Modifying
	@Query(value = "update oper_tb_stdgl_schemamodule set status =?1 where batch_no=?2 and id=?3",nativeQuery = true)
	void update_status(int status,String batch_no,String id);
}
