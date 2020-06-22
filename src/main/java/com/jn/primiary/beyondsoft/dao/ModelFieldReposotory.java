package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.ModelField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelFieldReposotory extends JpaRepository<ModelField,Integer> {

    @Query(value = "select * from tb_meta_modelfield where model_id=?1",nativeQuery = true)
    List<ModelField> getfieldbymodelid(String model_id);

    @Query(value = "select * from tb_meta_modelfield where id=?1",nativeQuery = true)
    ModelField getfieldbyid(String id);
}
