package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CodeInfoRepository extends JpaRepository <CodeInfo, Integer> {


    @Query(value = "select ifnull(date_format(create_time,'%Y-%m-%d %h:%m:%s'),'') as createTime," +
            " ifnull(date_format(update_time,'%Y-%m-%d %h:%m:%s'),'') as updateTime,a.* " +
            "from tb_stdgl_code_info a " +
            "where codetable_ename=?1 and status='1' ",nativeQuery = true)
    List<CodeInfo> getCodeInfoByName(String codeName);

    @Query(value = "select ifnull(date_format(create_time,'%Y-%m-%d %h:%m:%s'),'') as createTime," +
            " ifnull(date_format(update_time,'%Y-%m-%d %h:%m:%s'),'') as updateTime,a.* " +
            "from tb_stdgl_code_info a " +
            "where id not in ?2 and codetable_ename=?1 and status='1' ",nativeQuery = true)
    List<CodeInfo> getCodeInfoByNameIsExists(String codeName, String[] ids);

    @Modifying
    @Query(value = "insert into tb_stdgl_code_info " +
            "(id,codetable_cname,codetable_ename,code_cname,code_ename,code_value,remark,auth_remark,create_user,create_time,file_id,status,update_user,update_time) " +
            "select id,codetable_cname,codetable_ename,code_cname,code_ename,code_value,remark,auth_remark,create_user,create_time,file_id,status,update_user,update_time from oper_tb_stdgl_code_info " +
            "where batch_no=?1 and codetable_ename=?2",nativeQuery = true)
    void codeInfoOperTofinal(String batchNo, String codeName);

    @Modifying
    @Query(value = "update tb_stdgl_code_info set status='2' " +
            "where codetable_ename=? and status='1'",nativeQuery = true)
    void deleteCodeInfo(String codeName);

    @Query(value = "select * from tb_stdgl_code_info where file_id=?1 and status = 1",nativeQuery = true)
    List<CodeInfo> getCodeInfoByFileId(String file_id);

    @Query(value = "select * from oper_tb_stdgl_code_info where batch_no=?1 and codetable_ename=?2",nativeQuery = true)
    List<CodeInfo> getcodebybatchnoandename(String batch_no,String codetable_ename);
}