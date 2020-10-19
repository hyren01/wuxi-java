package com.jn.primiary.beyondsoft.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jn.primiary.beyondsoft.entity.*;
import com.jn.primiary.beyondsoft.service.CheckMoudleService;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.beyondsoft.vo.CheckMoudleListVo;
import com.jn.primiary.beyondsoft.vo.DataCheckResultVo;
import com.jn.primiary.beyondsoft.vo.FieldCheckResultVo;
import com.jn.primiary.beyondsoft.vo.MoudleListVo;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.*;


/**
 * @Des 标准检测控制层
 * @Author chenhong
 * @Date 2019/10/27 10:07
 */
@Validated
@Controller
@RequestMapping("stdglprj/checkmoudle")
public class CheckMoudleController {

    @Autowired
    CheckMoudleService checkService;

    @Value("${getAllRegisteredModels_url}") //模型列表
    private String getAllRegisteredModels_url;
    @Value("${getModelInfo_url}")   //模型字段
    private String getModelInfo_url;

    @Value("${getModuleDBbyMoudleId_url}")   //模型列表 获取表所在 库的信息
    private String getModuleDBbyMoudleId_url;

    @Value("${getDataSourceById_url}")   //获取数据源信息
    private String getDataSourceById_url;

    //安全字段
    String[] requireModuleFieldArray = {"mdSecDeg", "fileSecDeg", "unitID", "siteID", "means",
            "line", "eqpID", "direction", "speciality", "mission",
            "domain", "tarTyp", "truOrFal", "secDegProl"};

