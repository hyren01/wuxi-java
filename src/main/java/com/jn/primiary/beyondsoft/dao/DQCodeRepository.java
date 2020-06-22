package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据质量检测dao,用于对码表实体和数据库表操作
 * */
@Repository
public interface DQCodeRepository extends JpaRepository<CodeInfo, String>{
	
	@Query(value="SELECT * FROM tb_stdgl_code_info WHERE is_auth = ?1 GROUP BY code_ename", nativeQuery = true)
	List<CodeInfo> getValueType(String isAuth);
	
	@Query(value="select * from tb_stdgl_code_info WHERE is_auth = ?1 AND code_ename = ?2", nativeQuery = true)
	List<CodeInfo> getCodeValueByType(String isAuth, String codeEname);
}
