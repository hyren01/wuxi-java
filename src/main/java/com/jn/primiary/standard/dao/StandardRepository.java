package com.jn.primiary.standard.dao;

//import com.baomidou.mybatisplus.mapper.BaseMapper;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.standard.entity.Standard;



/**
 *
 * @author wld
 * @since 2019-05-15 10:30
 */



public interface StandardRepository extends JpaRepository <Standard,Integer> {
	
	List<Standard> findByCategoryId(String categoryId);
	
	List<Standard> findByDataSource(String datasource);
	Standard findById(String id);
	
	Standard findByCode(String code);
	@Query(value="select distinct(datasource) from tb_stdgl_schemamodule", nativeQuery = true)
	List<String> findAllDataSource();
	
	@Query(value="select * from tb_stdgl_schemamodule where  schemaname LIKE CONCAT('%',?1,'%') or description like CONCAT('%',?1,'%') or enname like CONCAT('%',?1,'%')", nativeQuery = true)
	List<Standard>  findByModelInfo(String Modelinfo);
	
	void deleteById(String stdId);
	
	
	Long countById(String stdId);
	
	Long countByCode(String code);
	  
}