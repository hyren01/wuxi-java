package com.jn.primiary.beyondsoft.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//@Aspect
//@Component
public class LogInterceptor {
    static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    static final String pCutStr = "execution(* com.jn.primiary.beyondsoft.controller.*..*(..))";
    //static final String pCutStr2 = "execution(* com.jn.primiary.office.controller.*..*(..))";

    //定义切点
    @Pointcut(value = pCutStr)
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("请求地址:" + request.getRequestURI()+"-------------执行时间: " + (end - start) + " ms!");
        return result;

    }
}
