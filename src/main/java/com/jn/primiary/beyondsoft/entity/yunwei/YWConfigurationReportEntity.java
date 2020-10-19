package com.jn.primiary.beyondsoft.entity.yunwei;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 配置上报 实体
 */
public class YWConfigurationReportEntity {

    private String useID;
    private String name;
    private String classCode;
    private String desc;
    private JSONObject belongRelation;
    private String useDepartment;
    private String appVersion;
    private long appPublishTime;
    private String operations;
    private List<String> apiServicesIP;
    private JSONArray useRelation;
    private String ip;
    private List<String> apiIP;

    public static class YWConfigurationReportEntityBuilder{
        YWConfigurationReportEntity entity = new YWConfigurationReportEntity();

        public YWConfigurationReportEntityBuilder setClassCode(String classCode){
            entity.setClassCode(classCode);
            return this;
        }
        public YWConfigurationReportEntityBuilder setUseId(String useID){
            entity.setUseID(useID);
            return this;
        }
        public YWConfigurationReportEntityBuilder setName(String name){
            entity.setName(name);
            return this;
        }

        public YWConfigurationReportEntityBuilder setDesc(String desc){
            entity.setDesc(desc);
            return this;
        }

        public YWConfigurationReportEntityBuilder setBelongRelation(JSONObject belongRelation){
            entity.setBelongRelation(belongRelation);
            return this;
        }



        public YWConfigurationReportEntity build(){
            return entity;
        }


    }

    public String getUseID() {
        return useID;
    }

    public void setUseID(String useID) {
        this.useID = useID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public JSONObject getBelongRelation() {
        return belongRelation;
    }

    public void setBelongRelation(JSONObject belongRelation) {
        this.belongRelation = belongRelation;
    }

    public String getUseDepartment() {
        return useDepartment;
    }

    public void setUseDepartment(String useDepartment) {
        this.useDepartment = useDepartment;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public long getAppPublishTime() {
        return appPublishTime;
    }

    public void setAppPublishTime(long appPublishTime) {
        this.appPublishTime = appPublishTime;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public  List<String> getApiServicesIP() {
        return apiServicesIP;
    }

    public void setApiServicesIP( List<String> apiServicesIP) {
        this.apiServicesIP = apiServicesIP;
    }

    public JSONArray getUseRelation() {
        return useRelation;
    }

    public void setUseRelation(JSONArray useRelation) {
        this.useRelation = useRelation;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<String> getApiIP() {
        return apiIP;
    }

    public void setApiIP(List<String> apiIP) {
        this.apiIP = apiIP;
    }
}
