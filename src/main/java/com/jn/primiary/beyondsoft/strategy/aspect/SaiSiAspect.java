package com.jn.primiary.beyondsoft.strategy.aspect;

import com.jn.primiary.beyondsoft.aspect.JwtTokenUtil;
import com.jn.primiary.beyondsoft.aspect.TokenInfo;
import com.jn.primiary.beyondsoft.entity.CommonException;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.commons.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

    public class SaiSiAspect extends AbstracyAspectStrategy {
        private Logger logger = Logger.getLogger(SaiSiAspect.class);

        public SaiSiAspect(String tokenAspectType, HttpServletRequest request, ProceedingJoinPoint point, HttpSession session) {
            super(tokenAspectType, request,point,session);
        }
        public SaiSiAspect(String tokenAspectType, HttpServletRequest request) {
            super(tokenAspectType, request);
        }


    /* 定义一个线程域，存放登录的对象 */
    private static final ThreadLocal<SysUser> t1 = new ThreadLocal<>();
    private String KEYWORD_TOKEN = "scistor-token";

    @Override
    public Object aspectFunction() throws Throwable {
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

        //放入线程域中
        t1.set(user);
        //程序执行
        proceed = point.proceed();
        //程序完成后，从线程域中删除用户信息
        t1.remove();
        return proceed;
    }





    }
