package com.jn.primiary.beyondsoft.entity.yunwei;


import com.alibaba.fastjson.JSONArray;

//import java.lang.reflect.JSONArray;
import java.util.Date;

public class YwData {
    //样例数据 以文档为准
    private int logType;
    private String startTime;
    private String endTime;
    private String subjectName;
    private String subjectID;
    private String subjectAddress;
    private String operation;
    private String subSystem;
    private String module;
    private String taskCode;
    private String taskName;
    private String objectType;
    private String condition;
    private JSONArray objectInfo;
    private JSONArray destAddress;
    private String outcome;
    private String message;
    private Date insertTime;

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectAddress() {
        return subjectAddress;
    }

    public void setSubjectAddress(String subjectAddress) {
        this.subjectAddress = subjectAddress;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public JSONArray getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(JSONArray objectInfo) {
        this.objectInfo = objectInfo;
    }

    public JSONArray getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(JSONArray destAddress) {
        this.destAddress = destAddress;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}

