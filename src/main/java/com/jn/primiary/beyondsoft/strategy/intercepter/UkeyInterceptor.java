package com.jn.primiary.beyondsoft.strategy.intercepter;

import com.jn.primiary.beyondsoft.entity.CommonException;
import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.beyondsoft.strategy.aspect.AbstracyAspectStrategy;
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
public class UkeyInterceptor extends AbstractInterceptorStrategy {
    private Logger logger = Logger.getLogger(UkeyInterceptor.class);
    @Autowired
    private UkeyTokenUtil ukeyTokenUtil;

    public UkeyInterceptor(String tokenAspectType, HttpServletRequest request) {
        super(tokenAspectType, request);
    }

    @Override
    public boolean interceptorFunction() throws Exception {
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
        return true;
    }
}
