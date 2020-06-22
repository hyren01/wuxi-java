package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysRoleRepository extends JpaRepository<SysRole,Integer> {

    @Query(value = "select sr.role_id,sr.role_name,sr.remark,sr.type from sys_role sr join user_role_table urt on sr.role_id=urt.role_id " +
            "where urt.user_id = ?1",nativeQuery = true)
    List<SysRole> findbyuserid(String userid);
}
