package com.jn.primiary.beyondsoft.strategy.intercepter;


import javax.servlet.http.HttpServletRequest;

public abstract class AbstractInterceptorStrategy {
    public HttpServletRequest request;
    private String tokenAspectType;

    public AbstractInterceptorStrategy(String tokenAspectType, HttpServletRequest request) {
        this.tokenAspectType = tokenAspectType;
        this.request = request;
    }

    public abstract boolean interceptorFunction() throws Exception;

}
