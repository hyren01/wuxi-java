package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.DbInfo3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbInfo3Repository extends JpaRepository<DbInfo3,Integer> {
    @Query(value = "select * from db_info",nativeQuery = true)
    List<DbInfo3> getAllDb();

}
