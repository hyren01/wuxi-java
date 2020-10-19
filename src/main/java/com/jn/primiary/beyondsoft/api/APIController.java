package com.jn.primiary.beyondsoft.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.api.service.APIService;
import com.jn.primiary.beyondsoft.api.vo.StandardVo;
import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.beyondsoft.entity.yunwei.YwData;
import com.jn.primiary.beyondsoft.entity.yunwei.YwLog;
import com.jn.primiary.beyondsoft.interceptor.AuthenticationInterceptor;
import com.jn.primiary.beyondsoft.monitor.StdMonitor;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Des 对外接口
 * @Author chenhong
 * @Date 2019/10/24 16:05
 */
@RestController
@RequestMapping("stdglprj/standard")
public class APIController {

    @Autowired
    APIService apiService;

    @Value("${logreport.systemNumber}")
    private String systemNumber;
    private Logger logger = Logger.getLogger(APIController.class);
    private Date date = new Date();
    /**
     * 标准列表
     *
     * @return
     */
    @RequestMapping(value = "/getStandardsByCategoryId/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<StandardVo> getStandardsByCategoryId(@PathVariable("categoryId") String categoryId) throws Exception {
        BaseResponse<StandardVo> response = new BaseResponse<StandardVo>();
        List<StandardVo> liststand = apiService.getStandardByCategoryId(categoryId);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(liststand);
        response.setMessage("获取信息成功");
        return response;
    }

    @RequestMapping(value = "/getStandardInfo/{standardId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<StandardVo> getStandardInfo(@PathVariable("standardId") String standardId) throws Exception {
        BaseResponse<StandardVo> response = new BaseResponse<StandardVo>();
        List<StandardVo> liststand = apiService.getStandardInfo(standardId);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(liststand);
        response.setMessage("获取信息成功");
        return response;
    }

    //获取全部标准目录
    @RequestMapping(value = "/getAllStandardCategorys", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<JSONArray> getAllStandardCategorys() {
        List<CategoryInfo> categoryNameAndIdList = apiService.getAllCategory();

        JSONArray array = (JSONArray) JSONArray.toJSON(categoryNameAndIdList);
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            obj.put("bmid", "");
            obj.put("fullbmid", "");
        }

        List<JSONArray> list = new ArrayList<>();
        list.add(array);

        BaseResponse<JSONArray> response = new BaseResponse<JSONArray>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        return response;
    }


    /**
     * 1.1.2　 获取指定标准目录的子目录信息
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/getStandardCategorys/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<JSONArray> getStandardCategorys(@PathVariable("parentId") String parentId) {
        List<CategoryInfo> childCategoryList = apiService.findChildCategory(parentId);

        JSONArray array = (JSONArray) JSONArray.toJSON(childCategoryList);
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            obj.put("bmid", "");
            obj.put("fullbmid", "");
        }

        List<JSONArray> list = new ArrayList<>();
        list.add(array);

        BaseResponse<JSONArray> response = new BaseResponse<JSONArray>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        response.setMessage("获取子目录成功");
        return response;
    }


    @RequestMapping(value = "/getAllSubStandardCategorys/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<JSONArray> getAllSubStandardCategorys(@PathVariable("parentId") String parentId) {
        List<CategoryInfo> childCategoryList = apiService.getAllSubStandardCategorys(parentId);

        JSONArray array = (JSONArray) JSONArray.toJSON(childCategoryList);
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            obj.put("bmid", "");
            obj.put("fullbmid", "");
        }

        List<JSONArray> list = new ArrayList<>();
        list.add(array);

        BaseResponse<JSONArray> response = new BaseResponse<JSONArray>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        response.setMessage("获取子目录成功");
        return response;
    }

    //获取最新日志
    @ResponseBody
    @RequestMapping(value = "/getLastestLog", method = RequestMethod.GET)
    public BaseResponse<YwLog> getLastestLog() {
        List<YwData> YwDatalist = AuthenticationInterceptor.getLoglist();

        logger.info(JSON.toJSONString(YwDatalist));
        YwLog ywLog = new YwLog();
        ywLog.setData(YwDatalist);
        ywLog.setSystemNumber(new StdMonitor().getSystemNumber());
        ywLog.setSystemAddress(new StdMonitor().getSystemAddress());
        List<YwLog> list = new ArrayList<>();
        list.add(ywLog);
        BaseResponse<YwLog> response = new BaseResponse<YwLog>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        response.setMessage("获取日志成功");
        return response;
    }

    public YwData getLastestLog(String url) {
        String lasturl = url;
        if (url.indexOf("/") != -1) {
            lasturl = url.substring(url.indexOf("/"));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = df.format(date);
        YwData ywData = new YwData();
        ywData.setLogType(1);
        ywData.setStartTime(dateString);
        ywData.setEndTime(dateString);
        ywData.setSubjectName("超级管理员");
        ywData.setSubjectAddress("172.16.35.245");
        ywData.setSubjectID("数据标准管理");
        if (lasturl.toLowerCase().contains("get") || lasturl.toLowerCase().contains("query") || lasturl.toLowerCase().contains("index")) {
            ywData.setOperation("105");
        } else if (lasturl.toLowerCase().contains("save")) {
            ywData.setOperation("102");
        } else if (lasturl.toLowerCase().contains("delete")) {
            ywData.setOperation("104");
        }else {
            ywData.setOperation("100");
        }
        ywData.setSubSystem("数据标准管理分系统_" + lasturl);
        ywData.setModule(lasturl);
        ywData.setTaskCode(lasturl);
        ywData.setTaskName(lasturl);
        ywData.setObjectType("1");
        ywData.setOutcome("0");
        ywData.setCondition("");
        ywData.setMessage("超级管理员访问了url地址："+url);
        JSONArray description = new JSONArray();
        description.add("超级管理员访问了url地址："+url);
        JSONArray objectInfo = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("address","172.16.35.245");
//        object.put("address",new StdMonitor().getSystemAddress());
        object.put("description", description);
        objectInfo.add(object);
        ywData.setObjectInfo(objectInfo);
        date = new Date();
        return ywData;
    }

}
