package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.OperDocStdModelField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OperStdModelFieldRepository extends JpaRepository <OperDocStdModelField,Integer>{

	@Query(value="select * from oper_tb_stdgl_schemafield where moudle_id = ?1",nativeQuery = true)
	List<OperDocStdModelField> getfieldbyid (String std_id);

	@Query(value="select * from oper_tb_stdgl_schemafield where moudle_id = ?1 and status = 1 and batch_no=?2",nativeQuery = true)
	List<OperDocStdModelField> getfieldbyidandstatus (String std_id,String batch_no);

	@Query(value="select * from oper_tb_stdgl_schemafield where obj_id = ?1",nativeQuery = true)
	List<OperDocStdModelField> getfieldbyobjid (String obj_id);

	@Query(value="select * from oper_tb_stdgl_schemafield where obj_id = ?1 and status = 1 and batch_no=?2",nativeQuery = true)
	List<OperDocStdModelField> getfieldbyobjidandstatus (String obj_id,String batch_no);

	@Query(value="delete * from oper_tb_stdgl_schemafield where moudle_id = ?1",nativeQuery = true)
	List<OperDocStdModelField> delfieldbystdid (String std_id);

	@Modifying
	@Query(value="insert into oper_tb_stdgl_schemafield(batch_no,id,fieldname,enname,fieldcode,defination,type,maxsize,rangee," +
			"required,comments,maxcontaincount,pxh,creator,createtime,fieldrange,data_type,obj_id,moudle_id,version，status) values " +
			"(:#{#field.batch_no},:#{#field.field_id},:#{#field.cname},:#{#field.ename},:#{#field.code},:#{#field.definded}," +
			"(:#{#field.datatype},:#{#field.length},:#{#field.field_range},:#{#field.required},:#{#field.comments},:#{#field.maxContains}," +
			"(:#{#field.pxh},:#{#field.creator},:#{#field.createtime},:#{#field.fieldrange},:#{#field.oper_flag},:#{#field.obj_id}," +
			"(:#{#field.model_id},:#{#field.version},:#{#field.status}", nativeQuery = true)
	void savefieldbysql(@Param("field") OperDocStdModelField field);

	@Modifying
	@Query(value="delete from oper_tb_stdgl_schemafield where id=?1 and batch_no=?2", nativeQuery = true)
	void delfieldbyidandbatchno(String stdid,String batch_no);


	@Modifying
	@Query(value = "update oper_tb_stdgl_code_info set status =?1 where batch_no=?2 and id=?3",nativeQuery = true)
	void update_status(int status,String batch_no,String id);










	@Query(value="select * from oper_tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemafield where fieldcode='GloLineNum' or fieldcode='ReconTime' or fieldcode='ReconManInfo' or defination=?1 )", nativeQuery = true)
	List<OperDocStdModelField> findjxall(String description);
	
	@Query(value="select * from oper_tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemamodule where  description=?1))", nativeQuery = true)
	List<OperDocStdModelField> findcomplexall(String description);
	
	@Query(value="select * from oper_tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemamodule where description=?1 )", nativeQuery = true)
	List<OperDocStdModelField> findduanall(String description);
	
	@Query(value="select * from oper_tb_stdgl_schemafield where schemacodeid=(select schemacodeid from tb_stdgl_schemafield where id=?1)", nativeQuery = true)
	List<OperDocStdModelField> findallfieldbyfieldid(String id);



}
