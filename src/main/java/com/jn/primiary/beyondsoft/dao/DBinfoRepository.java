package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.Dbinfo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DBinfoRepository extends JpaRepository<Dbinfo2,Integer> {

    @Override
    <S extends Dbinfo2> S save(S s);

    @Query(value = "select * from tb_stdgl_db_table where status = '1' order by create_time desc",nativeQuery = true)
    List<Dbinfo2> getAllDbInfo();

    @Query(value = "select * from tb_stdgl_db_table where db_id = ?1 and status = '1' order by create_time desc",nativeQuery = true)
    Dbinfo2 getDbInfoById(String dbId);

    @Modifying
    @Transactional
    @Query(value = "delete from tb_stdgl_db_table where db_id = ?1",nativeQuery = true)
    void deleteDbinfo2ById(String dbId);

    @Modifying
    @Transactional
    @Query(value = "update tb_stdgl_db_table set status = ?2 where db_id = ?1",nativeQuery = true)
    void updateDbInfo2ById(String id, String isAuth);

    @Modifying
    @Transactional
    @Query(value = "update tb_stdgl_db_table set db_id = ?2 where db_id = ?1",nativeQuery = true)
    void updateDbInfo2IdById(String oldId, String newId);

    @Modifying
    @Transactional
    @Query(value = "update tb_stdgl_db_table set is_auth = ?1 where db_id = ?2 and status = 1",nativeQuery = true)
    void setDbAuthType(String is_auth,String db_id);
}
