package com.jn.primiary.beyondsoft.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.dao.CategoryRepository;
import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.beyondsoft.service.SchemaModuleService;
import com.jn.primiary.beyondsoft.util.YunWeiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.jn.primiary.beyondsoft.service.DSStatistService;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;

/**
 * 数据标准统计Controller
 */
@Controller
@RequestMapping("stdglprj/statist")
@Api(value = "模型列表api")
public class DSStatistController {

    @Autowired
    private DSStatistService service;


    /**
     * @Title: statist
     * @Description: 现有模型数
     */
    @ResponseBody
    @RequestMapping(value = "/existModels", method = RequestMethod.GET)
    @ApiOperation(value = "返回现有模型数 ")
    public BaseResponse<Integer> existModels() {
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        int existModelsNum = service.getExistModelsNum();
        List<Integer> resultList = new ArrayList<>();
        resultList.add(existModelsNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取现有模型数成功");
        return response;
    }

    /**
     * @Title: statist
     * @Description: 已检测模型数
     */
    @ResponseBody
    @RequestMapping(value = "/detectedModels", method = RequestMethod.GET)
    @ApiOperation(value = "返回已检测模型数")
    public BaseResponse<Integer> detectedModels() {
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        int detectedModelsNum = service.getDetectedModelsNum();
        List<Integer> resultList = new ArrayList<>();
        resultList.add(detectedModelsNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取已检测模型数成功");
        return response;
    }

    /**
     * @Title: statist
     * @Description: 现有标准模型数
     */
    @ResponseBody
    @RequestMapping(value = "/standardModels", method = RequestMethod.GET)
    @ApiOperation(value = "返回现有标准模型数")
    public BaseResponse<Integer> standardModels() {
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        int standardModelsNum = service.getStandardModelsNum();
        List<Integer> resultList = new ArrayList<>();
        resultList.add(standardModelsNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取现有标准模型数成功");
        return response;
    }

    /**
     * @Title: statist
     * @Description: 现有标准模型数
     */
    @ResponseBody
    @RequestMapping(value = "/standardModelsByCategoryId", method = RequestMethod.POST)
    @ApiOperation(value = "根据目录ID,返回目录下标准模型数")
    public BaseResponse<Integer> standardModelsByCategoryId(@RequestBody Map<String, String> map) {
        String categoryid = map.get("categoryid");
        BaseResponse<Integer> response = new BaseResponse<Integer>();
        List<String> childCategoryIdList = new ArrayList<>();
        childCategoryIdList.add(categoryid);
        getCategoryChild(categoryid, childCategoryIdList);
        int standardModelsNum = 0;
        for (String everycategoryid : childCategoryIdList) {
            standardModelsNum += service.getStandardModelsNumByCategoryID(everycategoryid);
        }
        List<Integer> resultList = new ArrayList<>();
        resultList.add(standardModelsNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取现有标准模型数成功");
        return response;
    }

    //递归找某个目录下的子目录
    public void getCategoryChild(String categoryId, List<String> childCategoryIdList) {
        List<String> categoryInfoList = service.findCategoryInfoByParentId(categoryId);
        if (categoryInfoList.size() > 0) {
            for (int i = 0; i < categoryInfoList.size(); i++) {
                childCategoryIdList.add(categoryInfoList.get(i));
                getCategoryChild(categoryInfoList.get(i), childCategoryIdList);
            }
        }
    }

    /**
     * @Title: statist
     * @Description: 现有标准字段数
     */
    @ResponseBody
    @RequestMapping(value = "/standardFields", method = RequestMethod.GET)
    @ApiOperation(value = "返回现有标准字段数")
    public BaseResponse<Integer> standardFields() {
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        int standardFieldsNum = service.getStandardFieldsNum();
        List<Integer> resultList = new ArrayList<>();
        resultList.add(standardFieldsNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取现有标准字段数成功");
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/standardFieldsByCategoryId", method = RequestMethod.POST)
    @ApiOperation(value = "根据ID返回现有标准字段数")
    public BaseResponse<Integer> standardFieldsByCategoryId(@RequestBody Map<String, String> map) {
        BaseResponse<Integer> response = new BaseResponse<Integer>();
        String categoryid = map.get("categoryid");
        List<String> childCategoryIdList = new ArrayList<>();
        childCategoryIdList.add(categoryid);
        getCategoryChild(categoryid, childCategoryIdList);
        int standardFieldsNum = 0;
        for (String everycategoryid : childCategoryIdList) {
            standardFieldsNum += service.getStandardFieldsNumByCategoryId(everycategoryid);
        }
        List<Integer> resultList = new ArrayList<>();
        resultList.add(standardFieldsNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取现有标准字段数成功");
        return response;
    }


    /**
     * @Title: statist
     * @Description: 现有码表数量
     */
    @ResponseBody
    @RequestMapping(value = "/codeInfos", method = RequestMethod.GET)
    @ApiOperation(value = "返回现有码表数量")
    public BaseResponse<Integer> codeInfos() {
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        int codeinfoNum = service.getCodeInfoNum();
        List<Integer> resultList = new ArrayList<>();
        resultList.add(codeinfoNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取码表数量成功");
        return response;
    }


    /**
     * @Title: statist
     * @Description: 导入文档数量
     */
    @ResponseBody
    @RequestMapping(value = "/filenum", method = RequestMethod.GET)
    @ApiOperation(value = "返回导入文档数量")
    public BaseResponse<Integer> filenum() {
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        int fileNum = service.getFileNum();
        List<Integer> resultList = new ArrayList<>();
        resultList.add(fileNum);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取文件数量成功");
        return response;
    }

    /**
     * 获取所有统计信息
     * 业务运维
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllStatist", method = RequestMethod.GET)
    @ApiOperation(value = "返回监控数据")
    public BaseResponse<JSONObject> getAllStatist() {
        BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();
        List<JSONObject> resultList = new ArrayList<>();

        //用户数量
        JSONObject resultObj = new JSONObject();
        int userNum = service.getUserNum();
        resultObj.put("userNum", userNum);

        //文档数量
        int fileNum = service.getFileNum();
        resultObj.put("fileNum", fileNum);

        //标准数量
        int stdNum = service.getStandardModelsNum();
        resultObj.put("stdNum", stdNum);
        resultList.add(resultObj);

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取信息成功");
        return response;
    }

    /**
     * 获取所有统计信息
     * 基础运维
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBaseStatist", method = RequestMethod.GET)
    @ApiOperation(value = "返回监控数据")
    public BaseResponse<JSONObject> getBaseStatist() {
        BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();
        YunWeiUtil util = new YunWeiUtil();
        List<JSONObject> resultList = new ArrayList<>();

        JSONObject resultObj = new JSONObject();
        //获取内存使用率
        String memery = util.getMemery();
        resultObj.put("memery", memery);
        //获取CPU使用率
        String cpu = util.getMemery();
        resultObj.put("cpu", cpu);
        //获取当前运行路径
        String path = util.getPath();
        resultObj.put("path", path);
        //获取本地IP
        String localip = util.getLocalIP();
        resultObj.put("localip", localip);
        //获取操作系统类型
        String ostype = util.getOsType();
        resultObj.put("ostype", ostype);
        resultList.add(resultObj);

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(resultList);
        response.setMessage("获取信息成功");
        return response;
    }
}
