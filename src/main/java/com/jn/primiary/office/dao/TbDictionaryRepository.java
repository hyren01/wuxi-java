package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.TbDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TbDictionaryRepository extends JpaRepository <TbDictionary,Integer>{

    @Query(value = "select * from tb_dictionary where translation=?1",nativeQuery = true)
	public List<TbDictionary> findenamebycname(String word);

    @Query(value = "select * from tb_dictionary where translation like CONCAT(?1,'_') union " +
            "select * from tb_dictionary where translation like CONCAT('_',?1)",nativeQuery = true)
    public List<TbDictionary> findenamebycnamefuzzy(String word);
}
