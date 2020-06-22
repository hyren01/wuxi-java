package com.jn.primiary.metadata.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_authority")
@Entity
public class SysPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "authority_id", nullable = false, length = 11)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long authorityId; // 权限id.

	@Column(name = "authority_name", nullable = false, length = 100)
	private String authorityName; // 权限名称.

	@Column(name = "authority_sign", length = 256)
	private String authoritySign; // 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	
	@Column(name = "creator", length = 50)
	private String creator;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "remark", length = 140)
	private String remark;
	
	@Column(name = "delete_flag", length = 1)
	private int deleteFlag = 0;
	
	@Column(name = "system_id", length = 1)
	private int systemId = 0;
	
	public SysPermission() {
		createTime = Calendar.getInstance().getTime();
	}

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthoritySign() {
		return authoritySign;
	}

	public void setAuthoritySign(String authoritySign) {
		this.authoritySign = authoritySign;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getSystemId() {
		return systemId;
	}

	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "SysPermission [authorityId=" + authorityId + ", authorityName=" + authorityName + ", authoritySign="
				+ authoritySign + ", creator=" + creator + ", createTime=" + createTime + ", remark=" + remark
				+ ", deleteFlag=" + deleteFlag + ", systemId=" + systemId + "]";
	}

	
}