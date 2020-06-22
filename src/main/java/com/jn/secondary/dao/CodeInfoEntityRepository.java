package com.jn.secondary.dao;

import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.secondary.entity.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CodeInfoEntityRepository extends JpaRepository<CodeInfo,Integer>{

    @Modifying
    @Query(value = "create table person(id varchar(32),name varchar(60))",nativeQuery = true)
    void createCodeInfoTable();
}
