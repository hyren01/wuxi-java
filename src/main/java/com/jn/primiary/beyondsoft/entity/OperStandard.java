package com.jn.primiary.beyondsoft.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "oper_tb_stdgl_schemamodule")
@Entity
@DynamicInsert
@DynamicUpdate
public class OperStandard {
    @Id
	@Column(name = "id", nullable = false, length = 32)
    private String id="";//ID

    @Column(name = "batch_no", nullable = false, length = 30)
    private String batchNo="";

    @Column(name = "schemaname", nullable = true, length = 100)
    private String name="";//	标准中文名称

    @Column(name = "enname", nullable = true, length = 100)
    private String enName="";//	标准英文名称

    @Column(name = "schemacode", nullable = false, length = 50)
    private String code="";//	标准编码

	@Column(name = "type", nullable = false, length = 1)
	private Integer type;//	对象类型

	@Column(name = "createtime", nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;//	创建时间

	@Column(name = "creator", nullable = true, length = 50)
    private String createPerson="";//	创建人

    @Column(name = "update_time", nullable = true, length = 50)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;//	更新时间

    @Column(name = "update_person", nullable = true, length = 50)
    private String updatePerson="";//	更新人

    @Column(name = "description", nullable = true, length = 2048)
    private String description="";//	标准描述s

    @Column(name = "version", nullable = true, length = 32)
    private String version="";//	版本

    @Column(name = "datasource", nullable = true, length = 256)
    private String dataSource="";//	数据源

    @Column(name = "categoryid", nullable = false, length = 32)
    private String categoryId="";//挂载目录ID

    @Column(name = "data_type", nullable = false, length = 10)
    private String dataType="";

    @Column(name = "status", nullable = true, length = 2)
    private String status="";

    @Column(name = "is_auth", nullable = false, length = 10)
    private String isAuth="";

    @Column(name = "auth_remark", nullable = true, length = 1024)
    private String authRemark="";

    @Column(name = "file_id", nullable = true, length = 1024)
    private String fileId="";

    @Column(name = "db_id", nullable = false, length = 32)
    private String db_id="";

    @Column(name = "audit_person", nullable = false, length = 32)
    private String auditPerson="";

    @Column(name = "audit_time", nullable = false, length = 32)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;

    @Transient
    private List<OperStandardField> standardField;

    @Transient
    private List<OperStandardObject> standardObject;

    public String getAuditPerson() {
        return auditPerson;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    @Transient
    private Dbinfo2 dbinfo2;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OperStandardField> getStandardField() {
        return standardField;
    }

    public void setStandardField(List<OperStandardField> standardField) {
        this.standardField = standardField;
    }

    public List<OperStandardObject> getStandardObject() {
        return standardObject;
    }

    public void setStandardObject(List<OperStandardObject> standardObject) {
        this.standardObject = standardObject;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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
    public Dbinfo2 getDbinfo2() {
        return dbinfo2;
    }

    public void setDbinfo2(Dbinfo2 dbinfo2) {
        this.dbinfo2 = dbinfo2;
    }
}
