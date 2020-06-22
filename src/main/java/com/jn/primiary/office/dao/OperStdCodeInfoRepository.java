package com.jn.primiary.office.dao;

import com.jn.primiary.office.entity.OperDocStdCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OperStdCodeInfoRepository extends JpaRepository <OperDocStdCodeInfo,Integer>{

    @Modifying
    @Query(value="select * from oper_tb_stdgl_code_info where file_id=?1 order by codetable_ename desc,codetable_cname desc,create_time desc", nativeQuery = true)
    List<OperDocStdCodeInfo> get_codeinfo_mess(String file_id);


    @Query(value="select * from oper_tb_stdgl_code_info where file_id=?1 and status=1 order by create_time desc", nativeQuery = true)
    List<OperDocStdCodeInfo> get_codeinfo_mess_bystatus(String file_id);

    @Modifying
    @Query(value="delete * from oper_tb_stdgl_code_info where file_id=?1", nativeQuery = true)
    void delcodeinfobyfileid(String file_id);

    @Modifying
    @Query(value="insert into oper_tb_stdgl_code_info(batch_no,id,codetable_cname,codetable_ename,code_value,remark,auth_remark,create_user," +
            "create_time,file_id,status,update_user,update_time,data_type,is_auth,audit_user,audit_time) values " +
            "(:#{#code.batch_no},:#{#code.code_id},:#{#code.codetable_cname},:#{#code.codetable_ename},:#{#code.code_value},:#{#code.remark}" +
            "(:#{#code.auth_remark},:#{#code.creator},:#{#code.create_time},:#{#code.file_id},:#{#code.status},:#{#code.update_user}" +
            "(:#{#code.update_time},:#{#code.oper_flag},:#{#code.auth_status},:#{#code.auth_status}" +
            "(:#{#code.audit_user},:#{#code.audit_time}", nativeQuery = true)
    void savecodeinfobysql(@Param("code") OperDocStdCodeInfo code);

    @Modifying
    @Query(value = "update oper_tb_stdgl_code_info set is_auth =?1 where batch_no=?2 and id=?3",nativeQuery = true)
    void update_authtype(int is_auth,String batch_no,String id);

    @Modifying
    @Query(value = "update oper_tb_stdgl_code_info set status =?1 where batch_no=?2 and id=?3",nativeQuery = true)
    void update_status(int status,String batch_no,String id);
}
