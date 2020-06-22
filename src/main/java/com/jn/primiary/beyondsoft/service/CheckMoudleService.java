package com.jn.primiary.beyondsoft.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.Connection.DataSoureProvider;
import com.jn.primiary.beyondsoft.dao.*;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.strategy.Main;
import com.jn.primiary.beyondsoft.util.ComUtil;
import com.jn.primiary.beyondsoft.util.GetMoudleListUtil;
import com.jn.primiary.beyondsoft.util.HttpClientUtil;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.beyondsoft.vo.CheckMoudleListVo;
import com.jn.primiary.beyondsoft.vo.FieldCheckResultVo;
import com.jn.primiary.beyondsoft.vo.MoudleListVo;
import com.jn.primiary.metadata.service.ModelService;
import com.jn.primiary.metadata.utils.CommonUtil;
import com.jn.primiary.metadata.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jn.primiary.beyondsoft.vo.DataCheckInfoVo;
import com.jn.primiary.beyondsoft.vo.DataCheckResultVo;
import com.jn.primiary.beyondsoft.vo.DbConnInfoVo;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.util.*;
import java.io.Closeable;
import java.sql.*;
import java.util.Date;
import java.util.stream.Collectors;

//import com.jn.stdtest.entity.ReturnInf;


/**
 * @Des 标准管理业务层
 * @Author chenhong
 * @Date 2019/9/23 10:07
 */
@Service
@Transactional
@CacheConfig(cacheNames = "modelCache")
public class CheckMoudleService {

    @Autowired
    private ModuleRepository schemaModuleRepository;
    @Autowired
    private FieldRepository schemaFieldRepository;
    @Autowired
    private ObjectRepository objectRepository;
    @Autowired
    private CheckRepository fieldCheckRepository;

    @Autowired
    private ModelFieldReposotory modelFieldReposotory;

    @Autowired
    private DQModelRepository dqModelRepository;

    @Autowired
    private StdModelRelationTableRepository stdModelRelationTableRepository;

    @Autowired
    private ModelService modelService;
    @Autowired
    private SysDbRepository sysDbRepository;
    @Autowired
    private ModelnewRepository modelRepository;
    @Autowired
    private CodeInfoRepository codeInfoRepository;
    @Autowired
    private  CheckListRepository checkListRepository;
    @Autowired
    private FieldCodeRepository fieldCodeRepository;

    @Value("${getModelInfo_url}")   //模型字段
    private String getModelInfo_url;

    private Logger logger = Logger.getLogger(CheckMoudleService.class);




    /**
     * 标准检测对比
     *
     * @param model
     * @param stdId
     * @return
     */
    public List<FieldCheckResult> contraststdbyModel(Model model, String stdId, String objId) {
        Map<String,FieldCheckResult> tmpmap = new HashMap<>();
        List<FieldCheckResult> listresult = new LinkedList<>();
        Standard stand = getStandardInfoById(stdId, objId);
        List<ModelField> listmodelfield = model.getFields();
        logger.info("检测请求参数："+JSONArray.toJSONString(listmodelfield));
        List<StandardField> liststdfield = stand.getStandardField();

        for (ModelField field : listmodelfield) {
            FieldCheckResult result = new FieldCheckResult();
            String moudleFieldCode = field.getCode();
            tmpmap.put(moudleFieldCode,result);
            for (StandardField stdfield : liststdfield) {
                result = CompareField(field, stdfield, stand.getName());

                if (result.getCheckResult().equalsIgnoreCase("符合") ) {
                    tmpmap.put(moudleFieldCode,result);
                    break;
                }
                if(result.getCheckResult().equalsIgnoreCase("相似")){
                    if("符合".equals(tmpmap.get(moudleFieldCode).getCheckResult())) break;
                    else tmpmap.put(moudleFieldCode,result);
                }

                if(result.getCheckResult().equalsIgnoreCase("不符合")){
                    if("符合".equals(tmpmap.get(moudleFieldCode).getCheckResult()) || "相似".equals(tmpmap.get(moudleFieldCode).getCheckResult())) ;
                    tmpmap.put(moudleFieldCode,result);
                }

            }
            listresult.add(tmpmap.get(moudleFieldCode));

        }

        //每次检测，这个标准的检测次数就+1
        stand.setCount(stand.getCount()+1);
        schemaModuleRepository.updateStandCount(String.valueOf(stand.getCount()),stand.getId(),stand.getVersion());

        return listresult;
    }

    /**
     *  标准检测 标准 把对象类型平铺开检测
     * @param fields
     * @param stdId
     * @return
     */
    public List<FieldCheckResultVo> contrastbyField(List<ModelField> fields,String stdId){
        Map<String,FieldCheckResultVo> tmpmap = new HashMap<>();
        List<FieldCheckResultVo> listresult = new LinkedList<>();
        Standard stand = getStandardInfoById(stdId, null);
        List<StandardField> liststdfield = stand.getStandardField();
        for (ModelField field : fields) {
            FieldCheckResultVo result = new FieldCheckResultVo();
            String moudleFieldCode = field.getCode();
            tmpmap.put(moudleFieldCode,result);
            for (StandardField stdfield : liststdfield) {
                result = CompareFieldVo(field, stdfield, stand.getName());

                if (result.getCheckResult().equalsIgnoreCase("符合") ) {
                    tmpmap.put(moudleFieldCode,result);
                    break;
                }
                if(result.getCheckResult().equalsIgnoreCase("相似")){
                    if("符合".equals(tmpmap.get(moudleFieldCode).getCheckResult())) break;
                    else tmpmap.put(moudleFieldCode,result);
                }

                if(result.getCheckResult().equalsIgnoreCase("不符合")){
                    if("符合".equals(tmpmap.get(moudleFieldCode).getCheckResult()) || "相似".equals(tmpmap.get(moudleFieldCode).getCheckResult())) ;
                    tmpmap.put(moudleFieldCode,result);
                }

            }
            listresult.add(tmpmap.get(moudleFieldCode));

        }

        //每次检测，这个标准的检测次数就+1
        stand.setCount(stand.getCount()+1);
        schemaModuleRepository.updateStandCount(String.valueOf(stand.getCount()),stand.getId(),stand.getVersion());

        return listresult;
    }

    /**
     * 检测安全字段
     */
    public JSONObject contrastSafeField(List<ModelField> fields,String[] requireModuleFieldArray){
        List<String> requireModuleFieldList = Arrays.asList(requireModuleFieldArray);
        JSONObject resultObj = new JSONObject();
        for (String safeField : requireModuleFieldList) {
            resultObj.put(safeField,"no");
        }

        for (ModelField field : fields) {
            boolean isContain = requireModuleFieldList.contains(field.getEnName());
            if(isContain){
                resultObj.put(field.getEnName(),"yes");
            }
        }
        return resultObj;
    }



    /**
     * 一键检测对比(测试用)
     *
     * @param
     * @param
     * @return
     */
    public void oneClickDetection(String model_id,String username) {
        Model model = dqModelRepository.getmessbyid(model_id);
        List<FieldCheckResult> checkResultList = fieldCheckRepository.findByMoudleId(model_id);
        List<ModelField> modelFieldList = new ArrayList<>();
        for (FieldCheckResult fieldCheckResult : checkResultList) {
            String fieldId = fieldCheckResult.getFieldId();
            ModelField modelField = modelFieldReposotory.getfieldbyid(fieldId);
            modelFieldList.add(modelField);
        }
        model.setFields(modelFieldList);

        String stdId = checkResultList.get(0).getStdname();
        String objId = "";
        //查看一个字段是否是对象类型下的字段
        for (FieldCheckResult tmpresult : checkResultList) {
            if("符合".equals(tmpresult.getCheckResult()) || "相似".equals(tmpresult.getCheckResult())){
                String fieldid = tmpresult.getStdProperty();
                StandardField stdfield = schemaFieldRepository.getFieldByFieldId(fieldid);
                if(!StringUtils.isEmpty(stdfield.getObjId())){
                    objId = stdfield.getObjId();
                }
            }
        }

        String version = schemaModuleRepository.getVersion(stdId);
        //先重新检测
        List<FieldCheckResult> listresult = contraststdbyModel(model, stdId, objId);
        for (FieldCheckResult fieldCheckResult : listresult) {
            fieldCheckResult.setVersion(version);
            fieldCheckResult.setMoudleId(model_id);
        }
        //在保存
        saveCheckResult(listresult,username,stdId,version,model_id,objId);


//        StdModelRelationTable stdmodeltable = stdModelRelationTableRepository.getrelationbymodelid(model_id);
//        String stdId = stdmodeltable.getStd_id();
//        String objId = stdmodeltable.getObj_id();
            //return contraststdbyModel(model,stdId,objId);
    }

