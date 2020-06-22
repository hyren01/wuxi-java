package com.jn.primiary.beyondsoft.strategy.aspect;

import com.jn.primiary.beyondsoft.aspect.JwtTokenUtil;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.beyondsoft.service.UserService;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 本次测试  token验证
 */
public class DevAspect extends AbstracyAspectStrategy {
    private Logger logger = Logger.getLogger(DevAspect.class);
    private JwtTokenUtil tokenUtil = new JwtTokenUtil();
    //@Autowired
    private UserService service = new UserService();

    public DevAspect(String tokenAspectType, HttpServletRequest request, ProceedingJoinPoint point, HttpSession session) {
        super(tokenAspectType, request,point,session);
    }

    @Override
    public Object aspectFunction() throws Throwable {
        logger.info("本地测试tokenAspect");

        SysUser user = new SysUser();
        user.setUsername("2001");
        user.setPassword("111111");
        user.setId("2");

        UserContextUtil.setUser(user);
        Object result = point.proceed();
        //UserContextUtil.remove();

        return result;
    }

}
