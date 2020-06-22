package com.jn.primiary.beyondsoft.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jn.primiary.beyondsoft.entity.Checklist;

import java.util.List;

public interface CheckListRepository extends JpaRepository<Checklist,Integer> {
    @Query(value = "select * from tb_stdgl_checklist where modelcode = ?1",nativeQuery = true)
    List<Checklist> findByModelCode(String modelcode);

    @Query(value = "select distinct (modelcode) from tb_stdgl_checklist ",nativeQuery = true)
    List<String> getAllmodelcode();

}
