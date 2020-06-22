package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "oper_tb_stdgl_code_info")
@Entity
@DynamicInsert
@DynamicUpdate
public class OperCodeInfo {


	@Column(name = "batch_no", nullable = true, length = 2)
	private String batchNo="";

	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id=""; //id
	
	@Column(name = "codetable_cname", nullable = true, length = 80)
	private String codetableCname=""; //码表中文名
	
	@Column(name = "codetable_ename", nullable = true, length = 80)
	private String codetableEname=""; //码表英文名
	
	@Column(name = "code_cname", nullable = true, length = 80)
	private String codeCname=""; //码值中文名
	
	@Column(name = "code_ename", nullable = true, length = 80)
	private String codeEname=""; //码值英文名
	
	@Column(name = "code_value", nullable = true, length = 10)
	private String codeValue=""; //代码值

	@Column(name = "remark", nullable = true, length = 2048)
	private String remark=""; //备注
	
	@Column(name = "auth_remark", nullable = true, length = 1024)
	private String authRemark="";//授权类型
	
	@Column(name = "create_user", nullable = true, length = 16)
	private String createUser="";//审核状态

	@Column(name = "create_time", nullable = true, length = 32)
	private Date createTime;
	
	@Column(name = "file_id", nullable = true, length = 8)
	private String fileId="";

	@Column(name = "status", nullable = true, length = 2)
	private String status="";

	@Column(name = "update_user", nullable = true, length = 2)
	private String updateUser="";

	@Column(name = "update_time", nullable = true, length = 2)
	private Date updateTime;

	@Column(name = "data_type", nullable = true, length = 2)
	private String dataType="";
	@Column(name = "is_auth", nullable = true, length = 10)
	private String isAuth="";
	@Column(name = "audit_user", nullable = true, length = 10)
	private String auditUser="";
	@Column(name = "audit_time", nullable = true, length = 10)
	private Date auditTime;
	@Column(name = "db_id", nullable = false, length = 32)
	private String db_id="";

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodetableCname() {
		return codetableCname;
	}

	public void setCodetableCname(String codetableCname) {
		this.codetableCname = codetableCname;
	}

	public String getCodetableEname() {
		return codetableEname;
	}

	public void setCodetableEname(String codetableEname) {
		this.codetableEname = codetableEname;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getDb_id() {
		return db_id;
	}

	public void setDb_id(String db_id) {
		this.db_id = db_id;
	}
}
