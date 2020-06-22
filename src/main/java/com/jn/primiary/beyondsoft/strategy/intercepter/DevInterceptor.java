package com.jn.primiary.beyondsoft.strategy.intercepter;

import com.jn.primiary.beyondsoft.aspect.JwtTokenUtil;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.beyondsoft.service.UserService;
import com.jn.primiary.beyondsoft.strategy.aspect.AbstracyAspectStrategy;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 本次测试  token验证
 */
public class DevInterceptor extends AbstractInterceptorStrategy {
    private Logger logger = Logger.getLogger(DevInterceptor.class);
    private String KEYWORD_TOKEN = "scistor-token";
    public DevInterceptor(String tokenAspectType, HttpServletRequest request) {
        super(tokenAspectType, request);
    }

    @Override
    public boolean interceptorFunction() throws Exception {
        logger.info("本地测试tokenAspect");
        String token = request.getHeader(KEYWORD_TOKEN);

        SysUser user = new SysUser();
        user.setUsername("2001");
        user.setPassword("111111");
        user.setId("2");

        UserContextUtil.setUser(user);
        UserContextUtil.setSaiSiToken(token);
        return true;
    }
}
