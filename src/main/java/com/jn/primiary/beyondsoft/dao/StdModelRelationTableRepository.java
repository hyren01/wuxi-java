package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.FieldCheckResult;
import com.jn.primiary.beyondsoft.entity.StdModelRelationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StdModelRelationTableRepository extends JpaRepository <StdModelRelationTable, Integer> {

	@Query(value="select * from std_model_relation_table where tb_meta_model_id=?1", nativeQuery = true)
	StdModelRelationTable getrelationbymodelid(String modelid);

	@Query(value = "select * from std_model_relation_table where tb_meta_model_id=?1",nativeQuery = true)
	StdModelRelationTable getrelation(String modelid);

	@Modifying
	@Query(value = "delete from std_model_relation_table where tb_meta_model_id=?1",nativeQuery = true)
	void deleterelation(String modelid);
}