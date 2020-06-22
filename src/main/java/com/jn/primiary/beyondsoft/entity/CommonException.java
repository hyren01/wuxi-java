package com.jn.primiary.beyondsoft.entity;

import com.jn.primiary.metadata.entity.ResultCode;

public class CommonException extends Exception {
    private ResultCode resultCode;

    public CommonException(String message){
        super(message);
    }

    public CommonException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode(){
        return resultCode;
    }

}