    /**
     * 一键检测对比 ， 联调的时候放开(还没有写好，要看返回参数类型)
     * @param model_id
     * @param username
     */
    /*public void oneClickDetection(String model_id,String username,String url) throws Exception {
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("scistor-token",UserContextUtil.getToken());
        url = url+model_id;
        HttpClientUtil httputil = new HttpClientUtil();
        String result = httputil.get(url,headerMap);
        JSONObject obj = JSON.parseObject(result);
        JSONArray dataarray = obj.getJSONArray("data");
        String fieldstr = dataarray.getJSONObject(0).getString("fields");
        JSONArray array = JSON.parseArray(fieldstr);

        Model model = dqModelRepository.getmessbyid(model_id);
        List<FieldCheckResult> checkResultList = fieldCheckRepository.findByMoudleId(model_id);
        List<ModelField> modelFieldList = new ArrayList<>();
        for (FieldCheckResult fieldCheckResult : checkResultList) {
            String fieldId = fieldCheckResult.getFieldId();
            ModelField modelField = modelFieldReposotory.getfieldbyid(fieldId);
            modelFieldList.add(modelField);
        }
        model.setFields(modelFieldList);

        String stdId = checkResultList.get(0).getStdname();
        String objId = "";
        //查看一个字段是否是对象类型下的字段
        for (FieldCheckResult tmpresult : checkResultList) {
            if("符合".equals(tmpresult.getCheckResult()) || "相似".equals(tmpresult.getCheckResult())){
                String fieldid = tmpresult.getStdProperty();
                StandardField stdfield = schemaFieldRepository.getFieldByFieldId(fieldid);
                if(!StringUtils.isEmpty(stdfield.getObjId())){
                    objId = stdfield.getObjId();
                }
            }
        }

        String version = schemaModuleRepository.getVersion(stdId);
        //先重新检测
        List<FieldCheckResult> listresult = contraststdbyModel(model, stdId, objId);
        for (FieldCheckResult fieldCheckResult : listresult) {
            fieldCheckResult.setVersion(version);
            fieldCheckResult.setMoudleId(model_id);
        }
        //在保存
        saveCheckResult(listresult,username,stdId,version,model_id,objId);
    }*/


    public void oneClickDetection(String model_id,String username,String url) throws Exception {
        //获取model的是所有字段
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("scistor-token",UserContextUtil.getToken());
        url = url+model_id;
        HttpClientUtil httputil = new HttpClientUtil();
        String result = httputil.get(url,headerMap);
        JSONObject obj = JSON.parseObject(result);
        JSONArray dataArray = obj.getJSONArray("data");
        Model model = dataArray.getObject(0,Model.class);
        JSONArray fieldArray = dataArray.getJSONObject(0).getJSONArray("fields");
        //存放模型字段map
        Map<String,ModelField> modelFieldMap = new HashMap<>();
        for (int i = 0; i < fieldArray.size(); i++) {
            ModelField modelField = fieldArray.getObject(i,ModelField.class);
            modelFieldMap.put(modelField.getId(),modelField);
        }

        //查询之前de检测结果
        List<FieldCheckResult> checkResultList = fieldCheckRepository.findByMoudleId(model_id);

        //<stdId,对应检测的模型字段>
        Map<String,List<ModelField>> stdIdCheckResultMap = new HashMap<>();
        for (int i = 0; i < checkResultList.size(); i++) {
            String stdId = checkResultList.get(i).getStdname();
            String fieldId = checkResultList.get(i).getFieldId();

            if(stdIdCheckResultMap.containsKey(stdId)){
                stdIdCheckResultMap.get(stdId).add(modelFieldMap.get(fieldId));
            }else{
                List<ModelField> modelFieldGroupByStdIdList = new ArrayList<>();
                modelFieldGroupByStdIdList.add(modelFieldMap.get(fieldId));
                stdIdCheckResultMap.put(stdId,modelFieldGroupByStdIdList);
            }
        }

        //重新检测，并保存
        for(Map.Entry entry : stdIdCheckResultMap.entrySet()){
            String key = entry.getKey().toString();
            List<ModelField> value = (List<ModelField>)entry.getValue();
            model.setFields(value);

            String stdId = key;
            String objId = "";

            String version = schemaModuleRepository.getVersion(stdId);
            //先重新检测
            List<FieldCheckResult> listresult = contraststdbyModel(model, stdId, objId);
            for (FieldCheckResult fieldCheckResult : listresult) {
                fieldCheckResult.setVersion(version);
                fieldCheckResult.setMoudleId(model_id);
            }
            //在保存
            saveCheckResult(listresult,username,stdId,version,model_id,objId);

        }

        //查看一个字段是否是对象类型下的字段
//        for (FieldCheckResult tmpresult : checkResultList) {
//            if("符合".equals(tmpresult.getCheckResult()) || "相似".equals(tmpresult.getCheckResult())){
//                String fieldid = tmpresult.getStdProperty();
//                StandardField stdfield = schemaFieldRepository.getFieldByFieldId(fieldid);
//                if(!StringUtils.isEmpty(stdfield.getObjId())){
//                    objId = stdfield.getObjId();
//                }
//            }
//        }


    }


    /**
     * 获取标准数据
     *
     * @param standardId
     * @return
     */
    public Standard getStandardInfoById(String standardId, String objId) {

        Standard stand = schemaModuleRepository.getStandardById(standardId, null);
        if (stand != null) {
            List<StandardField> fields = schemaFieldRepository.getFieldById(standardId, objId);
            stand.setStandardField(fields);
        }

        return stand;
    }

    /**
     * 对比
     *
     * @param modelfield
     * @param stdfield
     * @param standname
     * @return
     */

    /*
    private FieldCheckResult CompareField(ModelField modelfield, StandardField stdfield, String standname) {
        FieldCheckResult result = new FieldCheckResult();
        boolean iscode = false;
        boolean isenname = false;
        boolean iscname = false;
        //boolean istype=false;
        //boolean issize=false;
        if (modelfield.getCode().equalsIgnoreCase(stdfield.getCode())) {
            iscode = true;
        }
        if (!StringUtils.isEmpty(modelfield.getEnName()) && modelfield.getEnName().equalsIgnoreCase(stdfield.getEnName())) {
            isenname = true;
        }
        if (modelfield.getName().equalsIgnoreCase(stdfield.getName())) {
            iscname = true;
        }
//        if(modelfield.getType().equalsIgnoreCase(stdfield.getType())) {
//            istype=true;
//        }
//        if(modelfield.getMaxsize()==stdfield.getMaxsize()) {
//            issize=true;
//        }
        //if(true==isenname&&true==iscname&&true==istype&&true==issize) {
        if (true == isenname && true == iscname && true == iscode) {
            result.setCheckResult("符合");
            result.setDetail("完全符合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
            result.setStdname(standname);
        } else if (true == isenname && true == iscname && false == iscode) {
            result.setCheckResult("相似");
            result.setDetail("code不吻合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
        }
//        else if(true==isenname&&true==iscname&&true==istype&&false==issize){
//            result.setCheckResult("相似");
//            result.setDetail("长度不吻合");
//            result.setFieldId(modelfield.getId());
//            result.setStdProperty(stdfield.getId());
//
//        }
        //else if(true==isenname&&false==iscname&&true==istype&&true==issize) {
        else if (true == isenname && false == iscname && true == iscode) {
            result.setCheckResult("相似");
            result.setDetail("中文名称不吻合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
        }
        //else if(false==isenname&&true==iscname&&true==istype&&true==issize) {
        else if (false == isenname && true == iscname && true == iscode) {
            result.setCheckResult("相似");
            result.setDetail("英文名称不吻合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
        } else {
            result.setCheckResult("不符合");
            result.setDetail("完全不符合");
            result.setFieldId(modelfield.getId());
        }
        String str = modelfield.toString();

        return result;
    }
*/

