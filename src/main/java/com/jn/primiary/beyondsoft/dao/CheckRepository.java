package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.FieldCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CheckRepository extends JpaRepository <FieldCheckResult, Integer> {

	@Query(value = "select * FROM tb_meta_modelfield_checkresult WHERE field_id = ?1", nativeQuery = true)
	FieldCheckResult findByFieldId(String fieldId);

	void deleteById(String id);

	@Modifying
	@Query(value = "DELETE FROM tb_meta_modelfield_checkresult WHERE field_id = ?1", nativeQuery = true)
	void deleteByFieldId(String fieldId);

	@Query(value = "select * FROM tb_meta_modelfield_checkresult " +
			"where moudle_id=?1 and field_id=?2", nativeQuery = true)
	FieldCheckResult getCheckMoudle(String moudleId, String fieldId);

	@Query(value = "select * FROM tb_meta_modelfield_checkresult WHERE moudle_id = ?1", nativeQuery = true)
	List<FieldCheckResult> findByMoudleId(String moudleId);

	@Query(value = "select stdname FROM tb_meta_modelfield_checkresult WHERE moudle_id = ?1 group by stdname", nativeQuery = true)
	List<String> getStdIdByMoudleId(String moudleId);

	@Query(value = "select * FROM tb_meta_modelfield_checkresult", nativeQuery = true)
	List<FieldCheckResult> findAllCheckResult();

	@Query(value = "select * FROM tb_meta_modelfield_checkresult where moudle_id in (?1)", nativeQuery = true)
	List<FieldCheckResult> findCheckResultInMoudleIds(List<String> moudleIds);

	@Query(value = "select moudle_id from tb_meta_modelfield_checkresult group by moudle_id", nativeQuery = true)
	List<String> findAllMouldId();

	@Query(value = "select * FROM tb_meta_modelfield_checkresult WHERE moudle_id = ?1 and field_id = ?2", nativeQuery = true)
	FieldCheckResult findByMoudleIdAndFieldId(String moudleId,String fieldId);

	@Query(value = "select name as moudleName,en_name as enName,code as moudleCode,description as moudleDesc," +
			"date_format(update_time,'%Y-%m-%d %h:%m:%s') as udpateDate,null as status," +
			"id as moudleId,null as isCheck,null as isCheckModify " +
			"from tb_meta_model order by update_time desc", nativeQuery = true)
	List<Object[]> getMoudleList();

	@Query(value = "select name as moudleName,en_name as enName,code as moudleCode," +
			"null as checkDate,null as checkResult,id as fieleId,model_id as moudleId " +
			"from tb_meta_modelfield where model_id=?1", nativeQuery = true)
	List<Object[]> getMoudleFieldList(String id);

	@Query(value = "select * FROM tb_meta_modelfield_checkresult WHERE stdname = ?1", nativeQuery = true)
	List<FieldCheckResult> findByStdId(String stdId);

	//add by wld 2020.04.10
	@Query(value = "select * FROM tb_meta_modelfield_checkresult WHERE stdproperty = ?1", nativeQuery = true)
	List<FieldCheckResult> findByStdProperty(String stdproperty);

	//add by mqy
	@Query(value = "select id FROM tb_stdgl_schemamodule WHERE categoryid in (select id from tb_stdgl_category where parent_id='1'or parent_id='af59f789-6bfe-430f-b04f-a692fde4c52b')", nativeQuery = true)
	List<String> findallStd();

	@Query(value = "select moudle_id FROM tb_meta_modelfield_checkresult WHERE stdname = ?1", nativeQuery = true)
	List<String> findByStdname(String stdname);
}