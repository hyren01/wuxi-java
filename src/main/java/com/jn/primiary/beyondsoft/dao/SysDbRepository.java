package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.FieldCheckResult;
import com.jn.primiary.beyondsoft.entity.SysDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SysDbRepository extends JpaRepository <SysDb, Integer> {

	@Override
	List<SysDb> findAll();

	@Query(value = "select * from tb_stdgl_sysdb where sys_name = ?1",nativeQuery = true)
	List<SysDb> findSysDbBySysName(String sys_name);
}