    private FieldCheckResult CompareField(ModelField modelfield, StandardField stdfield, String standname) {
        FieldCheckResult result = new FieldCheckResult();
        boolean iscode = false;
        boolean isenname=false;
        boolean iscname = false;
        boolean istype=false;
        boolean issize=false;

        if (!StringUtils.isEmpty(modelfield.getEnName()) && modelfield.getEnName().equalsIgnoreCase(stdfield.getEnName())) {
            isenname = true;
        }
        if (modelfield.getCode().equalsIgnoreCase(stdfield.getCode())) {
            iscode = true;
        }

        if (modelfield.getName().equalsIgnoreCase(stdfield.getName())) {
            iscname = true;
        }
        if(modelfield.getType().equalsIgnoreCase(stdfield.getType())) {
            istype=true;
        }
        if(modelfield.getMaxsize()==stdfield.getMaxsize()) {
            issize=true;
        }
        //if(true==isenname&&true==iscname&&true==istype&&true==issize) {
        if ( true == iscname && true == iscode) {
            result.setCheckResult("符合");
            result.setDetail("完全符合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
            result.setStdname(standname);
        } else if ( true == iscname && false == iscode) {
            result.setCheckResult("相似");
            result.setDetail("code不吻合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
        }else  if(true==isenname){
            result.setCheckResult("相似");
            result.setDetail("英文名相似");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
        }
        else if(true==iscname&&true==istype&&false==issize){
            result.setCheckResult("相似");
            result.setDetail("长度不吻合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());

        }
        //else if(true==isenname&&false==iscname&&true==istype&&true==issize) {
        else if ( false == iscname && true == iscode) {
            result.setCheckResult("相似");
            result.setDetail("中文名称不吻合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
            modelfield.setName(stdfield.getName());
            //modelFieldReposotory.save(modelfield);
        }
        else if(true==iscname&&true==istype&&true==issize) {

            result.setCheckResult("相似");
            result.setDetail("英文名称不吻合");
            result.setFieldId(modelfield.getId());
            result.setStdProperty(stdfield.getId());
        } else {
            result.setCheckResult("不符合");
            result.setDetail("完全不符合");
            result.setFieldId(modelfield.getId());
        }

        result.setVersion(stdfield.getVersion());


        return result;
    }

    private FieldCheckResultVo CompareFieldVo(ModelField modelfield, StandardField stdfield, String standname) {
        FieldCheckResultVo result = new FieldCheckResultVo();
        boolean iscode = false;
        boolean isenname=false;
        boolean iscname = false;
        boolean istype=false;
        boolean issize=false;

        if (!StringUtils.isEmpty(modelfield.getEnName()) && modelfield.getEnName().equalsIgnoreCase(stdfield.getEnName())) {
            isenname = true;
        }
        if (modelfield.getCode().equalsIgnoreCase(stdfield.getCode())) {
            iscode = true;
        }

        if (modelfield.getName().equalsIgnoreCase(stdfield.getName())) {
            iscname = true;
        }
        if(modelfield.getType().equalsIgnoreCase(stdfield.getType())) {
            istype=true;
        }
        if(modelfield.getMaxsize()==stdfield.getMaxsize()) {
            issize=true;
        }
        //if(true==isenname&&true==iscname&&true==istype&&true==issize) {
        if ( true == iscname && true == iscode) {
            result.setCheckResult("符合");
            result.setDetail("完全符合");
            result.setFieldId(modelfield.getId());
            result.setFieldCode(modelfield.getCode());
            result.setStdProperty(stdfield.getId());
            result.setStdname(standname);
            result.setStandardField(stdfield);
        } else if ( true == iscname && false == iscode) {
            result.setCheckResult("相似");
            result.setDetail("code不吻合");
            result.setFieldId(modelfield.getId());
            result.setFieldCode(modelfield.getCode());
            result.setStdProperty(stdfield.getId());
            result.setStandardField(stdfield);
        }else  if(true==isenname){
            result.setCheckResult("相似");
            result.setDetail("英文名相似");
            result.setFieldId(modelfield.getId());
            result.setFieldCode(modelfield.getCode());
            result.setStdProperty(stdfield.getId());
            result.setStandardField(stdfield);
        }
        else if(true==iscname&&true==istype&&false==issize){
            result.setCheckResult("相似");
            result.setDetail("长度不吻合");
            result.setFieldId(modelfield.getId());
            result.setFieldCode(modelfield.getCode());
            result.setStdProperty(stdfield.getId());
            result.setStandardField(stdfield);
        }
        //else if(true==isenname&&false==iscname&&true==istype&&true==issize) {
        else if ( false == iscname && true == iscode) {
            result.setCheckResult("相似");
            result.setDetail("中文名称不吻合");
            result.setFieldId(modelfield.getId());
            result.setFieldCode(modelfield.getCode());
            result.setStdProperty(stdfield.getId());
            modelfield.setName(stdfield.getName());
            //modelFieldReposotory.save(modelfield);
            result.setStandardField(stdfield);
        }
        else if(true==iscname&&true==istype&&true==issize) {

            result.setCheckResult("相似");
            result.setDetail("英文名称不吻合");
            result.setFieldId(modelfield.getId());
            result.setFieldCode(modelfield.getCode());
            result.setStdProperty(stdfield.getId());
            result.setStandardField(stdfield);
        } else {
            result.setCheckResult("不符合");
            result.setDetail("完全不符合");
            result.setFieldId(modelfield.getId());
            result.setFieldCode(modelfield.getCode());
            result.setStandardField(null);
        }

        result.setVersion(stdfield.getVersion());


        return result;
    }

    @CacheEvict(key = "#root.target.moudleListCacheKey()")
    public void saveCheckResult(List<FieldCheckResult> listresult, String username, String std_id, String version,String model_id,String objId) {
        //Standard std = schemaModuleRepository.getstdmessbyidandstatus(std_id);

        for (FieldCheckResult result : listresult) {
            if (result.getFieldId() != null && !result.getFieldId().isEmpty()) {
                FieldCheckResult oldresult = fieldCheckRepository.findByFieldId(result.getFieldId());
                //先查询是否有核标结果，如果有需要先删掉
                if (null != oldresult) {
                    fieldCheckRepository.deleteByFieldId(result.getFieldId());
                }
                result.setId(CommonUtil.getUUID());
                result.setStdname(std_id);
                result.setCheckUser(username);
                result.setCheckDate(new Date());
                result.setObjId(objId);
                fieldCheckRepository.save(result);
            }
        }

        /*StdModelRelationTable relation = stdModelRelationTableRepository.getrelation(model_id);
        if(relation!=null){
            stdModelRelationTableRepository.deleterelation(model_id);
        }

        StdModelRelationTable relationTable = new StdModelRelationTable();
        relationTable.setRelation_id(CommonUtil.getUUID());
        relationTable.setStd_id(std_id);
        relationTable.setModel_id(model_id);
        relationTable.setVersion(version);
        relationTable.setObj_id(objId);
        stdModelRelationTableRepository.save(relationTable);*/

    }



    //测试用
    public List<MoudleListVo> getMoudleList() throws Exception {
        List<MoudleListVo> moudleList =new ArrayList<>();
        List<Object[]> objList=fieldCheckRepository.getMoudleList();
        if(objList.size()>0){
            moudleList=ComUtil.castEntity(objList,MoudleListVo.class);
        }
        //组装VO，查看模型是否做过检测/是否需要重新检测
        for (MoudleListVo moudleListVo:moudleList) {
            //查看模型是否做过检测
            List<FieldCheckResult> list=fieldCheckRepository.findByMoudleId(moudleListVo.getMoudleId());
            //已检测
            if(list.size()>0){
                moudleListVo.setIsCheck("已检测");
                String std_id = list.get(0).getStdname();
                Standard std = schemaModuleRepository.getstdmessbyidandstatus(std_id);

                String version = list.get(0).getVersion();
                if(!std.getVersion().equals(version)){
                    moudleListVo.setIsCheckModify("标准变更");
                }
            }else{
                moudleListVo.setIsCheck("未检测");
            }





            /*if(list.size()>0){
                moudleListVo.setIsCheck("已检测");
                //查看不符合的字段是否已做过变更
                for (FieldCheckResult fieldCheckResult:list) {
                    StandardField field = new StandardField();
                    if(!StringUtils.isEmpty(fieldCheckResult.getStdProperty())){
                        field= schemaFieldRepository.getFieldByFieldId(fieldCheckResult.getStdProperty());
                    }else{
                        continue;
                    }
                    //判断版本
                    if(field!=null){
                        if(!fieldCheckResult.getVersion().equals(field.getVersion())){
                            moudleListVo.setIsCheckModify("标准变更");
                        }
                    }
                }
                //未检测
            }else{
                moudleListVo.setIsCheck("未检测");
            }*/
        }
        return moudleList;
    }


    /*public List<MoudleListVo> getMoudleList(String getAllRegisteredModels_url) throws Exception {
        logger.info("模型列表url："+getAllRegisteredModels_url);
        List<MoudleListVo> moudleList =new ArrayList<>();

        HttpClientUtil httputil = new HttpClientUtil();
        String result = httputil.get(getAllRegisteredModels_url,UserContextUtil.getToken());

        JSONObject obj = JSON.parseObject(result);
        JSONArray array = (JSONArray) obj.get("data");
        //logger.info("模型列表array："+array.toString());
        for (int i = 0; i < array.size(); i++) {
            JSONObject tmpobj = array.getJSONObject(i);
            MoudleListVo tmpmodel = new MoudleListVo();
            tmpmodel.setMoudleName(tmpobj.getString("name"));
            tmpmodel.setEnName(tmpobj.getString("enName"));
            tmpmodel.setMoudleCode(tmpobj.getString("code"));
            tmpmodel.setMoudleDesc(tmpobj.getString("description"));
            tmpmodel.setUpdateDate(tmpobj.getString("updateTime"));
            tmpmodel.setStatus(tmpobj.getString("registerStatus"));
            tmpmodel.setMoudleId(tmpobj.getString("id"));
            moudleList.add(tmpmodel);
        }
        logger.info("模型列表num："+moudleList.size());
        //组装VO，查看模型是否做过检测/是否需要重新检测
        for (MoudleListVo moudleListVo:moudleList) {

            //查看模型是否做过检测
            List<FieldCheckResult> list=fieldCheckRepository.findByMoudleId(moudleListVo.getMoudleId());
            //已检测
            if(list.size()>0){
                try{
                    moudleListVo.setIsCheck("已检测");
                    String std_id = list.get(0).getStdname();
                    Standard std = schemaModuleRepository.getstdmessbyidandstatus(std_id);

                    String version = list.get(0).getVersion();
                    System.out.println(version+"-------"+std.getVersion());
                    if(!std.getVersion().equals(version)){
                        moudleListVo.setIsCheckModify("标准变更");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                moudleListVo.setIsCheck("未检测");
            }
        }

        List<MoudleListVo> sortedlist = moudleList.stream().sorted(Comparator.comparing(MoudleListVo::getIsCheck)).collect(Collectors.toList());
        return sortedlist;
    }*/

    public String moudleListCacheKey() {
        return "moudleListCacheKey";
    }


    @Cacheable(key = "#root.target.moudleListCacheKey()")
    public List<MoudleListVo> getMoudleList(String getAllRegisteredModels_url) throws Exception {
        logger.info("模型列表url："+getAllRegisteredModels_url);

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("scistor-token",UserContextUtil.getToken());
        HttpClientUtil httputil = new HttpClientUtil();
        String result = httputil.get(getAllRegisteredModels_url,headerMap);

        JSONObject obj = JSON.parseObject(result);
        JSONArray array = (JSONArray) obj.get("data");

        List<MoudleListVo> moudleList = Collections.synchronizedList(new ArrayList<MoudleListVo>());
        //index:线程数
        way(array,8,moudleList);

        //List<MoudleListVo> sortedlist = moudleList.stream().sorted(Comparator.comparing(MoudleListVo::getIsCheck)).collect(Collectors.toList());
        //先按检测结果排序，再按时间排序
        moudleList.sort(Comparator.comparing(MoudleListVo::getIsCheck).reversed().thenComparing(MoudleListVo::getUpdateDate).reversed());


        return moudleList;
    }

    public void way(JSONArray array,int index,List<MoudleListVo> sortedlist) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        int listSize = array.size();
        if(index > listSize){
            index = listSize;
        }

        int end = 0;
        int basenum = listSize/index;
        int remainderNum = listSize%index;
        for (int i = 0; i < index; i++) {
            int start = end;
            end = start + basenum;
            if(i == index - 1){
                end = start + basenum + remainderNum;
            }

            Thread thread = new Thread(
                    new GetMoudleListUtil("xc"+String.valueOf(i),array,start,end,sortedlist));
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }


    }


    /**
     * 查看检测结果 （测试用）
     * @param moudleId
     * @return
     * @throws Exception
     */
    public List<CheckMoudleListVo> getCheckMoudleList(String moudleId) throws Exception {
        List<CheckMoudleListVo> moudleList=new ArrayList<>();
        //查询检测结果
        //List<Object[]> objectList = fieldCheckRepository.getCheckMoudleList(moudleId);
        //FIXME 后续通过调接口获取模型字段，获取到数据后组装返回给页面的VO
        /****************************************************************/
        List<Object[]> objList=fieldCheckRepository.getMoudleFieldList(moudleId);
        if(objList.size()>0){
            moudleList=ComUtil.castEntity(objList,CheckMoudleListVo.class);
        }
        /****************************************************************/
        for (CheckMoudleListVo checkMoudleListVo:moudleList) {
            FieldCheckResult checkResult=fieldCheckRepository.getCheckMoudle(checkMoudleListVo.getMoudleId(),checkMoudleListVo.getFieleId());
            if(checkResult!=null){
                checkMoudleListVo.setCheckDate(checkResult.getCheckDate().toString());
                checkMoudleListVo.setCheckResult(checkResult.getCheckResult());

                String std_id = checkResult.getStdname();
                Standard std = schemaModuleRepository.getstdmessbyidandstatus(std_id);
                checkMoudleListVo.setStd_id(std_id);
                checkMoudleListVo.setStd_cname(std.getName());

                String relate_field_id = checkResult.getStdProperty();
                if(!StringUtils.isEmpty(relate_field_id)){
                    StandardField field = schemaFieldRepository.getFieldByFieldId(relate_field_id);
                    if(!StringUtils.isEmpty(field.getObjId())){
                        StandardObject stdobj = objectRepository.getObjectByObjid(field.getObjId());
                        checkMoudleListVo.setObj_id(field.getObjId());
                        checkMoudleListVo.setObj_cname(stdobj.getObjCname());
                    }else{
                        checkMoudleListVo.setObj_id("");
                        checkMoudleListVo.setObj_cname("");
                    }
                    checkMoudleListVo.setRelate_field_id(relate_field_id);
                    checkMoudleListVo.setRelate_field_name(field.getName());
                }
            }
        }
        return moudleList;
    }

    /**
     * 查看检测结果 (正式联调的时候放开)
     * @param moudleId
     * @return
     * @throws Exception
     */
    public List<CheckMoudleListVo> getCheckMoudleList(String moudleId,String url) throws Exception {
        List<CheckMoudleListVo> moudleList=new ArrayList<>();

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("scistor-token",UserContextUtil.getToken());
        HttpClientUtil httputil = new HttpClientUtil();
        String result = httputil.get(url,headerMap);
        JSONObject obj = JSON.parseObject(result);

        JSONArray dataarray = obj.getJSONArray("data");
        String modulename = dataarray.getJSONObject(0).getString("name");
        String moduleid = dataarray.getJSONObject(0).getString("id");
        String fieldstr = dataarray.getJSONObject(0).getString("fields");
        JSONArray array = JSON.parseArray(fieldstr);
        List<ModelField> modelFields = JSONArray.parseArray(array.toString(), ModelField.class);

        //List<Object[]> objList=fieldCheckRepository.getMoudleFieldList(moudleId);
        //if(objList.size()>0){
        //    moudleList=ComUtil.castEntity(objList,CheckMoudleListVo.class);
        //}

        for (int i = 0; i < modelFields.size(); i++) {
            CheckMoudleListVo check = new CheckMoudleListVo();
            check.setFieldName(modelFields.get(i).getName());
            check.setEnName(modelFields.get(i).getEnName());
            check.setFieldCode(modelFields.get(i).getCode());
            check.setFieleId(modelFields.get(i).getId());
            check.setMoudleId(moduleid);

            //获取模型的 一个字段的检测结果
            FieldCheckResult singleFieldcheckResult = fieldCheckRepository.findByMoudleIdAndFieldId(moduleid, modelFields.get(i).getId());
            if(singleFieldcheckResult != null){
                //有检测结果的字段
                String stdId = singleFieldcheckResult.getStdname();
                //检测结果对应的 标准信息
                Standard std = schemaModuleRepository.getStandardById(stdId, "");
                check.setStd_id(std.getId());
                check.setStd_cname(std.getName());

                String stdFieldId = singleFieldcheckResult.getStdProperty();

                if(!StringUtils.isEmpty(stdFieldId)){
                    //检测结果 对应的 标准字段信息
                    StandardField standardField = schemaFieldRepository.getFieldByFieldId(stdFieldId);
                    check.setRelate_field_id(standardField.getId());
                    check.setRelate_field_name(standardField.getName());

                }


            }

            moudleList.add(check);

        }

        for (CheckMoudleListVo checkMoudleListVo:moudleList) {
            FieldCheckResult checkResult=fieldCheckRepository.getCheckMoudle(checkMoudleListVo.getMoudleId(),checkMoudleListVo.getFieleId());
            if(checkResult!=null){
                checkMoudleListVo.setCheckDate(checkResult.getCheckDate().toString());
                checkMoudleListVo.setCheckResult(checkResult.getCheckResult());
            }
        }
        return moudleList;
    }


    /**
     * 查看检测结果 (测试用)
     * @param moudleId
     * @return
     * @throws Exception
     */
    /*public List<CheckMoudleListVo> getCheckMoudleList(String moudleId,String url) throws Exception {
        List<CheckMoudleListVo> moudleList=new ArrayList<>();

        HttpClientUtil httputil = new HttpClientUtil();
        String result = httputil.get(url);
        JSONObject obj = JSON.parseObject(result);
        JSONArray dataarray = obj.getJSONArray("data");
        String fieldstr = dataarray.getJSONObject(0).getString("fields");
        JSONArray array = JSON.parseArray(fieldstr);
        List<ModelField> modelFields = JSONArray.parseArray(array.toString(), ModelField.class);

        //List<Object[]> objList=fieldCheckRepository.getMoudleFieldList(moudleId);
        if(objList.size()>0){
            moudleList=ComUtil.castEntity(objList,CheckMoudleListVo.class);
        }

        for (CheckMoudleListVo checkMoudleListVo:moudleList) {
            FieldCheckResult checkResult=fieldCheckRepository.getCheckMoudle(checkMoudleListVo.getMoudleId(),checkMoudleListVo.getFieleId());
            if(checkResult!=null){
                checkMoudleListVo.setCheckDate(checkResult.getCheckDate().toString());
                checkMoudleListVo.setCheckResult(checkResult.getCheckResult());
            }
        }
        return moudleList;
    }*/


    /**
     * (正式联调的时候放开)
     */
    public List<ModelField> getMoudleField(String model_id ,String getModelInfo_url) throws Exception {
        String url = getModelInfo_url+model_id;
        logger.info("模型字段url："+url);
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("scistor-token",UserContextUtil.getToken());
        HttpClientUtil httputil = new HttpClientUtil();
        String result = httputil.get(url,headerMap);
        JSONObject obj = JSON.parseObject(result);
        JSONArray dataarray = obj.getJSONArray("data");
        String fieldstr = dataarray.getJSONObject(0).getString("fields");
        JSONArray array = JSON.parseArray(fieldstr);
        List<ModelField> modelFields = JSONArray.parseArray(array.toString(), ModelField.class);
        return modelFields;
    }

    //测试用
    public List<ModelField> getMoudleField(String model_id){
        return modelFieldReposotory.getfieldbymodelid(model_id);

    }


    public  List<StdCheckModelEntity> getmodelInfobytdpro(List<String> liststdpro) throws Exception {
        List<StdCheckModelEntity> listCheckModel = new LinkedList<StdCheckModelEntity>();
        if(liststdpro!=null){
            if(liststdpro.size()>0){
                for(String stdpro:liststdpro){
                    List<FieldCheckResult> listcheckresult=fieldCheckRepository.findByStdProperty(stdpro);
                    if(null==listcheckresult) continue;
                    if(listcheckresult.size()==0) continue;

                    for(FieldCheckResult checkresult:listcheckresult){
                        StdCheckModelEntity checkModel=new StdCheckModelEntity();
                        checkModel.setCheckresult(checkresult.getCheckResult());
                        checkModel.setDetail(checkresult.getDetail());
                        checkModel.setFieldid(checkresult.getFieldId());
                        checkModel.setStdfieldid(stdpro);

                        com.jn.primiary.metadata.entity.Model model;



                        if(!StringUtils.isEmpty(getModelInfo_url)){
                            model = modelService.getModelInfo(checkresult.getMoudleId(),getModelInfo_url);
                            if(model==null) continue;

                            checkModel.setModelcnname(model.getName());
                            checkModel.setModelcode(model.getCode());
                            checkModel.setModelid(model.getId());

                            List<com.jn.primiary.metadata.entity.ModelField> listmodelfield=model.getFields();
                            if(listmodelfield==null) continue;
                            if(listmodelfield.size()==0) continue;
                            for(com.jn.primiary.metadata.entity.ModelField modelField:listmodelfield){
                                if(modelField.getId().equalsIgnoreCase(checkresult.getFieldId())){
                                    checkModel.setFieldcnname(modelField.getName());
                                    checkModel.setFieldcode(modelField.getCode());
                                }
                            }




                        }
                        listCheckModel.add(checkModel);

                    }

                }
            }

        }




        return listCheckModel;
    }

    public  List<StdCheckModelEntity> getmodelInfobytdpro(String stdId) throws Exception {
        List<String> liststdpro=schemaFieldRepository.getFieldIdByStdId(stdId);
        return getmodelInfobytdpro(liststdpro);

    }



    public  List<StdCheckModelEntity> getmodelInfobystd(List<String> liststd) throws Exception {
        List<StdCheckModelEntity> listCheckModel = new LinkedList<StdCheckModelEntity>();
        if(liststd!=null){
            if(liststd.size()>0){
                for(String std:liststd){
//                    List<String> stdpros=fieldCheckRepository.findByStdname(std);
                    Standard stdmodule=schemaModuleRepository.getstdmessbyidandstatus(std);
//                    for(String stdpro:stdpros){
                    List<String> listreult=fieldCheckRepository.findByStdname(std);
                    if(null==listreult) continue;
                    if(listreult.size()==0) continue;

//                    for(FieldCheckResult checkresult:listcheckresult){
//                        List<String> listreult=listcheckresult.stream().map(FieldCheckResult::getMoudleId).collect(Collectors.toList());
                    List<String> listreult2=new ArrayList<>();
                    for(int i=0;i<listreult.size();i++){
                        if(!listreult2.contains(listreult.get(i))){
                            listreult2.add(listreult.get(i));
                        }
                    }

                    for(String a:listreult2){
                        StdCheckModelEntity checkModel=new StdCheckModelEntity();
                        // checkModel.setCheckresult(checkresult.getCheckResult());
                        // checkModel.setDetail(checkresult.getDetail());
                        // checkModel.setFieldid(checkresult.getFieldId());
                        // checkModel.setStdfieldid(stdpro);
                        checkModel.setStdmodelid(stdmodule.getId());
                        checkModel.setStdmodelcode(stdmodule.getCode());
                        checkModel.setStdmodelcnname(stdmodule.getName());
                        com.jn.primiary.metadata.entity.Model model;

                        if(!StringUtils.isEmpty(getModelInfo_url)){
                            model = modelService.getModelInfo(a,getModelInfo_url);
                            if(model==null) continue;

                            checkModel.setModelcnname(model.getName());
                            checkModel.setModelcode(model.getCode());
                            checkModel.setModelid(model.getId());

//                            List<com.jn.primiary.metadata.entity.ModelField> listmodelfield=model.getFields();
//                            if(listmodelfield==null) continue;
//                            if(listmodelfield.size()==0) continue;
//                            for(com.jn.primiary.metadata.entity.ModelField modelField:listmodelfield){
//                                if(modelField.getId().equalsIgnoreCase(checkresult.getFieldId())){
//                                    checkModel.setFieldcnname(modelField.getName());
//                                    checkModel.setFieldcode(modelField.getCode());
//                                }
//                            }




                        }
                        listCheckModel.add(checkModel);}


//                    }

//                }
                }
            }

        }




        return listCheckModel;
    }



    /*
     *
     *
     *
     * */
    public void checkAllModel(){

        List<String> listmodelcode=checkListRepository.getAllmodelcode();
        if(listmodelcode.size()==0) return;
        for(String modelcode:listmodelcode){
            List<Checklist> listneedcheck=checkListRepository.findByModelCode(modelcode);
            if(listneedcheck==null) continue;
            if(listneedcheck.size()==0) continue;
            for(Checklist needcheck:listneedcheck){
                Model model = getModelbyId(needcheck.getModelId());
                List<FieldCheckResult> listcheckresult=contraststdbyModel(model,needcheck.getStdId(),needcheck.getObjId());
                saveCheckResult(listcheckresult,needcheck.getStdId(),listcheckresult.get(0).getVersion(),model.getId(),needcheck.getObjId());
            }
        }


        return;
    }

    private Model getModelbyId(String modelid){
        Model model=modelRepository.findById(modelid);
        model.setFields(modelFieldReposotory.getfieldbymodelid(modelid));
        return model;
    }

    /*
     * add by wld 2020.04.16专门为保存全部检测结果增加的接口
     * */
    public void saveCheckResult(List<FieldCheckResult> listresult,  String std_id, String version,String model_id,String objId) {
        //Standard std = schemaModuleRepository.getstdmessbyidandstatus(std_id);

        for (FieldCheckResult result : listresult) {
            if(result.getCheckResult().equalsIgnoreCase("不符合")) continue;
            if (result.getFieldId() != null && !result.getFieldId().isEmpty()) {
                FieldCheckResult oldresult = fieldCheckRepository.findByFieldId(result.getFieldId());
                //先查询是否有核标结果，如果有需要先删掉
                if (null != oldresult) {
                    fieldCheckRepository.deleteByFieldId(result.getFieldId());
                }
                result.setId(CommonUtil.getUUID());
                result.setStdname(std_id);
                result.setCheckUser("admin");
                result.setMoudleId(model_id);
                result.setVersion(version);
                result.setObjId(objId);
                result.setCheckDate(new Date());
                fieldCheckRepository.save(result);
            }
        }



    }
    /*
     * add by wld 2020.04.16
     * */
    public List<String> getMoudleFieldList(){
        return fieldCheckRepository.findallStd();
    }


    public  List<StdCheckModelEntity> getallmodelInfobytdpro(List<String> liststd) throws Exception {
        List<StdCheckModelEntity> listCheckModel = new LinkedList<StdCheckModelEntity>();
        if(liststd!=null){
            if(liststd.size()>0){
                for(String std:liststd){
//                    List<String> stdpros=fieldCheckRepository.findByStdname(std);
                    Standard stdmodule=schemaModuleRepository.getstdmessbyidandstatus(std);
//                    for(String stdpro:stdpros){
                    List<String> listreult=fieldCheckRepository.findByStdname(std);
                    if(null==listreult) continue;
                    if(listreult.size()==0) continue;

//                    for(FieldCheckResult checkresult:listcheckresult){
//                        List<String> listreult=listcheckresult.stream().map(FieldCheckResult::getMoudleId).collect(Collectors.toList());
                    List<String> listreult2=new ArrayList<>();
                    for(int i=0;i<listreult.size();i++){
                        if(!listreult2.contains(listreult.get(i))){
                            listreult2.add(listreult.get(i));
                        }
                    }

                    for(String a:listreult2){
                        StdCheckModelEntity checkModel=new StdCheckModelEntity();
                        // checkModel.setCheckresult(checkresult.getCheckResult());
                        // checkModel.setDetail(checkresult.getDetail());
                        // checkModel.setFieldid(checkresult.getFieldId());
                        // checkModel.setStdfieldid(stdpro);
                        checkModel.setStdmodelid(stdmodule.getId());
                        checkModel.setStdmodelcode(stdmodule.getCode());
                        checkModel.setStdmodelcnname(stdmodule.getName());
                        com.jn.primiary.metadata.entity.Model model;

                        if(!StringUtils.isEmpty(getModelInfo_url)){
                            model = modelService.getModelInfo(a,getModelInfo_url);
                            if(model==null) continue;

                            checkModel.setModelcnname(model.getName());
                            checkModel.setModelcode(model.getCode());
                            checkModel.setModelid(model.getId());

//                            List<com.jn.primiary.metadata.entity.ModelField> listmodelfield=model.getFields();
//                            if(listmodelfield==null) continue;
//                            if(listmodelfield.size()==0) continue;
//                            for(com.jn.primiary.metadata.entity.ModelField modelField:listmodelfield){
//                                if(modelField.getId().equalsIgnoreCase(checkresult.getFieldId())){
//                                    checkModel.setFieldcnname(modelField.getName());
//                                    checkModel.setFieldcode(modelField.getCode());
//                                }
//                            }




                        }
                        listCheckModel.add(checkModel);}


//                    }

//                }
                }
            }

        }




        return listCheckModel;
    }




    public List<SysDb> getDbList(String moudleTableDataSource_url,String getDataSourceById_url,String moudleId) throws Exception {
        List<SysDb> list= new ArrayList<>();

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("scistor-token",UserContextUtil.getToken());

        HttpClientUtil httpClientUtil = new HttpClientUtil();
        JSONObject paramObj = new JSONObject();
        paramObj.put("id",moudleId);
        String dataSourceIds = httpClientUtil.postWithJson(moudleTableDataSource_url, paramObj.toString(),headerMap);
        logger.info("模型所在数据源："+JSONObject.parseObject(dataSourceIds));
        //JSONArray dataArray = JSONObject.parseObject(dataSourceIds).getJSONArray("data");

        String dataSourceIdStr= JSONObject.parseObject(dataSourceIds).getString("data");
        List<String> dataSourceIdList = JSONArray.parseArray(dataSourceIdStr, String.class);
        Set<String> dataSourceIdSet = new HashSet(dataSourceIdList);

        for (String dataSourceId : dataSourceIdSet) {
            String url = getDataSourceById_url+dataSourceId;
            String dataSourceMess = httpClientUtil.get(url, headerMap);
            logger.info("数据源信息："+JSONObject.parseObject(dataSourceMess));
            JSONArray dataSourceMessArray = JSONObject.parseObject(dataSourceMess).getJSONArray("data");

            if(dataSourceMessArray != null){
                for (int j = 0; j < dataSourceMessArray.size(); j++) {
                    JSONObject dataSourceObj = dataSourceMessArray.getJSONObject(j);
                    SysDb sysDb = new SysDb();
                    sysDb.setSysName(dataSourceObj.getString("dbCode"));
                    sysDb.setDbName(dataSourceObj.getString("dbname"));
                    sysDb.setDbDriver(dataSourceObj.getString("dbdriver"));
                    sysDb.setDbUsername(dataSourceObj.getString("user"));
                    sysDb.setDbpwd(dataSourceObj.getString("password"));
                    sysDb.setDbUrl(dataSourceObj.getString("url"));
                    sysDb.setId(CommonUtil.getUUID());
                    list.add(sysDb);
                }
            }

        }

//            String dataSource = dataArray.getJSONObject(i).getString("dataSource");
//            List<SysDb> tmpList = sysDbRepository.findSysDbBySysName(dataSource);
//            list.addAll(tmpList);

        return list;
    }

    public List<DataCheckResultVo> doCheckData(Map<String,Object> map) throws Exception {

        if(!map.get("driver").toString().contains("gbase") &&
                !map.get("driver").toString().contains("odps") &&
                !map.get("driver").toString().contains("postgresql")){
            throw new CommonException("目前支持gbase，pgsql，odps。暂不支持其他数据库类型");
        }

        /*****************页面选择的数据库信息start*********************/
        DbConnInfoVo dbInfo=new DbConnInfoVo();
        dbInfo.setDriveName(map.get("driver").toString());
        dbInfo.setUrl(map.get("dbUrl").toString());
        dbInfo.setSchema(map.get("dbName").toString());
        dbInfo.setUsername(map.get("dbUsername").toString());
        dbInfo.setPassword(map.get("dbpwd").toString());
        /*****************页面选择的数据库信息end*********************/
        /*****************数据检测信息start*********************/
        DataCheckInfoVo dciv=new DataCheckInfoVo();
        dciv.setTableName(map.get("checkTableName").toString());    //页面选择的检测表
        List<String> fieldList=new ArrayList<>();   //字段idList
        List<String> fieldNameList=new ArrayList<>();   //字段名称list
        List<Integer> checkLengthFlagsList=new ArrayList<>();   //是否检查长度
        List<Integer> checkMaxLengthValuesList=new ArrayList<>();   //字段对应最大长度
        List<Integer> checkNullFlagsList=new ArrayList<>();     //字段是否可为空
        List<Integer> checkCodeFlagsList=new ArrayList<>();     //字段是否有对应码表
        List<List<String>> checkCodeValuesList=new ArrayList<>();   //码表内容
        //根据传过来的表id查询下面的字段检测结果
        List<FieldCheckResult> checkList=fieldCheckRepository.findByMoudleId(map.get("checkTableId").toString());
        for (FieldCheckResult rs:checkList) {
            //只对符合的数据做数据检测
            if(rs.getCheckResult().equals("符合")){
                //查询对应标准字段信息
                StandardField field=schemaFieldRepository.getFieldByFieldId(rs.getStdProperty());
                fieldList.add(field.getId());
                fieldNameList.add(field.getName());
                checkLengthFlagsList.add(BooleanFlag.SHI.getCode());
                checkMaxLengthValuesList.add(field.getMaxsize());
                //TODO 这里有个问题，是否必填在数据库里怎么表示？这里先写死必填
                checkNullFlagsList.add(BooleanFlag.SHI.getCode());
                //查询字段是否有对应码表信息
                String code=fieldCodeRepository.getCodeTableByFeildId(field.getId());
                //是
                if(!StringUtils.isEmpty(code)){
                    checkCodeFlagsList.add(BooleanFlag.SHI.getCode());
                    List<CodeInfo> codeInfoList=codeInfoRepository.getCodeInfoByName(code);
                    List<String> codeValues=new ArrayList<>();
                    for (CodeInfo codeInfo:codeInfoList){
                        codeValues.add(codeInfo.getCodeValue());
                    }
                    checkCodeValuesList.add(codeValues);
                    //否
                }else{
                    checkCodeFlagsList.add(BooleanFlag.FOU.getCode());
                    checkCodeValuesList.add(new ArrayList<>());
                }
            }
        }
        dciv.setColIds(fieldList);
        dciv.setColNames(fieldNameList);
        dciv.setCheckLengthFlags(checkLengthFlagsList);
        dciv.setCheckMaxLengthValues(checkMaxLengthValuesList);
        dciv.setCheckNullFlags(checkNullFlagsList);
        dciv.setCheckCodeFlags(checkCodeFlagsList);
        dciv.setCheckCodeValues(checkCodeValuesList);
        /*****************数据检测信息end*********************/
        //调用数据检测方法
        List<DataCheckInfoVo> list=new ArrayList<>();
        list.add(dciv);
        List<DataCheckResultVo> result=checkData(dbInfo,list);
        return result;
    }

    /**
     * 用于检测数据的方法，提供字符长度、数据为空、码值范围检测。
     * @param db_info   DbConnInfoVo对象，表示数据库连接信息。
     * @param dc_infos  DataCheckInfoVo数组，表示检测信息。
     * @return  DataCheckResultVo数组，表示检测结果信息
     * @throws SQLException 执行SQL发生异常时会抛出该异常。
     */
    /*private List<DataCheckResultVo> checkData(DbConnInfoVo db_info, List<DataCheckInfoVo> dc_infos) throws Exception {
        List<DataCheckResultVo> dcs = new ArrayList<>();
        //1、根据数据库信息创建连接
        String driveName = db_info.getDriveName();
        String url = db_info.getUrl();
        String username = db_info.getUsername();
        String password = db_info.getPassword();
        String schema = db_info.getSchema();
        DriverManagerDataSource dataSource = DataSoureProvider.getInstance(driveName, url, username, password, schema);
        try(Connection conn = dataSource.getConnection()) {
            //2、根据检测数据信息，构建SQL，并执行SQL
            for(DataCheckInfoVo dc_info : dc_infos) {
                String tableName = dc_info.getTableName();
                List<String> colId = dc_info.getColIds();
                List<String> colNames = dc_info.getColNames();
                List<Integer> checkLengthFlags = dc_info.getCheckLengthFlags();
                List<Integer> checkMaxLengthValues = dc_info.getCheckMaxLengthValues();
                List<Integer> checkNullFlags = dc_info.getCheckNullFlags();
                List<Integer> checkCodeFlags = dc_info.getCheckCodeFlags();
                List<List<String>> checkCodeValues = dc_info.getCheckCodeValues();
                String sql = "SELECT COUNT(*) as num FROM " + tableName;

                for(int i = 0; i < colNames.size(); i++) {
                    String colName = colNames.get(i);
                    Integer checkLengthFlag = checkLengthFlags.get(i);
                    Integer checkNullFlag = checkNullFlags.get(i);
                    Integer checkCodeFlag = checkCodeFlags.get(i);

                    DataCheckResultVo dc = new DataCheckResultVo();
                    dc.setId(colId.get(i));
                    dc.setName(colName);
                    //检查字段的字节数是否超过最大值，查询出字节数超过最大长度的数据量
                    if(BooleanFlag.SHI.getCode() == checkLengthFlag) {
                        String checkSql = sql + " WHERE LENGTH(" + colName + ") > ?";
                        PreparedStatement preState = conn.prepareStatement(checkSql);
                        preState.setInt(1, checkMaxLengthValues.get(i));
                        ResultSet rs = preState.executeQuery();
                        if(rs.next()) {
                            Integer num = rs.getInt("num");
                            dc.setOverLenNum(num);
                        }
                    }
                    //检查字段的数据是否为空，查询出数据为空的数据量
                    if(BooleanFlag.SHI.getCode() == checkNullFlag) {
                        String checkSql = sql + " WHERE " + colName + " IS NULL";
                        PreparedStatement preState = conn.prepareStatement(checkSql);
                        ResultSet rs = preState.executeQuery();
                        if(rs.next()) {
                            Integer num = rs.getInt("num");
                            dc.setNullNum(num);
                        }
                    }
                    //检查字段的数据是否在码值范围，查询出不在码值范围的数据量
                    if(BooleanFlag.SHI.getCode() == checkCodeFlag) {
                        List<String> checkCodeValue = checkCodeValues.get(i);
                        String checkSql = sql + " WHERE " + colName + " NOT IN ?";
                        PreparedStatement preState = conn.prepareStatement(checkSql);
                        Array formatArray = conn.createArrayOf("varchar", checkCodeValue.toArray());
                        preState.setArray(1, formatArray);
                        ResultSet rs = preState.executeQuery();
                        if(rs.next()) {
                            Integer num = rs.getInt("num");
                            dc.setNotInCodeNum(num);
                        }
                    }
                    dcs.add(dc);
                }
            }
        }
        //手动关闭数据库连接，try()写法也会关闭数据库，DataSoureProvider实现了Closeable也会关闭数据库
        DataSoureProvider.manuClose();

        //3、返回检测信息，以字段为单位，每个字段的检测结果为一行数据
        return dcs;
    }*/

    private List<DataCheckResultVo> checkData(DbConnInfoVo db_info, List<DataCheckInfoVo> dc_infos) throws Exception {
        List<DataCheckResultVo> dcs = new ArrayList<>();
        //1、根据数据库信息创建连接
        String driveName = db_info.getDriveName();
        String url = db_info.getUrl();
        String username = db_info.getUsername();
        String password = db_info.getPassword();
        String schema = db_info.getSchema();
        //DriverManagerDataSource dataSource = DataSoureProvider.getInstance(driveName, url, username, password, schema);

        DataSoureProvider  provider = new DataSoureProvider(url,driveName, username, password);
        Connection conn = provider.getConnection();
        try{
            //2、根据检测数据信息，构建SQL，并执行SQL
            for(DataCheckInfoVo dc_info : dc_infos) {
                String tableName = dc_info.getTableName();
                List<String> colId = dc_info.getColIds();
                List<String> colNames = dc_info.getColNames();
                List<Integer> checkLengthFlags = dc_info.getCheckLengthFlags();
                List<Integer> checkMaxLengthValues = dc_info.getCheckMaxLengthValues();
                List<Integer> checkNullFlags = dc_info.getCheckNullFlags();
                List<Integer> checkCodeFlags = dc_info.getCheckCodeFlags();
                List<List<String>> checkCodeValues = dc_info.getCheckCodeValues();
                String sql = "SELECT COUNT(*) as num FROM " + tableName;

                for(int i = 0; i < colNames.size(); i++) {
                    String colName = colNames.get(i);
                    Integer checkLengthFlag = checkLengthFlags.get(i);
                    Integer checkNullFlag = checkNullFlags.get(i);
                    Integer checkCodeFlag = checkCodeFlags.get(i);

                    DataCheckResultVo dc = new DataCheckResultVo();
                    dc.setId(colId.get(i));
                    dc.setName(colName);
                    //检查字段的字节数是否超过最大值，查询出字节数超过最大长度的数据量
                    if(BooleanFlag.SHI.getCode() == checkLengthFlag) {
                        String checkSql = sql + " WHERE LENGTH('" + colName + "') > ?";
                        PreparedStatement preState = conn.prepareStatement(checkSql);
                        preState.setInt(1, checkMaxLengthValues.get(i));
                        ResultSet rs = preState.executeQuery();
                        if(rs.next()) {
                            Integer num = rs.getInt("num");
                            dc.setOverLenNum(num);
                        }
                    }
                    //检查字段的数据是否为空，查询出数据为空的数据量
                    if(BooleanFlag.SHI.getCode() == checkNullFlag) {
                        String checkSql = sql + " WHERE '" + colName + "' IS NULL";
                        PreparedStatement preState = conn.prepareStatement(checkSql);
                        ResultSet rs = preState.executeQuery();
                        if(rs.next()) {
                            Integer num = rs.getInt("num");
                            dc.setNullNum(num);
                        }
                    }
                    //检查字段的数据是否在码值范围，查询出不在码值范围的数据量
                    if(BooleanFlag.SHI.getCode() == checkCodeFlag) {
                        List<String> checkCodeValue = checkCodeValues.get(i);
                        String checkSql = sql + " WHERE '" + colName + "' NOT IN ?";
                        PreparedStatement preState = conn.prepareStatement(checkSql);
                        Array formatArray = conn.createArrayOf("varchar", checkCodeValue.toArray());
                        preState.setArray(1, formatArray);
                        ResultSet rs = preState.executeQuery();
                        if(rs.next()) {
                            Integer num = rs.getInt("num");
                            dc.setNotInCodeNum(num);
                        }
                    }
                    dcs.add(dc);
                }
            }
            conn.close();
        }catch(Exception e){
            conn.close();
            throw new Exception();
        }


        //3、返回检测信息，以字段为单位，每个字段的检测结果为一行数据
        return dcs;
    }

    /*static class DataSoureProvider implements Closeable{
        private static Connection conn;

        public static Connection getConnection( String driveName,String url,String username,String password, String schema){
            try {
                Class.forName(driveName);
                conn = DriverManager.getConnection(url,username,password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }

        public static void manuClose(){
            try {
                if(!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close(){
            try {
                if(!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    /**
     * 连接外部数据库的支持类，实例会常驻内存。
     */
    /*private static class DataSoureProvider implements Closeable {
        private static DriverManagerDataSource dataSource = new DriverManagerDataSource();

        public static DriverManagerDataSource getInstance(String driverName, String url, String username,
                                                          String password, String schema) {
            dataSource.setDriverClassName(driverName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setSchema(schema);

            return dataSource;
        }

        static void manuClose() {
            try {
                Connection conn = dataSource.getConnection();
                if(!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            DataSoureProvider.manuClose();
        }
    }*/


    public JSONArray getMoudleTableDataSource(String moudleTableDataSource_url,String param) throws Exception {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("userId","1");
        String result = httpClientUtil.postWithJson(moudleTableDataSource_url, param, headerMap);

        /**
         * {
         *     "resultCode": "RESULT_SUCCESS",
         *     "message": "getDBResourceByTable接口调用成功",
         *    "data": [
         *         {
         *             "ower": "ysdb",
         *             "dataSourceId": "WX474000",
         *             "id": "WX476894",
         *             "dataSource": "Mysql_254"
         *         }
         *     ]
         * }
         */
        JSONObject resultObj = JSONObject.parseObject(result);
        logger.info("模型所在库的接口返回信息："+resultObj.toString());
        JSONArray dataArray = resultObj.getJSONArray("data");
        return dataArray;



    }




}
