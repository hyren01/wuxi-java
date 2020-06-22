package com.jn.primiary.office.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jn.primiary.office.entity.DocStdModelField;






public interface StdModelFieldRepository extends JpaRepository <DocStdModelField,Integer>{
	
	
	
	@Query(value="select * from tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemafield where fieldcode='GloLineNum' or fieldcode='ReconTime' or fieldcode='ReconManInfo' or defination=?1 )", nativeQuery = true)  
	List<DocStdModelField> findjxall(String description);
	
	@Query(value="select * from tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemamodule where  description=?1))", nativeQuery = true)
	List<DocStdModelField> findcomplexall(String description);
	
	@Query(value="select * from tb_stdgl_schemafield where schemacodeid in(select id from tb_stdgl_schemamodule where description=?1 )", nativeQuery = true)  
	List<DocStdModelField> findduanall(String description);
	
	@Query(value="select * from tb_stdgl_schemafield where schemacodeid=(select schemacodeid from tb_stdgl_schemafield where id=?1)", nativeQuery = true)  
	List<DocStdModelField> findallfieldbyfieldid(String id);
	
	
	
    
}
