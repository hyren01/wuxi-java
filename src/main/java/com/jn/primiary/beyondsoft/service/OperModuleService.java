package com.jn.primiary.beyondsoft.service;


import com.alibaba.fastjson.JSONArray;
import com.hankcs.hanlp.dependency.nnparser.util.std;
import com.jn.primiary.beyondsoft.dao.*;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.beyondsoft.util.ConnUtil;
import com.jn.primiary.beyondsoft.vo.CodeValueVo;
import com.jn.primiary.beyondsoft.vo.OperStandardAndCodeVo;
import com.jn.primiary.beyondsoft.vo.OperStandardAndCodeVo2;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.office.entity.OperDocStdModel;
import com.jn.primiary.office.entity.TbStdglFiletable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OperModuleService {

    @Autowired
    OperModuleRepository operModuleRepository;
    @Autowired
    DBinfoRepository dBinfoRepository;
    @Autowired
    DbAndSchemaModuleRepository dbAndSchemaModuleRepository;
    @Autowired
    OperFieldRepository operFieldRepository;
    @Autowired
    OperObjectRepository operObjectRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    DbConnectionRepository dbConnectionRepository;
    @Autowired
    OperCodeInfo2Repository operCodeInfo2Repository;
    @Autowired
    CodeInfoRepository codeInfoRepository;
    @Autowired
    DbInfo3Repository dbInfo3Repository;

    //查询所有的操作记录
    public List<OperStandardAndCodeVo2> getAllStandard(String dbId) {
        List<Object[]> operStandardAndCode = operModuleRepository.getOperStandardAndCodeTable(dbId);
        List<OperStandardAndCodeVo2> standardAndCodeVoList = new ArrayList<>();
        try {
            standardAndCodeVoList = ComUtil.castEntity(operStandardAndCode, OperStandardAndCodeVo2.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return standardAndCodeVoList;
    }
    //查询所有的操作记录，不进行group by
    public List<OperStandardAndCodeVo> getAllStandard2(String dbId) {
        List<Object[]> operStandardAndCode = operModuleRepository.getOperStandardAndCodeTable2(dbId);
        List<OperStandardAndCodeVo> standardAndCodeVoList = new ArrayList<>();
        try {
            standardAndCodeVoList = ComUtil.castEntity(operStandardAndCode, OperStandardAndCodeVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return standardAndCodeVoList;
    }

    public List<OperStandard> getStandardInfoById(String id, String batchNo){
        return operModuleRepository.getStandardById(batchNo, id);

    }

    public List<CodeValueVo> getOperFieldAndOperCodeField(String batchNo, String Id, String tableName){
        List<Object[]> operFieldAndCodeFiled = operModuleRepository.getOperFieldAndCodeFiled(Id,tableName);
        List<CodeValueVo> fieldAndCodeList = new ArrayList<>();
        try {
            fieldAndCodeList = ComUtil.castEntity(operFieldAndCodeFiled, CodeValueVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldAndCodeList;
    }

    //查看所有的数据库标准信息
    public List<Dbinfo2> getAllDbInfo(){
        List<Dbinfo2> allDbInfo = dBinfoRepository.getAllDbInfo();
        try {
            //设置数据库记录的审核状态
            for (Dbinfo2 dbinfo2 : allDbInfo) {
                List<OperStandardAndCodeVo2> DSH_list = new ArrayList<>();
                List<OperStandardAndCodeVo2> SHTG_list = new ArrayList<>();
                List<OperStandardAndCodeVo2> SHJJ_list = new ArrayList<>();

                List<Object[]> operlist = operModuleRepository.getOperStandardAndCodeTable(dbinfo2.getDb_id());
                List<OperStandardAndCodeVo2> operStandardAndCodeVos = ComUtil.castEntity(operlist, OperStandardAndCodeVo2.class);
                //标准和码表是否全部通过
                for (OperStandardAndCodeVo2 mess : operStandardAndCodeVos) {
                    if(mess.getIsAuth().equals(String.valueOf(AuthType.DSH.getCode()))){
                        DSH_list.add(mess);
                    }else if(mess.getIsAuth().equals(String.valueOf(AuthType.SHTG.getCode()))){
                        SHTG_list.add(mess);
                    }else if(mess.getIsAuth().equals(String.valueOf(AuthType.SHJJ.getCode()))){
                        SHJJ_list.add(mess);
                    }
                }

                if(DSH_list.size() == operStandardAndCodeVos.size()){
                    dbinfo2.setIs_auth(String.valueOf(AuthType.DSH.getCode()));
                    dBinfoRepository.save(dbinfo2);
                    continue;
                }

                if(SHTG_list.size() == operStandardAndCodeVos.size()){
                    dbinfo2.setIs_auth(String.valueOf(AuthType.SHTG.getCode()));
                    dBinfoRepository.save(dbinfo2);
                    continue;
                }

                if(SHJJ_list.size() == operStandardAndCodeVos.size()){
                    dbinfo2.setIs_auth(String.valueOf(AuthType.SHJJ.getCode()));
                    dbinfo2.setStatus(String.valueOf(Status.SHIXIAO.getCode()));
                    dBinfoRepository.save(dbinfo2);
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return allDbInfo;
    }

    //查看所有的数据库信息
    public List<DbInfo3> getAllDbInfo3(){
        return dbInfo3Repository.getAllDb();
    }

    //保存数据库标准信息
    public void saveDbinfo(Dbinfo2 dbInfo2){
        dBinfoRepository.save(dbInfo2);
    }

    public Dbinfo2 getDbInfo(String dbId){
        return dBinfoRepository.getDbInfoById(dbId);
    }

    //保存数据库连接信息
    public void saveDbConnInfo(DbInfo3 dbInfo3){
        dbConnectionRepository.save(dbInfo3);
    }

    //保存码表
    public void saveOperCodeInfo(OperCodeInfo2 operCodeInfo2){
        operCodeInfo2Repository.save(operCodeInfo2);
    }

    //查询数据库下所有表
    public List<String> getDbTableNames(Dbinfo2 dbInfo){
        String dbDriver = getDbDriver(dbInfo.getDb_type());
        List<String> allTables = null;
        if(!StringUtils.isEmpty(dbDriver)){
            Connection conn = ConnUtil.getConnection(dbDriver, dbInfo.getJdbc_url(), "project_name", dbInfo.getUsername(), dbInfo.getPassword());
            allTables = ConnUtil.getAllTables(conn);
        }
        return allTables;
    }
    //查出表中所有字段信息
    public List<FieldInfo> getDbTableFields(Dbinfo2 dbInfo, String tableName){
        String dbDriver = getDbDriver(dbInfo.getDb_type());
        List<FieldInfo> fidlds = null;
        if(!StringUtils.isEmpty(dbDriver)){
            Connection conn = ConnUtil.getConnection(dbDriver, dbInfo.getJdbc_url(), "project_name", dbInfo.getUsername(), dbInfo.getPassword());
            fidlds = ConnUtil.getAllFidldsByCondition(conn, tableName);
        }
        return fidlds;
    }

    //保存所有的字段信息
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveNormalFields(OperStandardField operStandardField){
        operFieldRepository.save(operStandardField);
    }
    //根据moduleid查询所有字段
    public List<OperStandardField> getAllFieldsById(String moduleId){
        return operFieldRepository.getAllFieldByModuleId(moduleId);
    }
    //根据moduleid更新字段的moduleId
    public void updateFieldsByModuleId(String newModuleId,String oldModuleId,String dataType){
        operFieldRepository.updateSchemaField(newModuleId, oldModuleId,dataType);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateFieldsById(String status,String moduleId){
        operFieldRepository.updateSchemaFieldById(status,moduleId);
    }
    //保存对象类型字段信息
    public void saveObjFields(OperStandardObject operStandardObject){
        operObjectRepository.saveOperObject(operStandardObject);
    }

    //更改操作记录,将原有的记录状态改变，再新增一条
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStandard(String batchNo,String id,String flag){
        if(flag.equals("1")){
            operModuleRepository.updateModleStatus(String.valueOf(Status.SHIXIAO.getCode()),id,batchNo);
            operFieldRepository.updateSchemaFieldById(String.valueOf(Status.SHIXIAO.getCode()), id);
        } else {
            operCodeInfo2Repository.updateOperCodeInfoById(id,batchNo,String.valueOf(Status.SHIXIAO.getCode()));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOperModleStatus(String dbId){
        operModuleRepository.updateOperModleStatus(String.valueOf(Status.SHIXIAO.getCode()),dbId);
    }

    public void deleteStandard(String batchNo,String id,String flag){
        if(flag.equals("1")){
            operModuleRepository.deleteStandard(id,batchNo);
            operFieldRepository.deleteByModuleId(id);
        } else {
            operCodeInfo2Repository.deleteOperCodeInfo2ById(id,batchNo);
        }
    }

    public void deleteDb(String dbId,String auTh){
        if (auTh.equals(String.valueOf(AuthType.DSQ.getCode()))){
            dBinfoRepository.deleteDbinfo2ById(dbId);
        }
    }

    public void updateDbStatus(String dbId){
        dBinfoRepository.updateDbInfo2ById(dbId,String.valueOf(Status.SHIXIAO.getCode()));
    }
    public void updateDbIdById(String oldId,String newId){
        dBinfoRepository.updateDbInfo2IdById(oldId,newId);
    }

    public void updateOperCodeName(String tableName,String id,String batchNo){
        operCodeInfo2Repository.updateOperCodeName(tableName,id,batchNo);
    }


    //插入操作记录
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertStandards(OperStandard operStandard){
//        Configuration configuration = new Configuration().configure();
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.clear();
        operModuleRepository.save(operStandard);
    }

    public String getDbDriver(String dbtype){
        String dbTYPE = dbtype.toLowerCase();
        String driver = null;
        if (dbTYPE.contains("oracle")){
            driver = "oracle.jdbc.driver.OracleDriver";
            return driver;
        }
        if(dbTYPE.contains("mysql")){
            driver = "com.mysql.jdbc.Driver";
            return driver;
        }
        if(dbTYPE.contains("SQLServer 2000")){
            driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
            return driver;
        }
        if(dbTYPE.contains("SQLServer 2005")){
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            return driver;
        }
        if(dbTYPE.contains("DB2")){
            driver = "com.ibm.db2.jcc.DB2Driver";
            return driver;
        }

        if(dbTYPE.contains("pgsql")){
            driver = "org.postgresql.Driver";
            return driver;
        }
        return driver;
    }

    public List<Standard> getStandardBySchemaCode(String schemaCode){
        List<Standard> allStandard = moduleRepository.getAllBySchemeCode(schemaCode);
        return  allStandard;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOperStandardById(String newId,String version,String id,String batchNo,String categoryId,String dataType,String isAuth){
        operModuleRepository.updateModleVersion(newId,version,id,batchNo,categoryId,dataType,isAuth);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOperCodeById(String id,String batchNo,String status){
        operCodeInfo2Repository.updateOperCodeInfo( id, batchNo, status);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOperCodeInfoByDbId(String dbId){
        operCodeInfo2Repository.updateOperCodeInfoByDbId(String.valueOf(Status.SHIXIAO.getCode()),dbId);
    }


    public OperStandard getStandardByBatchNo(String batch_no,String id) {
        return operModuleRepository.getStandardByBatchNo(batch_no,id);
    }

    public List<CodeInfo> getCodeInfo(String tableEname) throws Exception{
        List<CodeInfo> info = codeInfoRepository.getCodeInfoByName(tableEname);
        if(info.size()>0){
            throw new Exception("码表名重复："+info.get(0).getCodetableEname()+",请修改码表名再保存");
        }
        return info;
    }

    public List<OperCodeInfo2> getOperCodeInfo(String batchNo){
        return operCodeInfo2Repository.getOperCodeInfoByBatchNo(batchNo);
    }

    public List<OperStandard> getOperStandard(String dbId){
        return operModuleRepository.getOperStandardByDbId(dbId);
    }

    public List<OperCodeInfo2> getOperCode(String dbId){
        return operCodeInfo2Repository.getOperCodeInfoByDbId(dbId);
    }

    public List<OperCodeInfo2> getOperCode2(String dbId,String tableName){
        return operCodeInfo2Repository.getOperCodeInfoByDbId2(dbId,tableName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveEditStandard(JSONArray operArray,JSONArray codeArray,String userName)throws Exception{
        String dataType = String.valueOf(DataType.UPDATE.getCode());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDbId = CommonUtil.getUUID();
        String dbId = null;
        String newBatchNo = ComUtil.getBatchNo();
        for (int i = 0; i < operArray.size(); i++) {
            OperStandard operStandard = operArray.getJSONObject(i).toJavaObject(OperStandard.class);
            String oldId = operStandard.getId();
//            operModuleService.updateOperModleStatus(operStandard.getDb_id());
            operModuleRepository.updateOperModleStatus(String.valueOf(Status.SHIXIAO.getCode()),operStandard.getDb_id());
            String uuid = CommonUtil.getUUID();
            dbId = operStandard.getDb_id();
            operStandard.setId(uuid);
            operStandard.setBatchNo(newBatchNo);
            operStandard.setDb_id(newDbId);
            operStandard.setDataType(dataType);
            operStandard.setCreatePerson(userName);
            operStandard.setCreateTime(Calendar.getInstance().getTime());
//            operModuleService.insertStandards(operStandard);
            operModuleRepository.save(operStandard);

            List<OperStandardField> standardFieldList = this.getAllFieldsById(oldId);

            for (int j = 0; j < standardFieldList.size(); j++) {
                String newId = CommonUtil.getUUID();
                OperStandardField operStandardField = new OperStandardField();
                BeanUtils.copyProperties(standardFieldList.get(j),operStandardField );
                operStandardField.setId(newId);
                operStandardField.setBatchNo(newBatchNo);
                operStandardField.setMoudleId(uuid);
                operStandardField.setDataType(dataType);
                operStandardField.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operStandardField.setCreateTime(Calendar.getInstance().getTime());
                operStandardField.setCreatePerson(userName);
//                operModuleService.saveNormalFields(operStandardField);
                operFieldRepository.save(operStandardField);
            }
//            operModuleService.updateFieldsById(String.valueOf(Status.SHIXIAO.getCode()),oldId);
            operFieldRepository.updateSchemaFieldById(String.valueOf(Status.SHIXIAO.getCode()),oldId);
        }
        for (int i = 0; i < codeArray.size(); i++) {
            OperCodeInfo2 operCodeInfo2 = codeArray.getJSONObject(i).toJavaObject(OperCodeInfo2.class);
//            operModuleService.updateOperCodeInfoByDbId(operCodeInfo2.getDb_id());
            List<CodeInfo> codeInfo = null;
            try {
                codeInfo = this.getCodeInfo(operCodeInfo2.getEnName());
            } catch (Exception e) {
                throw new Exception("码表英文名"+codeInfo.get(0)+"已存在,请修改");
            }
            operCodeInfo2Repository.updateOperCodeInfoByDbId(String.valueOf(Status.SHIXIAO.getCode()),operCodeInfo2.getDb_id());
            operCodeInfo2.setId(CommonUtil.getUUID());
            operCodeInfo2.setBatchNo(newBatchNo);
            operCodeInfo2.setDb_id(newDbId);
            operCodeInfo2.setDataType(dataType);
            operCodeInfo2.setCreatePerson(userName);
            operCodeInfo2.setCreateTime(df.format(new Date()));
//            operModuleService.saveOperCodeInfo(operCodeInfo2);
            operCodeInfo2Repository.save(operCodeInfo2);
        }
        Dbinfo2 dbinfo2 = new Dbinfo2();
        Dbinfo2 dbInfo = this.getDbInfo(dbId);
        BeanUtils.copyProperties(dbInfo,dbinfo2);
        dbinfo2.setDb_id(newDbId);
        dbinfo2.setCreate_time(df.format(new Date()));
        dbinfo2.setStatus(String.valueOf(Status.JIHUO.getCode()));
        dbinfo2.setIs_auth(String.valueOf(AuthType.DSQ.getCode()));
//        operModuleService.saveDbinfo(dbinfo2);
        dBinfoRepository.save(dbinfo2);
//        operModuleService.updateDbStatus(dbId);
        dBinfoRepository.updateDbInfo2ById(dbId,String.valueOf(Status.SHIXIAO.getCode()));
    }


    @Transactional(rollbackFor = Exception.class)
    public void applayStandard(String db_id,String category_id,String userName) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataType = String.valueOf(DataType.ADD.getCode());
        String isAuth = String.valueOf(AuthType.DSH.getCode());
        String status = String.valueOf(Status.JIHUO.getCode());
        String dbId = CommonUtil.getUUID();
        List<OperStandardAndCodeVo2> operStandardAndCodeVoList = this.getAllStandard(db_id);
        Dbinfo2 dbInfo = this.getDbInfo(db_id);
        Dbinfo2 dbinfo2 = new Dbinfo2();
        dbinfo2.setDb_id(dbId);
        dbinfo2.setDb_name(dbInfo.getDb_name());
        dbinfo2.setIs_auth(String.valueOf(AuthType.DSH.getCode()));
        dbinfo2.setStatus(String.valueOf(Status.JIHUO.getCode()));
        dbinfo2.setCreate_time(df.format(new Date()));
        dbinfo2.setJdbc_url(dbInfo.getJdbc_url());
        dbinfo2.setUsername(userName);
        dbinfo2.setDb_port(dbInfo.getDb_port());
        dbinfo2.setDb_type(dbInfo.getDb_type());
        dbinfo2.setDb_ip(dbInfo.getDb_ip());
        dbinfo2.setPassword(dbInfo.getPassword());
        this.saveDbinfo(dbinfo2);
        dBinfoRepository.updateDbInfo2ById(db_id,String.valueOf(Status.SHIXIAO.getCode()));
        String newBatchNo = ComUtil.getBatchNo();
        for (OperStandardAndCodeVo2 operStandardAndCodeVo : operStandardAndCodeVoList) {
            String name = operStandardAndCodeVo.getName();
            String code = operStandardAndCodeVo.getCode();
            String tableName = operStandardAndCodeVo.getEnName();
            String id = operStandardAndCodeVo.getId();
            String batchNo = operStandardAndCodeVo.getBatchNo();
            String uuid = CommonUtil.getUUID();
            if (name.equals("2")){
                String enName = operStandardAndCodeVo.getEnName();
                List<CodeInfo> codeInfo = null;
                try {
                    codeInfo = this.getCodeInfo(enName);
                } catch (Exception e) {
                    throw e;
                }
                List<OperCodeInfo2> operCodeInfos = this.getOperCode2(db_id,tableName);
                for (int j = 0; j < operCodeInfos.size(); j++) {
                    OperCodeInfo2 operCodeInfo2 = new OperCodeInfo2();
                    OperCodeInfo2 operCodeInfo_old = operCodeInfos.get(j);
                    String newid = CommonUtil.getUUID();
                    BeanUtils.copyProperties(operCodeInfo_old,operCodeInfo2);
                    operCodeInfo2.setId(newid);
                    operCodeInfo2.setBatchNo(newBatchNo);
                    operCodeInfo2.setCodeValue(String.valueOf(j + 1));
                    operCodeInfo2.setCreateTime(df.format(new Date()));
                    operCodeInfo2.setCreatePerson(userName);
                    operCodeInfo2.setUpdatePerson(userName);
                    operCodeInfo2.setDb_id(dbId);
                    operCodeInfo2.setIsAuth(isAuth);
                    operCodeInfo2.setDataType(dataType);
                    operCodeInfo2.setStatus(status);
//                    operModuleService.saveOperCodeInfo(operCodeInfo2);
                    operCodeInfo2Repository.save(operCodeInfo2);
//                    operModuleService.updateOperCodeById(id, batchNo, String.valueOf(Status.SHIXIAO.getCode()));
                    operCodeInfo2Repository.updateOperCodeInfo( operCodeInfo_old.getId(), operCodeInfo_old.getBatchNo(), String.valueOf(Status.SHIXIAO.getCode()));
                }

            } else {
                OperStandard operStandard = this.getStandardByBatchNo(batchNo, id);
                List<Standard> standardList = this.getStandardBySchemaCode(code);
//                String batchNo4 = ComUtil.getBatchNo();
                if(standardList.size()>0 && code.equalsIgnoreCase(standardList.get(0).getCode())){
                    OperStandard operStandard1 = new OperStandard();
                    operStandard1.setId(uuid);
                    operStandard1.setBatchNo(newBatchNo);
                    operStandard1.setEnName(operStandard.getEnName());
                    operStandard1.setCreateTime(Calendar.getInstance().getTime());
                    operStandard1.setCreatePerson(operStandard.getCreatePerson());
                    operStandard1.setCategoryId(category_id);
                    operStandard1.setUpdatePerson(operStandard.getUpdatePerson());
                    operStandard1.setUpdateTime(operStandard.getUpdateTime());
                    operStandard1.setDb_id(dbId);
                    operStandard1.setStatus(status);
                    operStandard1.setVersion("1");
                    operStandard1.setDataType(String.valueOf(DataType.UPDATE.getCode()));
                    operStandard1.setIsAuth(isAuth);
                    operStandard1.setAuditTime(operStandard.getAuditTime());
                    operStandard1.setAuditPerson(operStandard.getAuditPerson());
                    operStandard1.setCode(operStandard.getCode());
                    operStandard1.setName(name);
                    operStandard1.setAuthRemark(operStandard.getAuthRemark());
                    operStandard1.setFileId(operStandard.getFileId());
//                    operModuleService.insertStandards(operStandard1);
                    operModuleRepository.save(operStandard1);
                    String version = String.valueOf(Integer.parseInt(standardList.get(0).getVersion())+1);
//                    operModuleService.updateOperStandardById(uuid,version,id,batchNo,category_id,dataType,isAuth);
                    operModuleRepository.updateModleVersion(uuid,version,operStandard.getId(),operStandard.getBatchNo(),category_id,dataType,isAuth);
//                    operModuleService.updateStandard(batchNo, id, "1");
                    operModuleRepository.updateModleStatus(String.valueOf(Status.SHIXIAO.getCode()),operStandard.getId(),operStandard.getBatchNo());
                    //字段
                    List<OperStandardField> standardFieldList = this.getAllFieldsById(id);
                    for (int i = 0; i < standardFieldList.size(); i++) {
                        String uuid2 = CommonUtil.getUUID();
                        OperStandardField operStandardField = new OperStandardField();
                        BeanUtils.copyProperties(standardFieldList.get(i),operStandardField);
                        operStandardField.setId(uuid2);
                        operStandardField.setBatchNo(newBatchNo);
                        operStandardField.setMoudleId(uuid);
                        operStandardField.setDataType(String.valueOf(DataType.UPDATE.getCode()));
                        operStandardField.setStatus(status);
                        operStandardField.setCreateTime(Calendar.getInstance().getTime());
                        operStandardField.setCreatePerson(userName);
                        operStandardField.setVersion("1");
//                        operModuleService.saveNormalFields(operStandardField);
                        operFieldRepository.save(operStandardField);
                    }
//                    operModuleService.updateFieldsById(String.valueOf(Status.SHIXIAO.getCode()),id);
                    operFieldRepository.updateSchemaFieldById(String.valueOf(Status.SHIXIAO.getCode()),id);

                } else {

                    if(!ObjectUtils.isEmpty(operStandard)){
                        OperStandard operStandard1 = new OperStandard();
                        operStandard1.setId(uuid);
                        operStandard1.setBatchNo(newBatchNo);
                        operStandard1.setCreateTime(Calendar.getInstance().getTime());
                        operStandard1.setCreatePerson(operStandard.getCreatePerson());
                        operStandard1.setCategoryId(category_id);
                        operStandard1.setUpdatePerson(operStandard.getUpdatePerson());
                        operStandard1.setUpdateTime(operStandard.getUpdateTime());
                        operStandard1.setDb_id(dbId);
                        operStandard1.setStatus(status);
                        operStandard1.setVersion("1");
                        operStandard1.setDataType(dataType);
                        operStandard1.setIsAuth(isAuth);
                        operStandard1.setAuditTime(operStandard.getAuditTime());
                        operStandard1.setAuditPerson(operStandard.getAuditPerson());
                        operStandard1.setCode(operStandard.getCode());
                        operStandard1.setName(name);
                        operStandard1.setEnName(operStandard.getEnName());
                        operStandard1.setAuthRemark(operStandard.getAuthRemark());
                        operStandard1.setFileId(operStandard.getFileId());
                        operModuleRepository.save(operStandard1);
//                        operModuleService.updateStandard(batchNo, id, "1");
                        operModuleRepository.updateModleStatus(String.valueOf(Status.SHIXIAO.getCode()),operStandard.getId(),operStandard.getBatchNo());
                    }
                    //字段
                    List<OperStandardField> standardFieldList = this.getAllFieldsById(id);
                    for (int i = 0; i < standardFieldList.size(); i++) {
                        String uuid3 = CommonUtil.getUUID();
                        OperStandardField operStandardField = new OperStandardField();
                        BeanUtils.copyProperties(standardFieldList.get(i),operStandardField);
                        operStandardField.setId(uuid3);
                        operStandardField.setBatchNo(newBatchNo);
                        operStandardField.setMoudleId(uuid);
                        operStandardField.setDataType(dataType);
                        operStandardField.setStatus(status);
                        operStandardField.setCreateTime(Calendar.getInstance().getTime());
                        operStandardField.setCreatePerson(userName);
                        operStandardField.setVersion("1");
//                        operModuleService.saveNormalFields(operStandardField);
                        operFieldRepository.save(operStandardField);
                    }
//                    operModuleService.updateFieldsById(String.valueOf(Status.SHIXIAO.getCode()),id);
                    operFieldRepository.updateSchemaFieldById(String.valueOf(Status.SHIXIAO.getCode()),id);
                }
            }
        }
    }

}
