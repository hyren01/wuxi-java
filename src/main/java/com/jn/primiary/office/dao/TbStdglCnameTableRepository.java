package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.TbStdglCnameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TbStdglCnameTableRepository extends JpaRepository<TbStdglCnameTable,Integer> {
    @Query(value="select * from tb_stdgl_cname_table where cname=?1", nativeQuery = true)
    TbStdglCnameTable getEname(String cname);

    @Query(value="select * from tb_stdgl_cname_table where cname like CONCAT(?1,'%')", nativeQuery = true)
    List<TbStdglCnameTable> getSimpleEname(String cname);

}
