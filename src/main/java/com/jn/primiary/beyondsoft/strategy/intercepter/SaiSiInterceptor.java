package com.jn.primiary.beyondsoft.strategy.intercepter;

import com.jn.primiary.beyondsoft.aspect.JwtTokenUtil;
import com.jn.primiary.beyondsoft.aspect.TokenInfo;
import com.jn.primiary.beyondsoft.entity.CommonException;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.beyondsoft.strategy.aspect.AbstracyAspectStrategy;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.commons.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SaiSiInterceptor extends AbstractInterceptorStrategy {
    private Logger logger = Logger.getLogger(SaiSiInterceptor.class);
    private String KEYWORD_TOKEN = "scistor-token";
    //private String KEYWORD_TOKEN = "token";

    public SaiSiInterceptor(String tokenAspectType, HttpServletRequest request) {
        super(tokenAspectType, request);
    }

    @Override
    public boolean interceptorFunction() throws Exception {
        //连接赛斯
        //获得token 判断是否有token
        Object proceed= null;
        logger.info("进入赛斯token拦截器了");
        String token = request.getHeader(KEYWORD_TOKEN);
        logger.info("token="+token);
        if (StringUtils.isBlank(token)) throw new CommonException("未登录");

        //用户已登录，获取用户信息
        TokenInfo tokenInfo = JwtTokenUtil.getTokenInfo(token);
        logger.info("tokenInfo="+tokenInfo.toString());
        if(tokenInfo == null) throw new CommonException("无效的token");

        SysUser user = new SysUser();
        user.setId(tokenInfo.getId());
        user.setUsername(tokenInfo.getUserName());
        user.setPassword(tokenInfo.getUserAccount());

        UserContextUtil.setUser(user);
        UserContextUtil.setSaiSiToken(token);
        return true;
    }









}
