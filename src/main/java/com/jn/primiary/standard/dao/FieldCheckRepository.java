package com.jn.primiary.standard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jn.primiary.standard.entity.FieldCheckResult;





/**
 *
 * @author wld
 * @since 2019-05-15 10:30
 */
public interface FieldCheckRepository extends JpaRepository <FieldCheckResult, Integer> {
	
	FieldCheckResult findByFieldId(String fieldId);
	
	void deleteById(String id);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM tb_meta_modelfield_checkresult WHERE field_id = ?1", nativeQuery = true)
	void deleteByFieldId(String fieldId);
	  
}