    /**
     * 标准检测
     *
     * @param moudleId 标准ID
     * @param objId    对象ID,可为空
     * @param model    检测模型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/contrastbyModel", method = RequestMethod.POST)
    public BaseResponse contrastbyModel(@Pattern(regexp = "^\\w+$", message = "请输入正确的moudleId") @RequestParam String moudleId,
                                                          @RequestParam String objId,
                                                          @RequestBody Model model) throws Exception {
        BaseResponse response = new BaseResponse<FieldCheckResult>();
        if (StringUtils.isEmpty(moudleId)) throw new Exception("标准ID不能为空");
        List<FieldCheckResult> listresult = checkService.contraststdbyModel(model, moudleId, objId);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(listresult);
        response.setMessage("对比标准结果成功");
        return response;
    }



    /**
     * @param moudleId
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/contrastbyField", method = RequestMethod.POST)
    public BaseResponse<FieldCheckResultVo> contrastbyField(@RequestParam String moudleId,
                                                            @RequestBody Model model) throws Exception {
        BaseResponse<FieldCheckResultVo> response = new BaseResponse<FieldCheckResultVo>();
        if (StringUtils.isEmpty(moudleId)) throw new CommonException("标准ID不能为空");
        //测试用
        List<FieldCheckResultVo> listresult = checkService.contrastbyField(model.getFields(), moudleId);

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(listresult);
        response.setMessage("对比标准结果成功");
        return response;
    }

    /**
     * 检测安全字段
     *
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/contrastSafeField", method = RequestMethod.POST)
    public BaseResponse<JSONObject> contrastSafeField(@RequestBody Model model) throws Exception {
        BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();
        //测试用
        JSONObject resultObj = checkService.contrastSafeField(model.getFields(), requireModuleFieldArray);
        List<JSONObject> list = new ArrayList<>();
        list.add(resultObj);

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        response.setMessage("对比标准结果成功");
        return response;
    }


    /**
     * 一键检测
     *
     * @param model_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/oneClickDetection", method = RequestMethod.POST)
    public BaseResponse<FieldCheckResult> oneClickDetection(@RequestParam String model_id) {
        BaseResponse<FieldCheckResult> response = new BaseResponse<FieldCheckResult>();
        try {
            if (StringUtils.isEmpty(getAllRegisteredModels_url) && StringUtils.isEmpty(getModelInfo_url)) {
                //本地测试用
                checkService.oneClickDetection(model_id, UserContextUtil.getUserName());
            } else {
                //联调时候用
                checkService.oneClickDetection(model_id, UserContextUtil.getUserName(), getModelInfo_url);
            }

            response.setResultCode(ResultCode.RESULT_SUCCESS);
            response.setMessage("对比标准结果成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setResultCode(ResultCode.RESULT_SUCCESS);
            response.setMessage("对比标准结果出错");
        }

        return response;
    }


    /**
     * 保存检测结果
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResponse<String> save(@RequestBody JSONObject paramObj) {
        String model_id = paramObj.getString("model_id");
        String obj_id = paramObj.getString("obj_id");
        String std_id = paramObj.getString("std_id");
        String version = paramObj.getString("version");
        List<FieldCheckResult> listresult = paramObj.getJSONArray("tosave").toJavaList(FieldCheckResult.class);

        BaseResponse<String> response = new BaseResponse<String>();
        checkService.saveCheckResult(listresult, UserContextUtil.getUserName(), std_id, version, model_id, obj_id);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage("存储核标结果数据成功");
        return response;
    }

    /**
     * 模型列表（正式联调的时候放开）
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getMoudleList", method = RequestMethod.GET)
    public BaseResponse<MoudleListVo> getMoudleList() throws Exception {
        BaseResponse<MoudleListVo> response = new BaseResponse<MoudleListVo>();
        List<MoudleListVo> moudleList = null;
        if (StringUtils.isEmpty(getAllRegisteredModels_url)) {
            //本地的模型库，测试用
            moudleList = checkService.getMoudleList();
        } else {
            //正式联调
            moudleList = checkService.getMoudleList(getAllRegisteredModels_url);
        }
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(moudleList);
        response.setMessage("获取数据成功");
        return response;
    }

    /**
     * 获取模型列表字段信息（正式联调的时候放开）
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getMoudleField", method = RequestMethod.POST)
    public BaseResponse<ModelField> getMoudleField(@RequestParam(value = "moudleId") String moudleId) throws Exception {
        BaseResponse<ModelField> response = new BaseResponse<ModelField>();
        List<ModelField> fieldlist = null;
        if (StringUtils.isEmpty(getModelInfo_url)) {
            //本地测试用
            fieldlist = checkService.getMoudleField(moudleId);
        } else {
            fieldlist = checkService.getMoudleField(moudleId, getModelInfo_url);
        }

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(fieldlist);
        response.setMessage("获取数据成功");
        return response;
    }

    /**
     * 查看检测结果
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getCheckMoudleList", method = RequestMethod.POST)
    public BaseResponse<CheckMoudleListVo> getCheckMoudleList(@RequestParam String moudleId) throws Exception {
        BaseResponse<CheckMoudleListVo> response = new BaseResponse<CheckMoudleListVo>();
        List<CheckMoudleListVo> moudleList = null;
        if (StringUtils.isEmpty(getAllRegisteredModels_url) && StringUtils.isEmpty(getModelInfo_url)) {
            //测试用
            moudleList = checkService.getCheckMoudleList(moudleId);
        } else {
            //正式联调的时候放开
            String url = getModelInfo_url + moudleId;
            moudleList = checkService.getCheckMoudleList(moudleId, url);
        }

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(moudleList);
        response.setMessage("获取数据成功");
        return response;
    }

    /**
     * add by wld 2020.04.10
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getmodelinfobystdpro", method = RequestMethod.POST)
    public BaseResponse<StdCheckModelEntity> getmodelInfobytdpro(@RequestBody List<String> liststdpro) throws Exception {
        BaseResponse<StdCheckModelEntity> response = new BaseResponse<StdCheckModelEntity>();
        List<StdCheckModelEntity> listreult = checkService.getmodelInfobytdpro(liststdpro);


        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(listreult);
        response.setMessage("获取数据成功");
        return response;
    }


    /**
     * add by wld 2020.04.18
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getmodelinfobystdid", method = RequestMethod.POST)
    public BaseResponse<StdCheckModelEntity> getmodelInfobytdid(@RequestParam String stdId) throws Exception {
        BaseResponse<StdCheckModelEntity> response = new BaseResponse<StdCheckModelEntity>();
        List<StdCheckModelEntity> listreult = checkService.getmodelInfobytdpro(stdId);


        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(listreult);
        response.setMessage("获取数据成功");
        return response;
    }

    /**
     * add by mqy 2020.04.13
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getallmodelinfobystdpro", method = RequestMethod.GET)
    public BaseResponse<StdCheckModelEntity> getallmodelInfobytdpro() throws Exception {
        BaseResponse<StdCheckModelEntity> response = new BaseResponse<StdCheckModelEntity>();
        List<String> liststd = checkService.getMoudleFieldList();
        List<StdCheckModelEntity> listreult = checkService.getallmodelInfobytdpro(liststd);

//        for(int i=0;i<listreult.size();i++){
//            if(!listreult2.contains(listreult.get(i))){
//                listreult2.add(listreult.get(i));
//            }
//        }
        Set<StdCheckModelEntity> setdata = new HashSet<StdCheckModelEntity>();
        setdata.addAll(listreult);
        List<StdCheckModelEntity> listreult2 = new ArrayList<StdCheckModelEntity>(setdata);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(listreult2);
        response.setMessage("获取数据成功");
        return response;
    }

    /**
     * add by wld 2020.04.16 增加根据配置对所有的模型进行标准检测的功能
     * 需要先将所有的模型与字段导入标准数据库
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/checkallmodel", method = RequestMethod.GET)
    public BaseResponse<Object> checkAllModel() throws Exception {
        BaseResponse<Object> response = new BaseResponse<Object>();


        checkService.checkAllModel();


        response.setResultCode(ResultCode.RESULT_SUCCESS);

        response.setMessage("检测所有已经配置的模型完毕");
        return response;
    }


    /**
     * 查看检测结果
     *
     * @return
     * @throws Exception
     */
//    @ResponseBody
//    @RequestMapping(value = "/getDbList", method = RequestMethod.POST)
//    public BaseResponse<SysDb> getDbList() throws Exception {
//        BaseResponse<SysDb> response = new BaseResponse<SysDb>();
//        List<SysDb> dbList = checkService.getDbList();
//
//        response.setResultCode(ResultCode.RESULT_SUCCESS);
//        response.setData(dbList);
//        response.setMessage("获取数据成功");
//        return response;
//    }
    @ResponseBody
    @RequestMapping(value = "/getDbList", method = RequestMethod.POST)
    public BaseResponse<SysDb> getDbList(@RequestBody Map<String, Object> map) throws Exception {
        BaseResponse<SysDb> response = new BaseResponse<SysDb>();
        String str = JSONObject.toJSONString(map);
        String moudleId = (String) map.get("moudleId");
        List<SysDb> dbList = checkService.getDbList(getModuleDBbyMoudleId_url, getDataSourceById_url, moudleId);

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(dbList);
        response.setMessage("获取数据成功");
        return response;
    }


    @ResponseBody
    @RequestMapping(value = "/getDataCheck", method = RequestMethod.POST)
    public BaseResponse<DataCheckResultVo> getDataCheck(@RequestBody Map<String, Object> map) throws Exception {
        BaseResponse<DataCheckResultVo> response = new BaseResponse<DataCheckResultVo>();
        //List<SysDb> dbList = checkService.getDbList();
        List<DataCheckResultVo> result = checkService.doCheckData(map);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(result);
        response.setMessage("获取数据成功");
        return response;
    }

    /**
     * #模型列表 获取表所在 库的信息
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMoudleTableDataSource", method = RequestMethod.POST)
    public BaseResponse<JSONArray> getMoudleTableDataSource(@RequestBody Map<String, Object> map) throws Exception {
        BaseResponse<JSONArray> response = new BaseResponse<JSONArray>();

        //String schemaCode = (String) map.get("schemaCode");
        String str = JSONObject.toJSONString(map);
        checkService.getMoudleTableDataSource(getModuleDBbyMoudleId_url, str);

        return response;
    }


}
