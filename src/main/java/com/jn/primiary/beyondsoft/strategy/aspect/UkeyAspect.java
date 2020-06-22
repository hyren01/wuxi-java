package com.jn.primiary.beyondsoft.strategy.aspect;

import com.jn.primiary.beyondsoft.entity.CommonException;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.beyondsoft.util.UkeyTokenUtil;
import com.jn.primiary.beyondsoft.util.UserContextUtil;
import com.jn.primiary.metadata.utils.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ukey  token验证
 */
public class UkeyAspect extends AbstracyAspectStrategy {
    private Logger logger = Logger.getLogger(UkeyAspect.class);
    @Autowired
    private UkeyTokenUtil ukeyTokenUtil;

    public UkeyAspect(String tokenAspectType, HttpServletRequest request, ProceedingJoinPoint point, HttpSession session) {
        super(tokenAspectType, request,point,session);
    }

    @Override
    public Object aspectFunction() throws Throwable {
        logger.info("Ukey    tokenAspect");
        String Token = request.getParameter("token");
        String AppId = ukeyTokenUtil.getAppId();
        logger.info("------------------------appid："+AppId);
        logger.info("------------------------token："+Token);

        if (StringUtils.isEmpty(AppId)) throw new CommonException("请传入AppId");
        if (StringUtils.isEmpty(Token)) throw new CommonException("请传入token");

        SysUser tmp_user = ukeyTokenUtil.validateLoginByCas(AppId,Token,request);
        logger.info("------------------------tmp_user："+tmp_user.toString());

        UserContextUtil.setUser(tmp_user);
        Object result = point.proceed();
        UserContextUtil.removeUser();

        return result;
    }








}
