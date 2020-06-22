package com.jn.primiary.standard.dao;

//import com.baomidou.mybatisplus.mapper.BaseMapper;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jn.primiary.standard.entity.StandardCategory;


/**
 *
 * @author wld
 * @since 2019-05-15 10:30
 */



public interface StandardCategoryRepository extends JpaRepository <StandardCategory,Integer> {
	
	
	
	  
	List<StandardCategory> findByParentId(String parentId);
	
	
	  
}