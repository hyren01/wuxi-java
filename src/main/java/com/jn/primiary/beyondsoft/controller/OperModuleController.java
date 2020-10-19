package com.jn.primiary.beyondsoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jn.primiary.beyondsoft.aspect.CheckLoginEndPoint;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.service.CategoryService;
import com.jn.primiary.beyondsoft.service.OperModuleService;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.beyondsoft.vo.CodeValueVo;
import com.jn.primiary.beyondsoft.vo.OperStandardAndCodeVo;
import com.jn.primiary.beyondsoft.vo.OperStandardAndCodeVo2;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 标准操作类
 */
@Controller
@RequestMapping("stdglprj/operModule")
public class OperModuleController{

    @Autowired
    OperModuleService operModuleService;
    @Autowired
    CategoryService categoryService;

    //根据数据库id查询所有的操作记录
    @ResponseBody
    @RequestMapping(value = "/getAllStandardInfo",method = RequestMethod.POST)
    public BaseResponse<OperStandardAndCodeVo2> getAllStandardInfo(@RequestParam String db_id){
        List<OperStandardAndCodeVo2> operStandardAndCodeVoList = operModuleService.getAllStandard(db_id);
        BaseResponse<OperStandardAndCodeVo2> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(operStandardAndCodeVoList);
        return response;
    }

    //根据id和batchNo查看字段
    @ResponseBody
    @RequestMapping(value = "/getStandardFieldById",method = RequestMethod.POST)
    public BaseResponse<CodeValueVo> getStandardFieldById(@RequestParam String id, @RequestParam String batch_no, @RequestParam String tableName){
        List<CodeValueVo> standardFieldList = operModuleService.getOperFieldAndOperCodeField(batch_no, id,tableName);
        BaseResponse<CodeValueVo> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(standardFieldList);
        return response;
    }

    //查看所有的目录
    @ResponseBody
    @RequestMapping(value = "/getAllCategoryInfo",method = RequestMethod.GET)
    public BaseResponse<CategoryInfo> getAllCategoryInfo(){
        List<CategoryInfo> categoryInfoList = categoryService.getAllCategory();
        BaseResponse<CategoryInfo> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(categoryInfoList);
        return response;
    }

    //点击查看数据库按钮
    @ResponseBody
    @RequestMapping(value = "/getDbInfo",method = RequestMethod.GET)
    public BaseResponse<DbInfo3> getDbInfo3(){
        List<DbInfo3> dbInfoList = operModuleService.getAllDbInfo3();
        BaseResponse<DbInfo3> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(dbInfoList);
        return response;
    }

    //首页进来查看所有的数据库
    @ResponseBody
    @RequestMapping(value = "/getAllDbInfo",method = RequestMethod.GET)
    public BaseResponse<Dbinfo2> getAllDbInfo(){
        List<Dbinfo2> dbInfoList = operModuleService.getAllDbInfo();
        BaseResponse<Dbinfo2> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(dbInfoList);
        return response;
    }

