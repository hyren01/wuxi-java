package com.jn.primiary.office.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.office.entity.DocModel;






public interface StdModuleRepository extends JpaRepository <DocModel,Integer>{
	
	@Query(value="select * from tb_meta_model where std_source=?1", nativeQuery = true)  
	List<DocModel> findByStdsource(String stdsource);

}
