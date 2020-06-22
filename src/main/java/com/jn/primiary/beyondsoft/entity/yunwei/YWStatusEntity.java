package com.jn.primiary.beyondsoft.entity.yunwei;

/**
 * 状态上报  实体
 */
public class YWStatusEntity {
    //可用状态的编码，使用固定值：object.available
    private String state;
    //资源的类型编码
    private String classCode;
    //作为关键属性
    private String useID;
    //采样时间点(非必填)，如果不提供则为服务端当前时间
    private String time;
    //状态值，可用状态的value有：unknown、off、on。分别表示：未知、不可用、可用
    private String value;
    //状态变化描述(非必填)
    private String descr;

    public YWStatusEntity() {
    }

    public YWStatusEntity(String state, String classCode, String useID, String time, String value, String descr) {
        this.state = state;
        this.classCode = classCode;
        this.useID = useID;
        this.time = time;
        this.value = value;
        this.descr = descr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getUseID() {
        return useID;
    }

    public void setUseID(String useID) {
        this.useID = useID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
