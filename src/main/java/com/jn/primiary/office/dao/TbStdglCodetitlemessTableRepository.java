package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.TbStdglCodetitlemessTable;
import com.jn.primiary.office.entity.TbStdglFiletable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TbStdglCodetitlemessTableRepository extends JpaRepository<TbStdglCodetitlemessTable,Integer> {
    @Query(value="select * from tb_stdgl_codetitlemess_table where codeinfo_title=?1", nativeQuery = true)
    TbStdglCodetitlemessTable getcodevalue(String title);
}
