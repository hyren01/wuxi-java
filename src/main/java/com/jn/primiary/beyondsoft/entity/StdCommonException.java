package com.jn.primiary.beyondsoft.entity;

import com.jn.primiary.metadata.entity.BaseResponse;
import com.jn.primiary.metadata.entity.ResultCode;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class StdCommonException {
    private Logger logger = Logger.getLogger(StdCommonException.class);

    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public BaseResponse commonExceptionHandler(CommonException e){
        logger.error("发生异常，原因:"+e.getMessage(),e);
        BaseResponse response = new BaseResponse();
        response.setMessage(e.getMessage());
        response.setResultCode(ResultCode.RESULT_ERROR);
        return response;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(Exception e){
        logger.error("未知异常，报错:"+e.getMessage(),e);
        BaseResponse response = new BaseResponse();
        response.setMessage(e.getMessage());
        response.setResultCode(ResultCode.RESULT_ERROR);
        return response;
    }


}
