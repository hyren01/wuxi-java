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
@RequestMapping("ag/yyxt")
public class LogController {


    @ResponseBody
    @RequestMapping(value = "/reportLog", method = RequestMethod.GET)
    public BaseResponse<YwLog> getLastestLog() {
        APIController apiController = new APIController();
        return apiController.getLastestLog();
    }

}
