package com.jn.primiary.office.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.office.entity.DocStdModel;






public interface StdModleRepository extends JpaRepository <DocStdModel,Integer>{
	
	
	
	
	@Query(value="select * from tb_stdgl_schemamodule_wld where id=(select model_id from tb_stdgl_schemafield_wld where id=?1)", nativeQuery = true)  
	DocStdModel findmodelbyfieldid(String fieldid);
	
}
