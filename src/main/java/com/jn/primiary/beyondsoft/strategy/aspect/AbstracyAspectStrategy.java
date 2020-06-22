package com.jn.primiary.beyondsoft.strategy.aspect;

import com.jn.primiary.beyondsoft.entity.SysUser;
import com.jn.primiary.metadata.entity.BaseResponse;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstracyAspectStrategy {
    public String tokenAspectType;
    public HttpServletRequest request;
    public ProceedingJoinPoint point;
    public HttpSession session;

    public AbstracyAspectStrategy(String tokenAspectType, HttpServletRequest request,ProceedingJoinPoint point,HttpSession session) {
        this.tokenAspectType = tokenAspectType;
        this.request = request;
        this.point = point;
        this.session = session;
    }

    public AbstracyAspectStrategy(String tokenAspectType, HttpServletRequest request) {
        this.tokenAspectType = tokenAspectType;
        this.request = request;
    }

    public abstract Object aspectFunction() throws Throwable;

}
