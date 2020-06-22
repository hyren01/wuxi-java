package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    @Query(value="select pt.permission_id,pt.permission_name,pt.permission_type,pt.permission_remark from " +
            "permission_table pt join permission_role_table prt on pt.permission_id= prt.permission_id where prt.role_id=?1",nativeQuery = true)
    List<Permission> findbyroleid(String role_id);
}
