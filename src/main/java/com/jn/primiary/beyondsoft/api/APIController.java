package com.jn.primiary.beyondsoft.api;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.api.service.APIService;
import com.jn.primiary.beyondsoft.api.vo.StandardVo;
import com.jn.primiary.beyondsoft.entity.CategoryInfo;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Des 对外接口
 * @Author chenhong
 * @Date 2019/10/24 16:05
 */
@RestController
@RequestMapping("/standard")
public class APIController{

    @Autowired
    APIService apiService;


    /**
     * 标准列表
     *
     * @return
     */
    @RequestMapping(value = "/getStandardsByCategoryId/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<StandardVo> getStandardsByCategoryId(@PathVariable("categoryId") String categoryId)throws Exception{
        BaseResponse<StandardVo> response = new BaseResponse<StandardVo>();
        List<StandardVo> liststand = apiService.getStandardByCategoryId(categoryId);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(liststand);
        response.setMessage("获取信息成功");
        return response;
    }

    @RequestMapping(value = "/getStandardInfo/{standardId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse<StandardVo> getStandardInfo(@PathVariable("standardId") String standardId)throws Exception{
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

        JSONArray array = (JSONArray)JSONArray.toJSON(categoryNameAndIdList);
        for (int i = 0; i <array.size() ; i++) {
            JSONObject obj = (JSONObject)array.get(i);
            obj.put("bmid","");
            obj.put("fullbmid","");
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
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/getStandardCategorys/{parentId}",method= RequestMethod.GET)
    @ResponseBody
    public BaseResponse<JSONArray> getStandardCategorys(@PathVariable("parentId") String parentId){
        List<CategoryInfo> childCategoryList = apiService.findChildCategory(parentId);

        JSONArray array = (JSONArray)JSONArray.toJSON(childCategoryList);
        for (int i = 0; i <array.size() ; i++) {
            JSONObject obj = (JSONObject)array.get(i);
            obj.put("bmid","");
            obj.put("fullbmid","");
        }

        List<JSONArray> list = new ArrayList<>();
        list.add(array);

        BaseResponse<JSONArray> response = new BaseResponse<JSONArray>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        response.setMessage("获取子目录成功");
        return response;
    }



    @RequestMapping(value = "/getAllSubStandardCategorys/{parentId}",method= RequestMethod.GET)
    @ResponseBody
    public BaseResponse<JSONArray> getAllSubStandardCategorys(@PathVariable("parentId") String parentId){
        List<CategoryInfo> childCategoryList = apiService.getAllSubStandardCategorys(parentId);

        JSONArray array = (JSONArray)JSONArray.toJSON(childCategoryList);
        for (int i = 0; i <array.size() ; i++) {
            JSONObject obj = (JSONObject)array.get(i);
            obj.put("bmid","");
            obj.put("fullbmid","");
        }

        List<JSONArray> list = new ArrayList<>();
        list.add(array);

        BaseResponse<JSONArray> response = new BaseResponse<JSONArray>();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setData(list);
        response.setMessage("获取子目录成功");
        return response;
    }

}
