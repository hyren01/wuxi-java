package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 审核列表VO
 * @Author chenhong
 * @Date 2019/9/28 16:46
 */
public class StandardCodeAuditVo {

    private String ename;
    private String cname;
    private String code;
    private String createTime;
    private String createUser;
    private String isAuth;
    private String auditUser;
    private String auditTime;
    private String batchNo;
    private String type;
    private String dataType;
    private String moudleId;
    private String fileId;
    private String db_id;

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getMoudleId() {
        return moudleId;
    }

    public void setMoudleId(String moudleId) {
        this.moudleId = moudleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String craeteTime) {
        this.createTime = craeteTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public StandardCodeAuditVo(String ename, String cname, String code, String createTime, String createUser, String isAuth, String auditUser, String auditTime, String batchNo, String type, String dataType, String moudleId, String fileId,String db_id) {
        this.ename = ename;
        this.cname = cname;
        this.code = code;
        this.createTime = createTime;
        this.createUser = createUser;
        this.isAuth = isAuth;
        this.auditUser = auditUser;
        this.auditTime = auditTime;
        this.batchNo = batchNo;
        this.type = type;
        this.dataType = dataType;
        this.moudleId = moudleId;
        this.fileId = fileId;
        this.db_id = db_id;
    }

    public StandardCodeAuditVo() {
        super();
    }
}

	
