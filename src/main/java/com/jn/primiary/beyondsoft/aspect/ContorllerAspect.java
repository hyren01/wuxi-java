package com.jn.primiary.beyondsoft.aspect;

import com.jn.primiary.beyondsoft.strategy.aspect.AbstracyAspectStrategy;
import com.jn.primiary.beyondsoft.strategy.aspect.DevAspect;
import com.jn.primiary.beyondsoft.strategy.aspect.SaiSiAspect;
import com.jn.primiary.beyondsoft.strategy.aspect.UkeyAspect;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@Aspect
//@Component
public class ContorllerAspect {
    private Logger logger = Logger.getLogger(ContorllerAspect.class);

    @Value("${spring.profiles.active}")
    private String tokenAspectType;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired(required = false)
    private HttpSession session;

    @Pointcut("execution(* com.jn.primiary.beyondsoft.controller.*.*(..))")
    public void beyondsoftController(){}

    @Pointcut("execution(* com.jn.primiary.office.controller.*.*(..))")
    public void FileUploadController(){}



    //设置白名单，有这个注解的就不拦截
    @Pointcut("@annotation(com.jn.primiary.beyondsoft.aspect.LoginWhitePathAnnotation)")
    public void cutAnnotation(){}

    @Around("((beyondsoftController() || FileUploadController()) && !cutAnnotation())")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        AbstracyAspectStrategy aas = null;
        if("pro".equals(tokenAspectType)){
            aas = new SaiSiAspect(tokenAspectType,request,point,session);
        }else if("ukey".equals(tokenAspectType)){
            //ukey
            aas = new UkeyAspect(tokenAspectType,request,point,session);
        }else{
            //本地测试
            aas = new DevAspect(tokenAspectType,request,point,session);
        }
        result = aas.aspectFunction();

        return result;

    }



}
