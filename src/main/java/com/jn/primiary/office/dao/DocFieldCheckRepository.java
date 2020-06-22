package com.jn.primiary.office.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.office.entity.DocFieldCheckResult;






public interface DocFieldCheckRepository extends JpaRepository <DocFieldCheckResult,Integer>{
	
	
	@Query(value="select * from tb_meta_modelfield_checkresult where field_id=?1", nativeQuery = true)  
	DocFieldCheckResult findByFieldid(String fieldid);
	
		
    
}
