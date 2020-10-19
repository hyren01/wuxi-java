package com.jn.primiary.beyondsoft.dao;

import com.jn.primiary.beyondsoft.entity.OperCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OperCodeInfoRepository extends JpaRepository<OperCodeInfo, Integer> {

    @Query(value = "select codetableEname,codetableCname,createTime,createUser,isAuth,batchNo,dataType from (" +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "is_auth as isAuth,batch_no as batchNo,data_type as dataType from oper_tb_stdgl_code_info a " +
            "left join tb_stdgl_code_info b on a.id=b.id where is_auth in ('3','5')" +
            "union " +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "'1' as isAuth,null as batchNo,null as dataType from tb_stdgl_code_info a " +
            "left join oper_tb_stdgl_code_info b on a.id=b.id where a.status='1'" +
            ") a order by isAuth desc,createTime desc", nativeQuery = true)
    List<Object[]> getAllCodeInfo();

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_code_info(batch_no,id,codetable_cname," +
            "codetable_ename,code_cname,code_ename,code_value,remark,auth_remark," +
            "create_user,create_time,file_id,status,update_user,update_time," +
            "data_type,is_auth,audit_user,audit_time)" +
            "values(:#{#oc.batchNo},:#{#oc.id},:#{#oc.codetableCname},:#{#oc.codetableEname}" +
            ",:#{#oc.codeCname},:#{#oc.codeEname},:#{#oc.codeValue},:#{#oc.remark}" +
            ",:#{#oc.authRemark},:#{#oc.createUser},:#{#oc.createTime},:#{#oc.fileId}" +
            ",:#{#oc.status},:#{#oc.updateUser},:#{#oc.updateTime},:#{#oc.dataType}," +
            ":#{#oc.isAuth},'',null)", nativeQuery = true)
    void saveOperCodeInfo(@Param("oc") OperCodeInfo oc);

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_code_info " +
            "(id,codetable_cname,codetable_ename,code_cname,code_ename,code_value,remark,is_auth,auth_remark,create_user,create_time,file_id,status,update_user,update_time,batch_no,data_type) " +
            "select id,codetable_cname,codetable_ename,code_cname,code_ename,code_value,remark,?1,auth_remark,create_user,current_timestamp,file_id,status,update_user,update_time,?2,?3 from tb_stdgl_code_info " +
            "where codetable_ename=?4 and status='1' ", nativeQuery = true)
    void deleteCodeInfo(String authStatus, String batchNo, String dataType, String codeName);

    @Query(value = "select " +
//            " ifnull(date_format(create_time,'%Y-%m-%d %h:%m:%s'),'') as create_time," +
//            " ifnull(date_format(update_time,'%Y-%m-%d %h:%m:%s'),'') as update_time," +
//            " ifnull(date_format(audit_time,'%Y-%m-%d %h:%m:%s'),'') as audit_time,a.* " +
            " a.* from oper_tb_stdgl_code_info a where codetable_ename=?1 and batch_no=?2 and status='1'", nativeQuery = true)
    List<OperCodeInfo> getCodeInfoByName(String codeName, String barch_no);

    @Query(value = "select distinct(codetable_ename) as ename,codetable_cname as cname,codetable_cname as code," +
            "date_format(create_time,'%Y-%m-%d %h:%m:01') as createTime,create_user as createUser,is_auth as isAuth," +
            "audit_user as auditUser,date_format(audit_time,'%Yg-%m-%d %h:%m:01') as auditTime," +
            "batch_no as batchNo,'码表' as type,data_type as dataType,'' as moudleId,file_id as fileId,db_id as db_id from oper_tb_stdgl_code_info " +
            "where is_auth='3' order by createTime desc", nativeQuery = true)
    List<Object[]> getOperCodeInfoList();

    @Modifying
    @Query(value = "update oper_tb_stdgl_code_info set is_auth=?1,auth_remark=?2 " +
            "where batch_no=?3 and codetable_ename=?4", nativeQuery = true)
    void audit(String isAuth, String authRemark, String batchNo, String codeName);

    @Query(value = "select * from oper_tb_stdgl_code_info " +
            "where file_id=?1 and is_auth!=?2", nativeQuery = true)
    List<OperCodeInfo> getOperCodeInfoByFileId(String fileId, String isAuth);

    @Query(value = "select * from oper_tb_stdgl_code_info " +
            "where db_id=?1 and is_auth!=?2", nativeQuery = true)
    List<OperCodeInfo> getOperCodeInfoByDbId(String dbId, String isAuth);

    @Query(value = "select count(*) from oper_tb_stdgl_code_info " +
            "where batch_no=?1 and is_auth='5'", nativeQuery = true)
    int getCountByBatchNo(String batchNo);

    @Modifying
    @Query(value = "delete from oper_tb_stdgl_code_info " +
            "where batch_no=?1 and is_auth='5'", nativeQuery = true)
    void deleteByBatchNo(String batchNo);

    @Query(value = "select codetable_ename from tb_stdgl_code_info where codetable_ename = ?1", nativeQuery = true)
    List<String> isExist(String codetable_ename);


    @Modifying
    @Query(value = "update oper_tb_stdgl_code_info set is_auth='3' " +
            "where batch_no=?1 and is_auth='5'", nativeQuery = true)
    void doAudit(String batchNo);

    @Modifying
    @Query(value = "select DISTINCT(codetable_ename) from tb_stdgl_code_info", nativeQuery = true)
    List<String> getcodetableename();

    @Modifying
    @Query(value = "delete from oper_tb_stdgl_code_info " +
            "where batch_no=?1 and codetable_ename=?2", nativeQuery = true)
    void deleteByIdAndBatchNo(String batchNo, String codeName);

    @Query(value = "select codetableEname,codetableCname,createTime,createUser,isAuth,batchNo,dataType from (" +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "is_auth as isAuth,batch_no as batchNo,data_type as dataType from oper_tb_stdgl_code_info a " +
            "where is_auth in ('3','5')" +
            "union " +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "'1' as isAuth,null as batchNo,null as dataType from tb_stdgl_code_info a " +
            "where a.status='1'" +
            ") a order by isAuth desc,createTime desc limit :startindex,:pagesize", nativeQuery = true)
    List<Object[]> getAllCodeInfoByPage(@Param("startindex") Integer startindex, @Param("pagesize") Integer size);


    @Query(value = "select count(*) from (" +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "is_auth as isAuth,batch_no as batchNo,data_type as dataType from oper_tb_stdgl_code_info a " +
            "where is_auth in ('3','5')" +
            "union " +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "'1' as isAuth,null as batchNo,null as dataType from tb_stdgl_code_info a " +
            "where a.status='1'" +
            ") a order by isAuth asc,createTime desc", nativeQuery = true)
    Integer getAllCodeInfoCount();

    @Query(value = "select * from (" +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "is_auth as isAuth,batch_no as batchNo,data_type as dataType from oper_tb_stdgl_code_info a " +
            "where is_auth in ('3','5') " +
            " and ( a.codetable_ename like CONCAT('%',:searchword,'%') or a.codetable_cname like CONCAT('%',:searchword,'%') " +
            "or a.code_cname like CONCAT('%',:searchword,'%') or a.code_ename like CONCAT('%',:searchword,'%') )" +
            "union " +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "'1' as isAuth,null as batchNo,null as dataType from tb_stdgl_code_info a " +
            "where a.status='1'" +
            " and ( a.codetable_ename like CONCAT('%',:searchword,'%') or a.codetable_cname like CONCAT('%',:searchword,'%') " +
            "or a.code_cname like CONCAT('%',:searchword,'%') or a.code_ename like CONCAT('%',:searchword,'%') )" +
            ") tmp  order by isAuth asc,createTime desc limit :startindex,:pagesize", nativeQuery = true)
    List<Object[]> searchCodeInfo(@Param("searchword") String searchword, @Param("startindex") Integer startindex, @Param("pagesize") Integer size);

    @Query(value = "select count(*) from (" +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "is_auth as isAuth,batch_no as batchNo,data_type as dataType from oper_tb_stdgl_code_info a " +
            "where is_auth in ('3','5') " +
            " and ( a.codetable_ename like CONCAT('%',:searchword,'%') or a.codetable_cname like CONCAT('%',:searchword,'%') " +
            "or a.code_cname like CONCAT('%',:searchword,'%') or a.code_ename like CONCAT('%',:searchword,'%') )" +
            "union " +
            "select distinct(a.codetable_ename) as codetableEname,a.codetable_cname as codetableCname," +
            "date_format(a.create_time,'%Y-%m-%d %h:%m:01') as createTime,a.create_user as createUser," +
            "'1' as isAuth,null as batchNo,null as dataType from tb_stdgl_code_info a " +
            "where a.status='1'" +
            " and ( a.codetable_ename like CONCAT('%',:searchword,'%') or a.codetable_cname like CONCAT('%',:searchword,'%') " +
            "or a.code_cname like CONCAT('%',:searchword,'%') or a.code_ename like CONCAT('%',:searchword,'%') )" +
            ") tmp order by isAuth asc,createTime desc", nativeQuery = true)
    Integer searchCodeInfoCount(@Param("searchword") String searchword);

}