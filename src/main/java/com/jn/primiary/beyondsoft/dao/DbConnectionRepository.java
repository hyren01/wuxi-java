package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.DbInfo3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbConnectionRepository extends JpaRepository<DbInfo3,Integer> {
    @Override
    <S extends DbInfo3> S save(S s);
}
