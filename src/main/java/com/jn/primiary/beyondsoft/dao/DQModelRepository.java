package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据质量检测dao,用于对Model实体和数据库表操作
 * */
@Repository
public interface DQModelRepository extends JpaRepository<Model, String>{

    @Query(value = "select * from tb_meta_model where id=?1",nativeQuery = true)
    Model getmessbyid(String model_id);

}
