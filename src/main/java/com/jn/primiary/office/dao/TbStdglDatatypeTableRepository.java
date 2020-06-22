package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.TbStdglDatatypeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TbStdglDatatypeTableRepository extends JpaRepository<TbStdglDatatypeTable,Integer> {
    @Query(value="select * from tb_stdgl_datatype_table where type_cname=?1", nativeQuery = true)
    TbStdglDatatypeTable getType(String type);
}
