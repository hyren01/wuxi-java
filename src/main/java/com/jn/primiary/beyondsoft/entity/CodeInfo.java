package com.jn.primiary.beyondsoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "tb_stdgl_code_info")
@Entity
@DynamicInsert
@DynamicUpdate
public class CodeInfo {
	
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id;
	
	@Column(name = "codetable_cname", nullable = true, length = 80)
	private String codetableCname;
	
	@Column(name = "codetable_ename", nullable = true, length = 80)
	private String codetableEname;
	
	@Column(name = "code_cname", nullable = true, length = 80)
	private String codeCname;
	
	@Column(name = "code_ename", nullable = true, length = 80)
	private String codeEname;
	
	@Column(name = "code_value", nullable = true, length = 10)
	private String codeValue;

	@Column(name = "remark", nullable = true, length = 2048)
	private String remark;
	
	@Column(name = "auth_remark", nullable = true, length = 1024)
	private String authRemark;//授权类型
	
	@Column(name = "create_user", nullable = true, length = 16)
	private String createUser;//审核状态
	
	@Column(name = "create_time", nullable = true, length = 32)
	private String createTime;

	@Column(name = "update_user", nullable = true, length = 2)
	private String updateUser;

	@Column(name = "update_time", nullable = true, length = 2)
	private String updateTime;
	
	@Column(name = "file_id", nullable = true, length = 8)
	private String fileId;

	@Column(name = "status", nullable = true, length = 2)
	private String status;

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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
}
