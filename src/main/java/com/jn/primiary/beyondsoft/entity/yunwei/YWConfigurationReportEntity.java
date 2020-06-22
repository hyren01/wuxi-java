package com.jn.primiary.beyondsoft.entity.yunwei;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 配置上报 实体
 */
public class YWConfigurationReportEntity {

    private String useID;
    private String name;
    private String classCode;
    private String desc;
    private JSONObject belongRelation;

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
}
