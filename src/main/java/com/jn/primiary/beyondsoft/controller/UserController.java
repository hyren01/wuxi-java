package com.jn.primiary.beyondsoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.entity.CommonException;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.beyondsoft.service.UserService;
import com.jn.primiary.beyondsoft.strategy.aspect.AbstracyAspectStrategy;
import com.jn.primiary.beyondsoft.util.HttpClientUtil;
import com.jn.primiary.beyondsoft.util.UkeyTokenUtil;
import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import com.jn.primiary.metadata.utils.StringUtils;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "/sysuser")
@Api(value = "用户登录api")
public class UserController {
    @Value("${spring.profiles.active}")
    private String tokenAspectType;

    @Value("${cmsLoginUrl}")
    private String cmsLoginUrl;

    @Autowired
    private UserService service;
    @Autowired
    private HttpClientUtil httpClientUtil;
    //@Autowired
    //private UkeyTokenUtil ukeyTokenUtil;
    private Logger logger = Logger.getLogger(UserController.class);

    /**
     * 登录获取token
     *
     * @param
     * @param
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value="根据用户名密码返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "password", value = "密码", required = true, dataType = "String")
    })
    public BaseResponse<SysUser> login(@RequestBody SysUser sysuser){
        String username = sysuser.getUsername();
        String password = sysuser.getPassword();
        logger.info(username+"---"+password);
        BaseResponse<SysUser> response = new BaseResponse<SysUser>();
        try {
            SysUser user = service.login(username);
            if(password.equals(user.getPassword())){
                Map<String,Object> map = new HashMap<>();
                map.put("password",password);
                String token = jwtUtil.createJwt(user.getId(), username, map);

                SysUser userrole = service.findbyid(user.getId());
                List<SysUser> list = new ArrayList<>();
                list.add(userrole);

                HttpSession session = this.request.getSession();
                session.setAttribute("Authorization",token);
                session.setAttribute("user",userrole);
                session.setMaxInactiveInterval(60*60*24);

                response.setData(list);
                response.setMessage(token);
                response.setResultCode(ResultCode.RESULT_SUCCESS);
            }else{
                response.setMessage("登录失败，请检查用户名密码！");
                response.setResultCode(ResultCode.RESULT_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage("登录失败，请检查用户名密码！");
            response.setResultCode(ResultCode.RESULT_ERROR);
        }

        return response;
    }*/
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<JSONObject> login(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();
        JSONObject resultObj = new JSONObject();
        resultObj.put("loginType", tokenAspectType);
        if ("pro".equals(tokenAspectType)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (StringUtils.isEmpty(username)) throw new CommonException("请传入用户名");
            if (StringUtils.isEmpty(password)) throw new CommonException("请传入密码");

            JSONObject obj = new JSONObject();
            obj.put("account", username);
            obj.put("password", DigestUtils.md5DigestAsHex(password.getBytes()));//密码用md5加密
            obj.put("showAppInfo", "true");
            obj.put("syscode", "'stdcode'");
            String result = httpClientUtil.postWithJson(cmsLoginUrl, obj.toString());
            JSONObject scistorObj = JSONObject.parseObject(result);
            resultObj.put("data", scistorObj);

            response.setOtherData(resultObj);
            response.setMessage(scistorObj.getJSONObject("data").getString("tokenInfo"));
            response.setResultCode(ResultCode.RESULT_SUCCESS);
        } else if ("ukey".equals(tokenAspectType)) {
            // String result = httpClientUtil.get(trx_gettoken_url);
            String token = request.getParameter("token");
            String appId = request.getParameter("appId");
            if (StringUtils.isEmpty(appId)) throw new CommonException("请传入AppId");
            if (StringUtils.isEmpty(token)) throw new CommonException("请传入token");

            UkeyTokenUtil ukeyTokenUtil = new UkeyTokenUtil();
            SysUser user = ukeyTokenUtil.validateLoginByCas(token, appId, request);
            //List<SysUser> list = new ArrayList<>();
            //list.add(user);
            resultObj.put("data", user);

            response.setOtherData(resultObj);
            response.setMessage(token);
            response.setResultCode(ResultCode.RESULT_SUCCESS);

        } else {
            //dev
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (StringUtils.isEmpty(username)) throw new CommonException("请传入用户名");
            if (StringUtils.isEmpty(password)) throw new CommonException("请传入密码");
            SysUser user = service.login(username);
            if (password.equals(user.getPassword())) {
                Map<String, Object> map = new HashMap<>();
                map.put("password", password);

                SysUser userrole = service.findbyid(user.getId());
                // List<SysUser> list = new ArrayList<>();
                // list.add(userrole);
                resultObj.put("data", userrole);
                response.setOtherData(resultObj);
                response.setMessage("test_token");
                response.setResultCode(ResultCode.RESULT_SUCCESS);
            } else {
                throw new CommonException("登录失败，请检查用户名密码！");
            }
/*
            response.setData(null);
            response.setMessage("token");
            response.setResultCode(ResultCode.RESULT_SUCCESS);*/
        }

        return response;
    }


    @ResponseBody
    @RequestMapping(value = "/cmslogin", method = RequestMethod.POST)
    public BaseResponse<JSONObject> cmslogin(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();
        JSONObject resultObj = new JSONObject();
        String token = request.getParameter("tokenid");
        String syscode = request.getParameter("syscode");
        if (StringUtils.isEmpty(syscode)) throw new CommonException("请传入syscode");
        if (StringUtils.isEmpty(token)) throw new CommonException("请传入tokenid");

        UkeyTokenUtil ukeyTokenUtil = new UkeyTokenUtil();
        SysUser user = ukeyTokenUtil.validateLoginByCas(token, syscode, request);
        resultObj.put("data", user);

        response.setOtherData(resultObj);
        response.setMessage(token);
        response.setResultCode(ResultCode.RESULT_SUCCESS);


        return response;
    }


}
