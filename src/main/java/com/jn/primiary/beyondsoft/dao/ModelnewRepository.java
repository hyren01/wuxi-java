package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Created by wld on 2020/04/16.
 */
public interface ModelnewRepository extends JpaRepository<Model, String> {
	
	Model findById(String id);
	

	
	
	
	
	
}
