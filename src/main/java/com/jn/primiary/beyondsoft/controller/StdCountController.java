package com.jn.primiary.beyondsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.service.StdCountService;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("stdglprj/stdcount")
public class StdCountController{
    @Autowired
    private StdCountService stdCountService;

    @ResponseBody
    @RequestMapping(value = "/getcountmess",method= RequestMethod.GET)
    public BaseResponse<JSONObject> getcountmess(){
        BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();
        JSONObject resultobj = stdCountService.getcountmess();
        List<JSONObject> list = new ArrayList<>();
        list.add(resultobj);
        response.setData(list);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    };
}
