package com.jn.primiary.beyondsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;

@Table(name = "oper_tb_stdgl_code_info")
@Entity
@DynamicInsert
@DynamicUpdate
public class OperCodeInfo2 {
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id=""; //id

    @Column(name = "batch_no", nullable = true, length = 2)
    private String batchNo="";

    @Column(name = "codetable_cname", nullable = true, length = 80)
    private String name=""; //码表中文名

    @Column(name = "codetable_ename", nullable = true, length = 80)
    private String enName=""; //码表英文名

    @Column(name = "remark", nullable = true, length = 2048)
    private String code=""; //备注

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = true, length = 32)
    private String createTime;

    @Column(name = "create_user", nullable = true, length = 16)
    private String createPerson="";//创建人

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time", nullable = true, length = 2)
    private String updateTime;

    @Column(name = "update_user", nullable = true, length = 2)
    private String updatePerson="";

    @Column(name = "code_cname", nullable = true, length = 80)
    private String codeCname=""; //码值中文名

    @Column(name = "code_ename", nullable = true, length = 80)
    private String codeEname=""; //码值英文名

    @Column(name = "code_value", nullable = true, length = 10)
    private String codeValue=""; //代码值

    @Column(name = "auth_remark", nullable = true, length = 1024)
    private String authRemark="";//授权类型

    @Column(name = "file_id", nullable = true, length = 8)
    private String fileId="";

    @Column(name = "status", nullable = true, length = 2)
    private String status="";

    @Column(name = "data_type", nullable = true, length = 2)
    private String dataType="";

    @Column(name = "is_auth", nullable = true, length = 10)
    private String isAuth="";

    @Column(name = "audit_user", nullable = true, length = 10)
    private String auditUser="";

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "audit_time", nullable = true, length = 10)
    private String auditTime;

    @Column(name = "db_id", nullable = false, length = 32)
    private String db_id="";

    @Transient
    private List<OperStandardField> standardField;

    @Transient
    private Dbinfo2 dbinfo2;

    public List<OperStandardField> getStandardField() {
        return standardField;
    }

    public void setStandardField(List<OperStandardField> standardField) {
        this.standardField = standardField;
    }

    public Dbinfo2 getDbinfo2() {
        return dbinfo2;
    }

    public void setDbinfo2(Dbinfo2 dbinfo2) {
        this.dbinfo2 = dbinfo2;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCodeCname() {
        return codeCname;
    }

    public void setCodeCname(String codeCname) {
        this.codeCname = codeCname;
    }

    public String getCodeEname() {
        return codeEname;
    }

    public void setCodeEname(String codeEname) {
        this.codeEname = codeEname;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
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

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }
}
