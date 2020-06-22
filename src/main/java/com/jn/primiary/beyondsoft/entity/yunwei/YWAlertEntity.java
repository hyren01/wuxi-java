package com.jn.primiary.beyondsoft.entity.yunwei;

import com.alibaba.fastjson.JSONArray;

public class YWAlertEntity {
    //(非必填) 告警级别: Critical 3, Error 2, Warning 1, OK 0
    private String severity;
    //告警名称
    private String name;
    //告警描述
    private String description;
    //(非必填) 发生时间，整形数值
    private String occur_time;
    //告警对象（配置项的名称，例如告警的软件或者数据库的名称）
    private String entity_name;
    //(非必填) 告警对象ip地址
    private String entity_addr;
    //(非必填) 指定用于告警合并的字段，如果有多个字段请用逗号隔开。可选范围：entity_name,entity_addr,app_key,name,properties中的字段的code，code代表字段名称。
    private String merge_key;
    //用于定位统一资源库的资源，值可以选择二者中任意一个：（1）“classCode,useID”（2）“entity_name,entity_addr”
    private String identify_key;
    //告警的扩展字段，请使用 name, code, val的形式，其中name和code取值相同。如果identify_key选择“classCode,useID”值，则必须提供classCode和useID两个扩展字段，其他扩展字段用于丰富告警信息内容。
    private JSONArray properties;

    public YWAlertEntity() {
    }

    public YWAlertEntity(String severity, String name, String description, String occur_time, String entity_name, String entity_addr, String merge_key, String identify_key, JSONArray properties) {
        this.severity = severity;
        this.name = name;
        this.description = description;
        this.occur_time = occur_time;
        this.entity_name = entity_name;
        this.entity_addr = entity_addr;
        this.merge_key = merge_key;
        this.identify_key = identify_key;
        this.properties = properties;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccur_time() {
        return occur_time;
    }

    public void setOccur_time(String occur_time) {
        this.occur_time = occur_time;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getEntity_addr() {
        return entity_addr;
    }

    public void setEntity_addr(String entity_addr) {
        this.entity_addr = entity_addr;
    }

    public String getMerge_key() {
        return merge_key;
    }

    public void setMerge_key(String merge_key) {
        this.merge_key = merge_key;
    }

    public String getIdentify_key() {
        return identify_key;
    }

    public void setIdentify_key(String identify_key) {
        this.identify_key = identify_key;
    }

    public JSONArray getProperties() {
        return properties;
    }

    public void setProperties(JSONArray properties) {
        this.properties = properties;
    }
}
