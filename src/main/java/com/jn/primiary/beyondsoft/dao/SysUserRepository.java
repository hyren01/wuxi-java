package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysUserRepository extends JpaRepository<SysUser,Integer> {
    @Query(value = "select * from sys_user where username = ?1",nativeQuery = true)
    SysUser findbyusername(String username);

    @Query(value = "select * from sys_user where user_id = ?1",nativeQuery = true)
    SysUser findbyid(String id);
}
