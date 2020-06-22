package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.TbStdglCodePrefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TbStdglCodePrefixRepository extends JpaRepository <TbStdglCodePrefix,Integer>{

	@Query(value="select * from tb_stdgl_code_prefix where filename=?1", nativeQuery = true)
	TbStdglCodePrefix getPrefixByfilename(String filename);

}
