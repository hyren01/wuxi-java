package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.StandardField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FieldRepository extends JpaRepository <StandardField,Integer> {


	@Query(value ="select * from tb_stdgl_schemafield where IF(?2 !='',obj_id=?2,1=1) and moudle_id=?1 and status='1'" ,nativeQuery = true)
	List<StandardField> getFieldById(String stdId, String objId);

	@Modifying
	@Query(value = "insert into tb_stdgl_schemafield " +
			"(id,fieldname,enname,fieldcode,defination,version,type,maxsize,rangee,required,comments,maxcontaincount,pxh,creator,createtime,fieldrange,status,obj_id,moudle_id) " +
			"select id,fieldname,enname,fieldcode,defination,?2,type,maxsize,rangee,required,comments,maxcontaincount,pxh,creator,createtime,fieldrange,status,obj_id,moudle_id from oper_tb_stdgl_schemafield " +
			"where batch_no=?1 and status='1' and moudle_id=?3",nativeQuery = true)
	void fieldOperTofinal(String batchNo, int version, String id);

	@Modifying
	@Query(value = "update tb_stdgl_schemafield set status='2' " +
			"where moudle_id=?1 and version=?2",nativeQuery = true)
	void updateStatus(String moudle_id, String version);

	@Modifying
	@Query(value = "update tb_stdgl_schemafield set status='1' " +
			"where moudle_id=?1 and version=?2",nativeQuery = true)
	void udpateStatusByIdAndVersion(String moudle_id, String version);

	@Modifying
	@Query(value = "select distinct(moudle_id) from tb_stdgl_schemafield where id in (?1)",nativeQuery = true)
	String[] getMoudleIdByFieleId(String[] ids);

	@Query(value ="select * from tb_stdgl_schemafield where id=?1 and status='1'" ,nativeQuery = true)
	StandardField getFieldByFieldId(String fieldId);

	@Query(value = "select id as id,fieldcode as code,enname as enName,fieldname as name," +
			"required as required,'' as defaultValue,maxsize as maxsize,type as type," +
			"'' as primary1,pxh as pxh,rangee as range1,defination as defination,maxcontaincount as maxContains," +
			"comments as comments,'' as security,'' as state from tb_stdgl_schemafield " +
			"where status='1' and moudle_id=?1",nativeQuery = true)
	List<Object[]> getFieldList(String moudleId);

	@Query(value = "select * from tb_stdgl_schemafield where moudle_id=?1 and status = 1",nativeQuery = true)
	List<StandardField> getfieldbyidandstatus(String std_id);


	@Query(value ="select * from tb_stdgl_schemafield where IF(?2 !='',obj_id=?2,1=1) and moudle_id=?1 and version=?3" ,nativeQuery = true)
	List<StandardField> getFieldByIdAndVersion(String stdId,String objId,String version);

	@Query(value ="select * from tb_stdgl_schemafield where status='1' order by moudle_id desc" ,nativeQuery = true)
	List<StandardField> getAllField();

	@Query(value ="SELECT distinct(code_id) from tb_stdgl_schemafield WHERE moudle_id=?1 and code_id != null",nativeQuery = true)
	List<String> getFieldIdByStdIdCodeIdNotNull(String stdid);


  //add by wld 2020.04.09
    @Query(value ="SELECT DISTINCT(fieldname) from tb_stdgl_schemafield where fieldcode=?1",nativeQuery = true)
    List<String> findByFieldCode(String fieldcode);

	//add by wld 2020.04.18
	@Query(value ="SELECT id from tb_stdgl_schemafield WHERE moudle_id=?1",nativeQuery = true)
	List<String> getFieldIdByStdId(String stdid);


}