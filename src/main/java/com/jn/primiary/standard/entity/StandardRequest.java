/***********************************************************************
 * Module:  RegisterRequest.java
 * Author:  gongyf
 * Purpose: 注册请求对象
 ***********************************************************************/

package com.jn.primiary.standard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "tb_stdgl_request")
@Entity
public class StandardRequest {
	
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id = "";// 唯一标识
	
	@Column(name = "type", nullable = false, length = 32)
	private String type = "";	// 请求类型，分为新增/修改/删除/补录/注销
	
	@Column(name = "fun_type", nullable = false, length = 16)
	private String funType;
	
	@Column(name = "resource_id", nullable = false, length = 32)
	private String resourceId = ""; // 资源ID
	
	@Column(name = "resource_content", nullable = true, columnDefinition="TEXT")
	private String resourceContent = "";// 资源内容，如果新增，没有资源ID，可以将内容填在这里，有资源ID的，内容通过id从数据库获取
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "request_time", nullable = true)
	private Date requestTime;// 申请时间
	
	@Column(name = "request_person", nullable = true, length = 128)
	private String requestPerson = "";// 申请人
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "audit_time", nullable = true)
	private Date auditTime;// 审核时间
	
	@Column(name = "audit_person", nullable = true, length = 128)
	private String auditPerson = "";// 审核人
	
	@Column(name = "audit_result", nullable = true, length = 128)
	private String auditResult = "";// 审核结果	通过/拒绝
	
	@Column(name = "audit_message", nullable = true, columnDefinition="TEXT")
	private String auditMessage = "";// 审核原因
	
	@Column(name = "resource_code", nullable = true, length = 256)
    private String code;//	标准code
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFunType() {
		return funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceContent() {
		return resourceContent;
	}

	public void setResourceContent(String resourceContent) {
		this.resourceContent = resourceContent;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestPerson() {
		return requestPerson;
	}

	public void setRequestPerson(String requestPerson) {
		this.requestPerson = requestPerson;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getAuditMessage() {
		return auditMessage;
	}

	public void setAuditMessage(String auditMessage) {
		this.auditMessage = auditMessage;
	}

	@Override
	public String toString() {
		return "RegisterRequest [id=" + id + ", type=" + type + ", resourceType="  + ", resourceId="
				+ resourceId + ", resource_content=" + resourceContent + ", requestTime=" + requestTime
				+ ", requestPerson=" + requestPerson + ", auditTime=" + auditTime + ", auditPerson=" + auditPerson
				+ ", auditResult=" + auditResult + ", auditMessage=" + auditMessage + "]";
	}


}