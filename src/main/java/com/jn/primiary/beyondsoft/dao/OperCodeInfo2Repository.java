package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.OperCodeInfo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface OperCodeInfo2Repository extends JpaRepository<OperCodeInfo2,Integer> {
    @Override
    @Transactional
    <S extends OperCodeInfo2> S save(S s);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_code_info set status = ?3 where id = ?1 and batch_no = ?2",nativeQuery = true)
    void updateOperCodeInfo(String id, String batchNo, String status);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_code_info set status = ?1 where db_id = ?2",nativeQuery = true)
    void updateOperCodeInfoByDbId(String status,String dbId);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_code_info set status = ?3 where id=?1 and batch_no=?2",nativeQuery = true)
    void updateOperCodeInfoById(String id, String batchNo, String status);

    @Modifying
    @Transactional
    @Query(value = "delete from oper_tb_stdgl_code_info where id = ?1 and batch_no = ?2",nativeQuery = true)
    void deleteOperCodeInfo2ById(String id, String batchNo);

    @Query(value = "select * from oper_tb_stdgl_code_info where id = ?1 and batch_no = ?2",nativeQuery = true)
    List<OperCodeInfo2> getOperCodeInfoByBatchNo(String batchNo);

    @Query(value = "select * from oper_tb_stdgl_code_info where db_id = ?1 and status = '1'",nativeQuery = true)
    List<OperCodeInfo2> getOperCodeInfoByDbId(String dbId);

    @Query(value = "select * from oper_tb_stdgl_code_info where db_id = ?1 and codetable_ename = ?2 and status = '1'",nativeQuery = true)
    List<OperCodeInfo2> getOperCodeInfoByDbId2(String dbId,String tableName);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_code_info set codetable_ename = ?1 where id=?2 and batch_no=?3",nativeQuery = true)
    void updateOperCodeName(String codetable_ename, String id, String batchNo);
}
