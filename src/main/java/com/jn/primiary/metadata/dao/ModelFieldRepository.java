package com.jn.primiary.metadata.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.metadata.entity.ModelField;


/**
 * Created by gongyf on 2018/04/12.
 */
public interface ModelFieldRepository extends JpaRepository<ModelField, String> {
	
	ModelField findById(String id);
	
	@Query(value="select * from tb_meta_modelfield where model_id=?1 order by pxh", nativeQuery = true)
	List<ModelField> findByModelId(String modelId);	
	
	List<ModelField> findByCode(String code);
	
	void deleteByModelId(String modelId);
	
	// 查找非fmb模型的字段
	@Query(value="select a.* from tb_meta_modelfield a, tb_meta_model b where a.model_id=b.id and b.type=0", nativeQuery = true)
	List<ModelField> findAllExceptFmb();
	
	//add by wld 2020.04.09
	//查找没有中文名的字段
	@Query(value="SELECT * from  tb_meta_modelfield_wld WHERE code=name", nativeQuery = true)
	List<ModelField> findFieldnocnname();
	
	
	//add by wld 2020.04.10
	@Modifying
	@Query(value = "UPDATE tb_meta_modelfield_wld set name=?1  where code=?2 and code=name",nativeQuery = true)
	void updateCnname(String cnname, String en_name);




}
