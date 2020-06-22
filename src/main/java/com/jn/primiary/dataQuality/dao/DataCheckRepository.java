package com.jn.primiary.dataQuality.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jn.primiary.dataQuality.entity.TbMetaQuality;

public interface DataCheckRepository extends JpaRepository <TbMetaQuality, Integer> {
	
	TbMetaQuality findQualityInfoById(String id);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE tb_meta_quality SET check_status = ?2, exception_num = ?3, detail_path = ?4 WHERE id = ?1", nativeQuery = true)
	void updateQualityCheckStatusAndResult(String id, String checkStatus, BigDecimal exceptionNum, String detailPath);

}

