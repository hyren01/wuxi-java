package com.jn.primiary.office.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.office.entity.DocModelField;






public interface DocModelFieldRepository extends JpaRepository <DocModelField,Integer>{
	
	
	
	@Query(value="select * from tb_meta_modelfield where model_id=?1", nativeQuery = true)  
	List<DocModelField> findByModelid(String id);
    
}
