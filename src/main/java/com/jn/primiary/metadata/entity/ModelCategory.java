package com.jn.primiary.metadata.entity;

public class ModelCategory {
    private String id="";
    private String name="";
    private String parentId="";
    private String creator="";
    private String createTime="";
    private String updator="";
    private String updateTime="";
    private String bmid="";
    private String fullbmid="";
    private String pxh="";
    private String allModelCount="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid;
    }

    public String getFullbmid() {
        return fullbmid;
    }

    public void setFullbmid(String fullbmid) {
        this.fullbmid = fullbmid;
    }

    public String getPxh() {
        return pxh;
    }

    public void setPxh(String pxh) {
        this.pxh = pxh;
    }

    public String getAllModelCount() {
        return allModelCount;
    }

    public void setAllModelCount(String allModelCount) {
        this.allModelCount = allModelCount;
    }
}
