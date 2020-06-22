package com.jn.primiary.standard.dao;

//import com.baomidou.mybatisplus.mapper.BaseMapper;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.standard.entity.StandardField;




/**
 *
 * @author wld
 * @since 2019-05-15 10:30
 */



public interface StandardFieldRepository extends JpaRepository <StandardField,Integer> {
	
	List<StandardField> findBySchemacodeId(String schemacodeId);
	
	StandardField findById(String id);
	
	
	void deleteById(String id);
	void deleteBySchemacodeId(String schemacodeId);
	
	@Query(value="select * from tb_stdgl_schemafield where fieldname LIKE CONCAT('%',?1,'%') or defination like CONCAT('%',?1,'%') or enname like CONCAT('%',?1,'%')", nativeQuery = true)
	List<StandardField> findByFieldinfo(String fieldinfo);
	
	@Query(value="select * from tb_stdgl_schemafield where (fieldname LIKE CONCAT('%',?1,'%') or defination like CONCAT('%',?1,'%') or enname like CONCAT('%',?1,'%')) and category=?2", nativeQuery = true)
	List<StandardField> findByFieldAndCategory(String fieldinfo,String category);
	  
}