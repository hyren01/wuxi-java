package com.jn.primiary.beyondsoft.dao;


import com.jn.primiary.beyondsoft.entity.OperStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OperModuleRepository extends JpaRepository<OperStandard,Integer> {

    @Query(value ="select code,name,createPerson,createTime, isAuth,updateTime,id,batchNo,dataType,categoryId,count from ( " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,is_auth as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,batch_no as batchNo," +
            "data_type as dataType,categoryid as categoryId,version as version,'0' as count from oper_tb_stdgl_schemamodule a where is_auth in ('3','5')" +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "null as dataType,categoryid as categoryId,version as version,count as count from tb_stdgl_schemamodule a where status='1'" +
            ") a where IF(?1 !='',categoryid=?1,1=1)" +
            "order by isAuth desc,createTime desc" ,nativeQuery = true)
    List<Object[]> getAllStandardInfo(String categoryId);

        @Query(value ="select code,name,createPerson,createTime, isAuth,updateTime,id,batchNo,dataType,categoryId from ( " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "null as dataType,categoryid as categoryId,version as version from tb_stdgl_schemamodule a where status='1'" +
            ") b where IF(?1 !='',categoryid=?1,1=1)" +
            "order by isAuth desc,createTime desc" ,nativeQuery = true)
    List<Object[]> getAllSHTGStandardInfo(String categoryId);


    @Query(value =  "select a.id as id,a.batch_no as batchNo,a.schemacode as code,a.schemaname as name," +
            "a.enname as enName,a.creator as createPerson," +
            "date_format (a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,a.is_auth as isAuth," +
            "date_format (a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime," +
            "a.data_type as dataType,a.categoryid as categoryId,a.db_id as db_id,a.version as version " +
            "from oper_tb_stdgl_schemamodule a where db_id = ?1 and status = '1'"+
            "union "+
            "select b.id as id,b.batch_no as batchNo,b.codetable_ename as code,b.codetable_cname as name," +
            "b.codetable_ename as enName,b.create_user as createPerson,"+
            "b.create_time as createTime,b.is_auth as isAuth,"+
            "b.update_time as updateTime,b.data_type as dataType,"+
            "null as categoryId,b.db_id as db_id,null as version from oper_tb_stdgl_code_info b where b.db_id = ?1 and status = '1' group by b.codetable_ename",nativeQuery = true)
    List<Object[]> getOperStandardAndCodeTable(String dbId);

    @Query(value =  "select a.id as id,a.batch_no as batchNo,a.schemacode as code,a.schemaname as name," +
            "a.enname as enName,a.creator as createPerson," +
            "date_format (a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,a.is_auth as isAuth," +
            "date_format (a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime," +
            "a.data_type as dataType,a.categoryid as categoryId,a.db_id as db_id,a.version as version " +
            "from oper_tb_stdgl_schemamodule a where db_id = ?1 "+
            "union "+
            "select b.id as id,b.batch_no as batchNo,b.codetable_ename as code,b.codetable_cname as name," +
            "b.codetable_ename as enName,b.create_user as createPerson,"+
            "b.create_time as createTime,b.is_auth as isAuth,"+
            "b.update_time as updateTime,b.data_type as dataType,"+
            "null as categoryId,b.db_id as db_id,null as version from oper_tb_stdgl_code_info b where b.db_id = ?1",nativeQuery = true)
    List<Object[]> getOperStandardAndCodeTable2(String dbId);

    @Query(value = "select a.id as id,a.batch_no as batchNo,a.enname as codeEname,a.required as required,a.type as type,a.maxsize as maxsize,null as codeValue" +
            " from oper_tb_stdgl_schemafield a where a.moudle_id = ?1 and status = '1'"+
            "union "+
            "select b.id as id,b.batch_no as batchNo,b.code_ename as codeEname,null as required,null as type, null as maxsize, b.code_value as codeValue " +
            "from oper_tb_stdgl_code_info b where  b.id = ?1 and b.codetable_ename = ?2 and status = '1' group by code_value",nativeQuery = true)
    List<Object[]> getOperFieldAndCodeFiled(String id, String tableName);

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_schemamodule (batch_no,id,schemaname," +
            "enname,schemacode,description,datasource,version,type,creator,createtime," +
            "update_time,update_person,is_auth,auth_remark,categoryid,file_id,db_id," +
            "data_type,status,audit_person,audit_time) " +
            "values(:#{#os.batchNo},:#{#os.id},:#{#os.name},:#{#os.enName},:#{#os.code},:#{#os.description}," +
            ":#{#os.dataSource},:#{#os.version},:#{#os.type},:#{#os.createPerson},:#{#os.createTime}," +
            ":#{#os.updateTime},:#{#os.updatePerson},:#{#os.isAuth},:#{#os.authRemark},:#{#os.categoryId}," +
            "'','',:#{#os.dataType},:#{#os.status},'',null)",nativeQuery = true)
    void saveOperMoudle(@Param("os") OperStandard os);

    @Modifying
    @Query(value = "insert into oper_tb_stdgl_schemamodule " +
            "(id,schemaname,enname,schemacode,description,datasource,version,type,creator,createtime,update_time,update_person,is_auth,auth_remark,categoryid,status,file_id,db_id,batch_no,data_type) " +
            "select id,schemaname,enname,schemacode,description,datasource,version,type,creator,current_timestamp,update_time,update_person,?1,auth_remark,categoryid,'2',file_id,db_id,?2,?3 from tb_stdgl_schemamodule " +
            "where id =?4 and status='1' ",nativeQuery = true)
    void deleteMoudle(String authStatus, String batchNo, String dataType, String id);

    @Query(value = "select enname as ename,schemaname as cname,schemacode as code," +
            "date_format(createtime,'%Y-%m-%d %h:%m:%s') as createTime,creator as createUser,is_auth as isAuth," +
            "audit_person as auditUser,date_format(audit_time,'%Y-%m-%d %h:%m:%s') as auditTime," +
            "batch_no as batchNo,'标准' as type,data_type as dataType,id as moudleId,file_id as fileId,db_id as db_id " +
            "from oper_tb_stdgl_schemamodule " +
            "where is_auth='3' order by createtime desc",nativeQuery = true)
    List<Object[]> getOperList();

    @Query(value ="select date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,a.* from oper_tb_stdgl_schemamodule a " +
            "where batch_no=?1 and id=?2" ,nativeQuery = true)
    OperStandard getStandardByBatchNo(String batchNo, String id);

    @Query(value ="select date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,a.* from oper_tb_stdgl_schemamodule a " +
            "where batch_no=?1 and id=?2" ,nativeQuery = true)
    List<OperStandard> getStandardById(String batchNo, String id);

    @Modifying
    @Query(value = "update oper_tb_stdgl_schemamodule set is_auth=?1,auth_remark=?2 " +
            "where batch_no=?3 and id=?4",nativeQuery = true)
    void audit(String isAuth, String authRemark, String batchNo, String id);

    @Modifying
    @Transactional
    @Query(value = "delete from oper_tb_stdgl_schemamodule where id=?1 and batch_no = ?2",nativeQuery = true)
    void deleteByIdAndBatchNo(String id, String batch_no);

    @Query(value ="select * from oper_tb_stdgl_schemamodule " +
            "where file_id=?1 and is_auth!=?2" ,nativeQuery = true)
    List<OperStandard> getStandardByFileId(String fileId, String isAuth);

    @Query(value ="select * from oper_tb_stdgl_schemamodule " +
            "where db_id=?1 and is_auth!=?2" ,nativeQuery = true)
    List<OperStandard> getStandardByDbId(String dbId, String isAuth);

    @Query(value ="select count(*) from oper_tb_stdgl_schemamodule a " +
            "where batch_no=?1 and is_auth='5' " ,nativeQuery = true)
    int getCountByBatchNo(String batchNo);

    @Modifying
    @Query(value ="delete from oper_tb_stdgl_schemamodule " +
            "where batch_no=?1 and is_auth='5' " ,nativeQuery = true)
    int deleteByBatchNo(String batchNo);

    @Modifying
    @Query(value ="update oper_tb_stdgl_schemamodule set is_auth='3' " +
            "where batch_no=?1 and is_auth='5' " ,nativeQuery = true)
    int doAudit(String batchNo);

    @Modifying
    @Transactional
    @Query(value = "insert into oper_tb_stdgl_schemamodule " +
            "(id,schemaname,enname,schemacode,description,datasource,version,type,creator,createtime,update_time,update_person,is_auth,auth_remark,categoryid,status,file_id,db_id,batch_no,data_type) " +
            "select 1?,schemaname,enname,schemacode,description,datasource,version,type,creator,createtime,update_time,update_person,is_auth,auth_remark,categoryid,'2',file_id,db_id,batch_no,data_type from oper_tb_stdgl_schemamodule " +
            "where id = ?2 and batch_no=?3 ",nativeQuery = true)
    void insertOperStandardById(String newId, String id, String batch_no);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_schemamodule set status = ?1 where id = ?2 and batch_no = ?3",nativeQuery = true)
    void updateModleStatus(String status, String id, String batchNo);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_schemamodule set status = ?1 where db_id = ?2",nativeQuery = true)
    void updateOperModleStatus(String status, String dbId);

    @Modifying
    @Transactional
    @Query(value = "delete from oper_tb_stdgl_schemamodule where id=?1 and batch_no = ?2",nativeQuery = true)
    void deleteStandard(String id, String batchNo);

    @Modifying
    @Transactional
    @Query(value = "update oper_tb_stdgl_schemamodule set id = ?1,version = ?2 ,categoryid = ?5,data_type =?6,is_auth = ?7 where id = ?3 and batch_no = ?4",nativeQuery = true)
    void updateModleVersion(String newId, String version, String id, String batchNo, String categoryId, String dataType, String isAuth);

    @Override
    @Transactional
    <S extends OperStandard> S save(S s);

    @Query(value = "select * from  oper_tb_stdgl_schemamodule where db_id = ?1 and status = '1'",nativeQuery = true)
    List<OperStandard> getOperStandardByDbId(String dbId);





    @Query(value ="select code,name,createPerson,createTime, isAuth,updateTime,id,batchNo,dataType,categoryId,count from ( " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,is_auth as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,batch_no as batchNo," +
            "data_type as dataType,categoryid as categoryId,version as version,null as count from oper_tb_stdgl_schemamodule a where is_auth in ('3','5')" +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "null as dataType,categoryid as categoryId,version as version,count as count from tb_stdgl_schemamodule a where status='1'" +
            ") a where IF(?1 !='',categoryid=?1,1=1)" +
            "order by isAuth asc,createTime desc limit ?2,?3" ,nativeQuery = true)
    List<Object[]> getAllStandardInfoByPage(String categoryId,Integer startindex,Integer endindex);

    @Query(value ="select count(*) from ( " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,is_auth as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,batch_no as batchNo," +
            "data_type as dataType,categoryid as categoryId,version as version from oper_tb_stdgl_schemamodule a where is_auth in ('3','5')" +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "null as dataType,categoryid as categoryId,version as version from tb_stdgl_schemamodule a where status='1'" +
            ") a where IF(?1 !='',categoryid=?1,1=1)" ,nativeQuery = true)
    Integer getAllStandardInfoCount(String categoryId);


    /*@Query(value ="select code,name,createPerson,createTime, isAuth,updateTime,id,batchNo,dataType,categoryId,count from ( " +
            "select code,name,createPerson,createTime, isAuth,updateTime,id,batchNo,dataType,categoryId,version,count from (" +
            "select std.schemacode as code,std.schemaname as name,std.creator as createPerson," +
            "date_format(std.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(std.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,std.id as id,null as batchNo," +
            "null as dataType,std.categoryid as categoryId,std.version as version,std.count as count, " +
            "field.fieldname as fieldname,field.enname as fieldenname,field.fieldcode as fieldcode " +
            "from tb_stdgl_schemafield field right join tb_stdgl_schemamodule std on field.moudle_id = std.id " +
            "where std.status = '1' and field.status = '1'" +
            ") tmp where tmp.fieldname like CONCAT('%',?1,'%') or tmp.fieldenname like CONCAT('%',?1,'%') " +
            "or tmp.fieldcode like CONCAT('%',?1,'%') " +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,is_auth as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,batch_no as batchNo," +
            "data_type as dataType,categoryid as categoryId,version as version,'0' as count from oper_tb_stdgl_schemamodule a " +
            "where is_auth in ('3','5') " +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "null as dataType,categoryid as categoryId,version as version,count as count from tb_stdgl_schemamodule a " +
            "where status='1'" +
            ") a where a.code like CONCAT('%',?1,'%') or name like CONCAT('%',?1,'%') or updateTime like CONCAT('%',?1,'%')" +
            "order by isAuth asc,createTime desc limit ?2,?3" ,nativeQuery = true)*/

    //or update_time like CONCAT('%',?1,'%')
    @Query(value ="select code,name,createPerson,createTime, isAuth,updateTime,id,batchNo,dataType,categoryId,count from ( " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,is_auth as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,batch_no as batchNo," +
            "data_type as dataType,categoryid as categoryId,version as version,'0' as count from oper_tb_stdgl_schemamodule a where is_auth in ('3','5') " +
            "and (schemacode like CONCAT('%',?1,'%') or schemaname like CONCAT('%',?1,'%') ) " +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "null as dataType,categoryid as categoryId,version as version,count as count from tb_stdgl_schemamodule a where status='1' " +
            "and (schemacode like CONCAT('%',?1,'%') or schemaname like CONCAT('%',?1,'%'))" +
            ") a order by isAuth asc,createTime desc limit ?2,?3 " ,nativeQuery = true)
    List<Object[]> searchStandard(String searchword,int startindex,int size);

    /*@Query(value ="select count(*) from ( " +
            "select std.schemacode as code,std.schemaname as name,std.creator as createPerson," +
            "date_format(std.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(std.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,std.id as id,null as batchNo," +
            "'' as dataType,std.categoryid as categoryId,std.version as version " +
            "from tb_stdgl_schemafield field right join tb_stdgl_schemamodule std on field.moudle_id = std.id " +
            "where field.status = '1' and field.fieldname like CONCAT('%',?1,'%') or field.enname like CONCAT('%',?1,'%') " +
            "or field.fieldcode like CONCAT('%',?1,'%') " +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,is_auth as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,batch_no as batchNo," +
            "data_type as dataType,categoryid as categoryId,version as version from oper_tb_stdgl_schemamodule a " +
            "where is_auth in ('3','5') and schemacode like CONCAT('%',?1,'%') or schemaname like CONCAT('%',?1,'%') " +
            "or update_time like CONCAT('%',?1,'%') " +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "'' as dataType,categoryid as categoryId,version as version from tb_stdgl_schemamodule a " +
            "where status='1' and schemacode like CONCAT('%',?1,'%') or schemaname like CONCAT('%',?1,'%') " +
            "or update_time like CONCAT('%',?1,'%') " +
            ") a " ,nativeQuery = true)*/

    @Query(value ="select count(*) from ( " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,is_auth as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,batch_no as batchNo," +
            "data_type as dataType,categoryid as categoryId,version as version from oper_tb_stdgl_schemamodule a where is_auth in ('3','5') " +
            "and (schemacode like CONCAT('%',?1,'%') or schemaname like CONCAT('%',?1,'%')) " +
            "union " +
            "select schemacode as code,schemaname as name,creator as createPerson," +
            "date_format(a.createtime,'%Y-%m-%d %h:%m:%s') as createTime,'1' as isAuth," +
            "date_format(a.update_time,'%Y-%m-%d %h:%m:%s') as updateTime,id as id,null as batchNo," +
            "null as dataType,categoryid as categoryId,version as version from tb_stdgl_schemamodule a where status='1' " +
            "and (schemacode like CONCAT('%',?1,'%') or schemaname like CONCAT('%',?1,'%'))" +
            ") a " ,nativeQuery = true)
    Integer searchStandardCount(String searchword);






}