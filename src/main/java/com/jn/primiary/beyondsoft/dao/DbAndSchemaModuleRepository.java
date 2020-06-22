package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.DbAndSchemaModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbAndSchemaModuleRepository extends JpaRepository<DbAndSchemaModule,String> {

    @Query(value = "select a.id,a.batch_no,a.db_id,a.status,a.createtime,a.description,a.data_type,b.db_name,b.db_type,a.is_auth,a.schemacode,a.version from oper_tb_stdgl_schemamodule a,tb_stdgl_db_table b where  a.db_id = b.db_id order by a.createtime desc ",nativeQuery = true)
    List<Object[]> getModuleAndDatabaseInfo();
}
