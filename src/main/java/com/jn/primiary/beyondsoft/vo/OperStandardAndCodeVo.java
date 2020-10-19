package com.jn.primiary.beyondsoft.vo;


public class OperStandardAndCodeVo {

    private String id;//ID
    private String batchNo;
    private String code;//	短名
    private String name;//	标准中文名称
    private String enName;//	标准英文名称
    private String createPerson;//	创建人
    private String createTime;//	创建时间
    private String isAuth;
    private String updateTime;//	更新时间
    private String dataType;
    private String categoryId;//挂载目录ID
    private String db_id;
    private String version;//	版本
    private String type;//	41表示该条信息是标准信息
    private String updatePerson;//	更新人
    private String description;//	标准描述s
    //    private String dataSource;//	数据源
    private String status;
    private String authRemark;
    private String fileId;
    private String auditPerson;
    private String auditTime;

    public OperStandardAndCodeVo(String id, String batchNo, String code, String name, String enName, String createPerson, String createTime, String isAuth, String updateTime, String dataType, String categoryId, String db_id, String version) {
        this.id = id;
        this.batchNo = batchNo;
        this.code = code;
        this.name = name;
        this.enName = enName;
        this.createPerson = createPerson;
        this.createTime = createTime;
        this.isAuth = isAuth;
        this.updateTime = updateTime;
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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getAuditPerson() {
        return auditPerson;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }
}
