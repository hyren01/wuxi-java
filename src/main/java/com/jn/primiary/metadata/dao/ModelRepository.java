package com.jn.primiary.metadata.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.metadata.entity.Model;


/**
 * Created by gongyf on 2018/04/12.
 */
public interface ModelRepository extends JpaRepository<Model, String> {
	
	Model findById(String id);
	
	@Query(value="SELECT * from tb_meta_model where type=0", nativeQuery = true)
	List<Model> findTableModel();
	
	
	@Query(value="select * from tb_meta_model where type=0 order by create_time desc", nativeQuery = true)  
	List<Model> findAllOrderByCreateTimeDesc();

	@Query(value="select * from tb_meta_model where id=?1", nativeQuery = true)
	Model findbymodelid(String id);
	
	
	//查找没有中文名的模型 
	//add by wld 2020.04.09
	@Query(value="SELECT * from tb_meta_model_wld where en_name=name", nativeQuery = true)
	List<Model> findModelnocnname();
	
	//add by wld 2020.04.09
	@Modifying
    @Query(value = "update tb_meta_model_wld set name=?1 where en_name=?2",nativeQuery = true)
    void updateCnname(String cnname, String en_name);
	
	
	
	
	
}
