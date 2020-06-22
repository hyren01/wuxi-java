package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.ModelField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据质量检测dao,用于对ModelField实体和数据库表操作
 * */
@Repository
public interface DQFieldRepository extends JpaRepository<ModelField, String>{
	@Query(value="select * from tb_meta_modelfield where model_id = ?1", nativeQuery = true)
	List<ModelField> findModelFieldByModelId(String modelID);
}
