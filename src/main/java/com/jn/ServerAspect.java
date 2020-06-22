package com.jn;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Des 服务切面，与工程主业务逻辑分离，记录监控日志
 * @Author chenhong
 * @Date 2020/1/8 10:30
 */
//@Aspect
//@Component
public class ServerAspect {

    private final Logger logger = LoggerFactory.getLogger(ServerAspect.class);
    @Around("execution(public * com.jn.primiary.beyondsoft.controller..*.*(..))")
    public Object controllerLog(ProceedingJoinPoint pdj) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        long startTime = System.currentTimeMillis();//开始时间

        Object[] args = pdj.getArgs();//获取参数数组
        Object ret = pdj.proceed(args);//执行原方法并获取返回结果

        long endTime = System.currentTimeMillis();//结束时间

        // 记录下请求内容
        StringBuilder sb = new StringBuilder();
        sb.append("###请求URL: " + request.getRequestURL().toString());
        sb.append("   ###IP: " + request.getRemoteAddr());
        sb.append("   ###Params: " + Arrays.toString(args));
        sb.append("   ###CLASS_METHOD: " + pdj.getSignature().getDeclaringTypeName() + "." + pdj.getSignature().getName());
        sb.append("   ###耗时: " + (endTime - startTime) + "毫秒");

        logger.info(sb.toString());
        return ret;
    }

}