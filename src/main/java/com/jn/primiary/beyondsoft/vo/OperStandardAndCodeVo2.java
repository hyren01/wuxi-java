package com.jn.primiary.beyondsoft.vo;


public class OperStandardAndCodeVo2 {

    public String id;//ID
    public String batchNo;
    public String code;//	短名
    public String name;//	标准中文名称
    public String enName;//	标准英文名称
    public String createPerson;//	创建人
    public String createTime;//	创建时间
    public String isAuth;
    public String dataType;
    public String categoryId;//挂载目录ID
    public String db_id;
    public String version;//	版本


    public OperStandardAndCodeVo2(String id, String batchNo, String code, String name, String enName, String createPerson, String createTime, String isAuth, String dataType, String categoryId, String db_id, String version) {
        this.id = id;
        this.batchNo = batchNo;
        this.code = code;
        this.name = name;
        this.enName = enName;
        this.createPerson = createPerson;
        this.createTime = createTime;
        this.isAuth = isAuth;
        this.dataType = dataType;
        this.categoryId = categoryId;
        this.db_id = db_id;
        this.version = version;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
