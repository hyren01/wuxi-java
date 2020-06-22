/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;


public class CheckModel {
	

	private String id = "";// 唯一标识
	
	private String code = "";// 模型对象编码
	
	private String enName = ""; // 英文名称
	
	private String name = "";// 模型对象中文名称
	

	private Integer type = 0;	// 模型类型，分为普通模型/组合模型

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createTime;// 创建时间
	
	@Column(name = "create_person", nullable = true, length = 128)
	private String createPerson = "";// 创建人
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date updateTime;// 更新时间
	
	private String updatePerson = "";// 更新人

	private String description = "";// 模型描述 // 改为和数据库一致description，原来用describe
	
	private String version = "";// 版本信息
	
	private String registerStatus = ""; // 注册状态 未注册/正在注册/已注册 // add by gongyf 2017.11.07


	private String categoryId = "";	// 挂载的目录ID

	private String bmid; // 编目ID


	private String fullbmid;// 全编目ID
	

	private String borned = "未落地";		// 是否落地	未落地/已落地
	

	private Integer bornCount = 0;		// 是否落地	未落地/已落地
	
	private String extention = "";		// 扩展信息，可以用于注册模型时带入挂载的表	


	private List<CheckModelField> fields = new ArrayList<CheckModelField>();// 字段
	
	
	private boolean isChecked;//是否已经核标
	
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
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

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public String getBorned() {
		return borned;
	}

	public void setBorned(String borned) {
		this.borned = borned;
	}

	public Integer getBornCount() {
		return bornCount;
	}

	public void setBornCount(Integer bornCount) {
		this.bornCount = bornCount;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public List<CheckModelField> getFields() {
		return fields;
	}

	public void setFields(List<CheckModelField> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", code=" + code + ", enName=" + enName + ", name=" + name + ", type=" + type
				+ ", createTime=" + createTime + ", createPerson=" + createPerson + ", updateTime=" + updateTime
				+ ", updatePerson=" + updatePerson + ", description=" + description + ", version=" + version
				+ ", registerStatus=" + registerStatus + ", categoryId=" + categoryId + ", fields=" + fields + ", bmid="
				+ bmid + ", fullbmid=" + fullbmid + ", borned=" + borned + ", bornCount=" + bornCount + "]";
	}
	
	

}