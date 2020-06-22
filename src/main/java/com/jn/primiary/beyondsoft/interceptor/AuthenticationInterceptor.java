package com.jn.primiary.beyondsoft.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.strategy.aspect.AbstracyAspectStrategy;
import com.jn.primiary.beyondsoft.strategy.aspect.DevAspect;
import com.jn.primiary.beyondsoft.strategy.aspect.SaiSiAspect;
import com.jn.primiary.beyondsoft.strategy.aspect.UkeyAspect;
import com.jn.primiary.beyondsoft.strategy.intercepter.AbstractInterceptorStrategy;
import com.jn.primiary.beyondsoft.strategy.intercepter.DevInterceptor;
import com.jn.primiary.beyondsoft.strategy.intercepter.SaiSiInterceptor;
import com.jn.primiary.beyondsoft.strategy.intercepter.UkeyInterceptor;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.commons.StringUtils;
import com.jn.primiary.metadata.entity.ResultCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(AuthenticationInterceptor.class);
    @Value("${spring.profiles.active}")
    private String tokenAspectType;

    private String[] allowurl={
            //登录
            "/stdglprj/sysuser/login",
            //swagger
            "/stdglprj/swagger-ui.html",
            //文件上传
            //"/stdglprj/docmodel/addstdbyfile",
            //文件下载
            "/stdglprj/docmodel/downloadfile",

            //标准查询
            "/stdglprj/newstandard/getStandardById",
            //标准检测
            "/stdglprj/checkmoudle/contrastbyField",
            "/stdglprj/checkmoudle/contrastbyModel",
            //数据检测
            "/stdglprj/checkmoudle/getDataCheck",
            //excel模板下载
            "/stdglprj/newstandard/getExcelTemp",
            //导出标准
            "/stdglprj/newstandard/exportStandardToExcel"
            //根据id获取目录信息
            //"/stdglprj/category/getCategoryInfo",
            //标准检测
            //"/stdglprj/checkmoudle/contrastbyModel",
            //标准检测  单独给凯哥用的
            //"/stdglprj/checkmoudle/contrastbyField",
            //检测安全字段
            //"/stdglprj/checkmoudle/contrastSafeField",
            //根据码表英文名获取码表
            //"/stdglprj/codeinfo/getCodeInfoByName",
            //获取所有统计信息
            //"/stdglprj/statist/getAllStatist",
            //返回所有统计信息
            //"/stdglprj/statist/getBaseStatist"
            //获取模型列表 的目录信息
            //"/stdglprj/model/getAllModelCategory"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        List<String> allowUrlList = Arrays.asList(allowurl);
        String url = request.getRequestURI();
        logger.info("请求的url："+url);
        if(!StringUtils.isEmpty(url)){
            for (String tmpurl : allowurl) {
                if(url.startsWith(tmpurl)){
                    return true;
                }
            }
        }

        boolean result = false;
        AbstractInterceptorStrategy ais = null;
        if("pro".equals(tokenAspectType)){
            //saisi
            ais = new SaiSiInterceptor(tokenAspectType,request);
        }else if("ukey".equals(tokenAspectType)){
            //ukey
            ais = new UkeyInterceptor(tokenAspectType,request);
        }else{
            //本地测试
            ais = new DevInterceptor(tokenAspectType,request);
        }
        result = ais.interceptorFunction();
        return result;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        UserContextUtil.removeUser();
        UserContextUtil.removeToken();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

}