    //保存数据库信息
    @ResponseBody
    @RequestMapping(value = "/addDbInfo",method = RequestMethod.POST)
    public BaseResponse<DbInfo3> addDbInfo(@RequestParam(required=true) String dbInfo){
        DbInfo3 dbInfo3 = JSONObject.parseObject(dbInfo).toJavaObject(DbInfo3.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dbInfo3.setDb_id(CommonUtil.getUUID());
        dbInfo3.setCreate_time(df.format(new Date()));
        operModuleService.saveDbConnInfo(dbInfo3);
        BaseResponse<DbInfo3> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    //查询数据库中所有表的表名
    @ResponseBody
    @RequestMapping(value = "/getDbTableInfo",method = RequestMethod.POST)
    public BaseResponse<JSONObject> getDbTableInfo(@RequestParam(required = true)String db){
        Dbinfo2 dbinfo2 = JSONObject.parseObject(db).toJavaObject(Dbinfo2.class);
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> lessNameList = new ArrayList<>();
        ArrayList<JSONObject> resJo = new ArrayList<>();
        List<String> dbTableNames = operModuleService.getDbTableNames(dbinfo2);
        jsonObject.put("table_cname", dbTableNames);
        jsonObject.put("table_cname", dbTableNames);
        for (String dbTableName : dbTableNames) {
            if(dbTableName.length()>=3){
                lessNameList.add(dbTableName.substring(0,3));
            } else {
                lessNameList.add(dbTableName);
            }
        }
        jsonObject.put("less_name", lessNameList);
        resJo.add(jsonObject);
        BaseResponse<JSONObject> response = new BaseResponse<>();
        response.setData(resJo);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    //删除操作记录
    @ResponseBody
    @RequestMapping(value = "/deleteStandard",method = RequestMethod.POST)
    public BaseResponse<String> deleteStandard(@RequestParam String db_id,@RequestParam String Auth){
        List<OperStandardAndCodeVo> operStandardAndCodeVoList = operModuleService.getAllStandard2(db_id);
        for (OperStandardAndCodeVo operStandardAndCodeVo : operStandardAndCodeVoList) {
            String isAuth = operStandardAndCodeVo.getIsAuth();
            String batchNo = operStandardAndCodeVo.getBatchNo();
            String id = operStandardAndCodeVo.getId();
            String flag = operStandardAndCodeVo.getName();
            if (!isAuth.equals("4")){
                operModuleService.updateStandard(batchNo, id,flag);
            } else {
                operModuleService.deleteStandard(batchNo,id,flag);
            }
        }
        operModuleService.deleteDb(db_id,Auth);
        BaseResponse<String> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    //插入操作记录
    @ResponseBody
    @RequestMapping(value = "/insertStandards",method = RequestMethod.POST)
    public BaseResponse<String> insertStandards(@RequestParam(required = true)String op){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Dbinfo2 dbinfo2 = JSONObject.parseObject(op).getJSONObject("dbinfo2").toJavaObject(Dbinfo2.class);
        dbinfo2.setDb_id(CommonUtil.getUUID());
        JSONArray standardList = JSONObject.parseObject(op).getJSONArray("standardList");
        String batchNo = ComUtil.getBatchNo();
        for (int i = 0; i < standardList.size(); i++) {
            String bm = standardList.getJSONObject(i).getString("name");
            if(bm.equals("1")){
                OperStandard operStandard = standardList.getJSONObject(i).toJavaObject(OperStandard.class);
                String id = CommonUtil.getUUID();
                operStandard.setId(id);
                operStandard.setBatchNo(batchNo);
                operStandard.setCreateTime(new Date());
                operStandard.setCreatePerson(UserContextUtil.getUserName());
                operStandard.setUpdatePerson(UserContextUtil.getUserName());
                operStandard.setDb_id(dbinfo2.getDb_id());
                operStandard.setIsAuth(String.valueOf(AuthType.DSQ.getCode()));
                operStandard.setDataType(String.valueOf(DataType.ADD.getCode()));
                operStandard.setVersion("1");
                operStandard.setStatus(String.valueOf(Status.JIHUO.getCode()));
                operModuleService.insertStandards(operStandard);
                List<OperStandardField> standardFieldList = operStandard.getStandardField();
                for (OperStandardField operStandardField : standardFieldList) {
                    operStandardField.setId(CommonUtil.getUUID());
                    operStandardField.setBatchNo(batchNo);
                    operStandardField.setMoudleId(id);
                    operStandardField.setDataType(String.valueOf(DataType.ADD.getCode()));
                    operStandardField.setStatus(String.valueOf(Status.JIHUO.getCode()));
                    operStandardField.setCreateTime(Calendar.getInstance().getTime());
                    operStandardField.setCreatePerson(UserContextUtil.getUserName());
                    operStandardField.setVersion("1");
                    operModuleService.saveNormalFields(operStandardField);
                }
            } else {
                OperCodeInfo2 operCodeInfo2 = standardList.getJSONObject(i).toJavaObject(OperCodeInfo2.class);
                List<OperStandardField> standardField = operCodeInfo2.getStandardField();
                for (int j = 0; j < standardField.size(); j++) {
                    String id = CommonUtil.getUUID();
                    operCodeInfo2.setId(id);
                    operCodeInfo2.setBatchNo(batchNo);
                    operCodeInfo2.setCodeEname(standardField.get(j).getEnName());
                    operCodeInfo2.setCodeValue(String.valueOf(j+1));
                    operCodeInfo2.setCreateTime(df.format(new Date()));
                    operCodeInfo2.setCreatePerson(UserContextUtil.getUserName());
                    operCodeInfo2.setUpdatePerson(UserContextUtil.getUserName());
                    operCodeInfo2.setDb_id(dbinfo2.getDb_id());
                    operCodeInfo2.setIsAuth(String.valueOf(AuthType.DSQ.getCode()));
                    operCodeInfo2.setDataType(String.valueOf(DataType.ADD.getCode()));
                    operCodeInfo2.setStatus(String.valueOf(Status.JIHUO.getCode()));
                    operModuleService.saveOperCodeInfo(operCodeInfo2);
                }

            }
        }
        dbinfo2.setCreate_time(df.format(new Date()));
        dbinfo2.setStatus(String.valueOf(Status.JIHUO.getCode()));
        dbinfo2.setIs_auth(String.valueOf(AuthType.DSQ.getCode()));
        operModuleService.saveDbinfo(dbinfo2);
        BaseResponse<String> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    //查询表中所有字段信息
    @ResponseBody
    @RequestMapping(value = "/queryFieldsInfo",method = RequestMethod.POST)
    public BaseResponse<FieldInfo> queryFieldsInfo(@RequestParam(required = true)String db){
        JSONObject dbObj = JSONObject.parseObject(db);
        Dbinfo2 dbinfo2 = new Dbinfo2();
        dbinfo2.setDb_ip(dbObj.getString("db_ip"));
        dbinfo2.setDb_name(dbObj.getString("db_name"));
        dbinfo2.setDb_type(dbObj.getString("db_type"));
        dbinfo2.setDb_port(dbObj.getString("db_port"));
        dbinfo2.setUsername(dbObj.getString("username"));
        dbinfo2.setPassword(dbObj.getString("password"));
        dbinfo2.setJdbc_url(dbObj.getString("jdbc_url"));
        List<FieldInfo> dbTableFields = operModuleService.getDbTableFields(dbinfo2, dbObj.getString("table_name"));
        BaseResponse<FieldInfo> response = new BaseResponse<>();
        response.setData(dbTableFields);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    //点击编辑，将码表和标准分开返回
    @ResponseBody
    @RequestMapping(value = "/editTable",method = RequestMethod.POST)
    public BaseResponse<List> editTable(@RequestParam String db_id){
        List<OperStandard> operStandard = operModuleService.getOperStandard(db_id);
        List<OperCodeInfo2> operCode = operModuleService.getOperCode(db_id);
        ArrayList<List> resList = new ArrayList<>();
        resList.add(operStandard);
        resList.add(operCode);
        BaseResponse<List> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resList);
        return response;
    }

    //查询码表字段
    @ResponseBody
    @RequestMapping(value = "/getOperCode",method = RequestMethod.POST)
    public BaseResponse<OperCodeInfo2> getOperCode(@RequestParam String db_id,@RequestParam String tableName){
        List<OperCodeInfo2> operCode = operModuleService.getOperCode2(db_id,tableName);
        BaseResponse<OperCodeInfo2> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(operCode);
        return response;
    }

    //编辑后的保存
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/saveEditStandard",method = RequestMethod.POST)
    public BaseResponse<String> saveEditStandard(@RequestParam String operTable,@RequestParam String codeTable) throws Exception {
        JSONArray operArray = JSONArray.parseArray(operTable);
        JSONArray codeArray = JSONArray.parseArray(codeTable);
        BaseResponse<String> response = new BaseResponse<>();
        try {
            operModuleService.saveEditStandard(operArray,codeArray,UserContextUtil.getUserName());
            response.setResultCode(ResultCode.RESULT_SUCCESS);
        } catch (Exception e) {
            response.setResultCode(ResultCode.RESULT_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /*//申请操作
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/applayStandard",method = RequestMethod.POST)
    public BaseResponse<String> applayStandard(@RequestParam String db_id,@RequestParam String category_id) throws Exception {
        BaseResponse<String> response = new BaseResponse<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataType = String.valueOf(DataType.UPDATE.getCode());
        String isAuth = String.valueOf(AuthType.DSH.getCode());
        String status = String.valueOf(Status.JIHUO.getCode());
        String dbId = CommonUtil.getUUID();
        List<OperStandardAndCodeVo> operStandardAndCodeVoList = operModuleService.getAllStandard(db_id);
        Dbinfo2 dbInfo = operModuleService.getDbInfo(db_id);
        Dbinfo2 dbinfo2 = new Dbinfo2();
        dbinfo2.setDb_id(dbId);
        dbinfo2.setDb_name(dbInfo.getDb_name());
        dbinfo2.setIs_auth(String.valueOf(AuthType.DSH.getCode()));
        dbinfo2.setStatus(String.valueOf(Status.JIHUO.getCode()));
        dbinfo2.setCreate_time(df.format(new Date()));
        dbinfo2.setJdbc_url(dbInfo.getJdbc_url());
        dbinfo2.setUsername(this.user.getUsername());
        dbinfo2.setDb_port(dbInfo.getDb_port());
        dbinfo2.setDb_type(dbInfo.getDb_type());
        dbinfo2.setDb_ip(dbInfo.getDb_ip());
        dbinfo2.setPassword(dbInfo.getPassword());
        operModuleService.saveDbinfo(dbinfo2);
        operModuleService.updateDbStatus(db_id);
        for (OperStandardAndCodeVo operStandardAndCodeVo : operStandardAndCodeVoList) {
            String name = operStandardAndCodeVo.getName();
            String code = operStandardAndCodeVo.getCode();
            String id = operStandardAndCodeVo.getId();
            String batchNo = operStandardAndCodeVo.getBatchNo();
            String uuid = CommonUtil.getUUID();
            if (name.equals("2")){
                String enName = operStandardAndCodeVo.getEnName();
                try {
                    operModuleService.getCodeInfo(enName);
                } catch (Exception e) {
                    response.setMessage(e.getMessage());
                    return response;
                }

                List<OperCodeInfo2> operCodeInfos = operModuleService.getOperCode(db_id);
                for (int j = 0; j < operCodeInfos.size(); j++) {
                    OperCodeInfo2 operCodeInfo2 = new OperCodeInfo2();
                    OperCodeInfo2 operCodeInfo_old = operCodeInfos.get(j);
                    String newid = CommonUtil.getUUID();
                    operCodeInfo2.setId(newid);
                    operCodeInfo2.setBatchNo(batchNo);
                    operCodeInfo2.setCodeEname(operCodeInfo_old.getEnName());
                    operCodeInfo2.setCodeCname(operCodeInfo_old.getCodeCname());
                    operCodeInfo2.setCode(operCodeInfo_old.getCode());
                    operCodeInfo2.setCodeValue(String.valueOf(j + 1));
                    operCodeInfo2.setCreateTime(df.format(new Date()));
                    operCodeInfo2.setCreatePerson(this.user.getUsername());
                    operCodeInfo2.setUpdatePerson(this.user.getUsername());
                    operCodeInfo2.setDb_id(dbId);
                    operCodeInfo2.setIsAuth(isAuth);
                    operCodeInfo2.setDataType(dataType);
                    operCodeInfo2.setStatus(status);
                    operModuleService.saveOperCodeInfo(operCodeInfo2);
                    operModuleService.updateOperCodeById(id, batchNo, String.valueOf(Status.SHIXIAO.getCode()));
                }

            } else {
                OperStandard operStandard = operModuleService.getStandardByBatchNo(batchNo, id);
                List<Standard> standardList = operModuleService.getStandardBySchemaCode(code);
                if(standardList.size()>0 && code.equalsIgnoreCase(standardList.get(0).getCode())){
                    OperStandard operStandard1 = new OperStandard();
                    operStandard1.setId(uuid);
                    operStandard1.setBatchNo(batchNo);
                    operStandard1.setEnName(operStandard.getEnName());
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
                    operStandard1.setAuthRemark(operStandard.getAuthRemark());
                    operStandard1.setFileId(operStandard.getFileId());
                    operModuleService.insertStandards(operStandard1);
                    String version = String.valueOf(Integer.parseInt(standardList.get(0).getVersion())+1);
                    operModuleService.updateOperStandardById(uuid,version,id,batchNo,category_id,dataType,isAuth);
                    operModuleService.updateStandard(batchNo, id, "1");
                    //字段
                    List<OperStandardField> standardFieldList = operModuleService.getAllFieldsById(id);
                    for (int i = 0; i < standardFieldList.size(); i++) {
                        OperStandardField operStandardField = new OperStandardField();
                        BeanUtils.copyProperties(standardFieldList.get(i),operStandardField);
                        operStandardField.setId(CommonUtil.getUUID());
                        operStandardField.setBatchNo(batchNo);
                        operStandardField.setMoudleId(uuid);
                        operStandardField.setDataType(dataType);
                        operStandardField.setStatus(status);
                        operStandardField.setCreateTime(Calendar.getInstance().getTime());
                        operStandardField.setCreatePerson(this.user.getUsername());
                        operStandardField.setVersion("1");
                        operModuleService.saveNormalFields(operStandardField);
                    }
                    operModuleService.updateFieldsById(String.valueOf(Status.SHIXIAO.getCode()),id);

                } else {

                    if(!ObjectUtils.isEmpty(operStandard)){
                        OperStandard operStandard1 = new OperStandard();
                        operStandard1.setId(uuid);
                        operStandard1.setBatchNo(batchNo);
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
                        operModuleService.insertStandards(operStandard1);
                        operModuleService.updateStandard(batchNo, id, "1");
                    }
                    //字段
                    List<OperStandardField> standardFieldList = operModuleService.getAllFieldsById(id);
                    for (int i = 0; i < standardFieldList.size(); i++) {
                        OperStandardField operStandardField = new OperStandardField();
                        BeanUtils.copyProperties(standardFieldList.get(i),operStandardField);
                        operStandardField.setId(CommonUtil.getUUID());
                        operStandardField.setBatchNo(batchNo);
                        operStandardField.setMoudleId(uuid);
                        operStandardField.setDataType(dataType);
                        operStandardField.setStatus(status);
                        operStandardField.setCreateTime(Calendar.getInstance().getTime());
                        operStandardField.setCreatePerson(this.user.getUsername());
                        operStandardField.setVersion("1");
                        operModuleService.saveNormalFields(operStandardField);
                    }
                    operModuleService.updateFieldsById(String.valueOf(Status.SHIXIAO.getCode()),id);
                }
            }
        }

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }*/

    //申请操作
    @ResponseBody
    @RequestMapping(value = "/applayStandard",method = RequestMethod.POST)
    public BaseResponse<String> applayStandard(@RequestParam String db_id,@RequestParam String category_id){
        BaseResponse<String> response = new BaseResponse<>();
        try {
            operModuleService.applayStandard(db_id,category_id,UserContextUtil.getUserName());
            response.setResultCode(ResultCode.RESULT_SUCCESS);
        } catch (Exception e) {
            String message = e.getMessage();
            response.setResultCode(ResultCode.RESULT_ERROR);
            response.setMessage(message);
        }
        return response;
    }

}
