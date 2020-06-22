package com.jn.primiary.beyondsoft.dao;

import com.alibaba.fastjson.JSONArray;
import com.jn.primiary.beyondsoft.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ModuleRepository extends JpaRepository <Standard,Integer> {


	@Query(value ="select date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime," +
			"date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,a.*from tb_stdgl_schemamodule a where status='1'" ,nativeQuery = true)
	List<Standard> getAllStandardInfo();

	@Query(value ="select date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime," +
			"date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,a.* from tb_stdgl_schemamodule a where IF(?2 !='',version=?2 and status='2',status='1') and id=?1 " ,nativeQuery = true)
	Standard getStandardById(String stdId, String version);

	@Modifying
	@Query(value = "update tb_stdgl_schemamodule " +
			"set status='2'" +
			"where batch_no=?1 and moudle_id=?2",nativeQuery = true)
	void update(String batchNo, String moudle_id);

	@Modifying
	@Query(value = "insert into tb_stdgl_schemamodule " +
			"(id,schemaname,enname,schemacode,description,datasource,version,type,creator,createtime,update_time,update_person,auth_remark,categoryid,status,file_id,db_id) " +
			"select id,schemaname,enname,schemacode,description,datasource,?2,type,creator,createtime,update_time,update_person,auth_remark,categoryid,status,file_id,db_id from oper_tb_stdgl_schemamodule " +
			"where batch_no=?1 and id=?3",nativeQuery = true)
	void moudleOperTofinal(String batchNo, int version, String id);

	@Query(value = "select version from tb_stdgl_schemamodule where id=?1 and status='1'",nativeQuery = true)
	String getVersion(String moudle_id);

	@Modifying
	@Query(value = "update tb_stdgl_schemamodule set status='2' " +
			"where id=?1 and version=?2",nativeQuery = true)
	 void updateStatus(String moudle_id, String version);

	@Query(value ="select a.id as id,a.schemacode as code,a.schemaname as name,a.enname as enName,date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime," +
			"date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,a.version as version from tb_stdgl_schemamodule a where status=2 and a.id=?1" ,nativeQuery = true)
	List<Object[]> getOldVersionList(String moudle_id);

	@Modifying
	@Query(value = "update tb_stdgl_schemamodule set status='1' " +
			"where id=?1 and version=?2",nativeQuery = true)
	void udpateStatusByIdAndVersion(String moudle_id, String version);

	@Query(value="select max(version) from tb_stdgl_schemamodule where schemacode = ?1",nativeQuery = true)
	String getstdbycode(String code);

	@Query(value = "select * from tb_stdgl_schemamodule where schemacode = ?1 order by version desc " ,nativeQuery = true)
	List<Standard> getAllBySchemeCode(String schemacode);

	@Query(value ="select id as id,schemacode as code,enname as enName,schemaname as name," +
			"type as type,date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime," +
			"creator as createPerson,date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime," +
			"update_person as updatePerson,description as description,version as version," +
			"categoryid as categoryId from tb_stdgl_schemamodule a " +
			"where status='1' and IF(?1 !='',categoryid=?1,1=1) and IF(?2 !='',id=?2,1=1)" ,nativeQuery = true)
	List<Object[]> getStandardByCategoryId(String categoryId, String id);

	@Query(value = "select * from tb_stdgl_schemamodule where file_id=?1 and status = 1",nativeQuery = true)
	List<Standard> getStandByFileId(String file_id);

	@Query(value="select * from tb_stdgl_schemamodule where id=?1 and status = 1",nativeQuery = true)
	Standard getstdmessbyidandstatus(String std_id);


	@Modifying
	@Query(value = "update tb_stdgl_schemamodule set count = ?1 where id=?2 and version=?3" ,nativeQuery = true)
	void updateStandCount(String count , String moudle_id, String version);


	@Query(value = "select b.categoryid from tb_stdgl_schemafield a inner join tb_stdgl_schemamodule b on a.moudle_id = b.id where a.id = ?1" ,nativeQuery = true)
	String getCategoryIdByFieldId(String fieldId);

}