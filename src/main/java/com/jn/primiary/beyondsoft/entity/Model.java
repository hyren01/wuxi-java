/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.beyondsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "tb_meta_model")
@Entity
public class Model {
	
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id = "";// 唯一标识
	
	@Column(name = "code", nullable = false, length = 128)
	private String code = "";// 模型对象编码
	
	@Column(name = "en_name", nullable = true, length = 128)
	private String enName = ""; // 英文名称
	
	@Column(name = "name", nullable = true, length = 128)
	private String name = "";// 模型对象中文名称
	
	@Column(name = "type", nullable = true, length = 4)
	private Integer type = 0;	// 模型类型，分为普通模型/组合模型

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "create_time", nullable = true)
	private Date createTime;// 创建时间
	
	@Column(name = "create_person", nullable = true, length = 128)
	private String createPerson = "";// 创建人
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "update_time", nullable = true)
	private Date updateTime;// 更新时间
	
	@Column(name = "update_person", nullable = true, length = 128)
	private String updatePerson = "";// 更新人
	
	@Column(name = "description", nullable = true, length = 2048)
	private String description = "";// 模型描述 // 改为和数据库一致description，原来用describe
	
	@Column(name = "version", nullable = true, length = 32)
	private String version = "";// 版本信息
	
	@Column(name = "register_status", nullable = true, length = 32)
	private String registerStatus = ""; // 注册状态 未注册/正在注册/已注册 // add by gongyf 2017.11.07

	//@JsonIgnore
	@Column(name = "category_id", nullable = true, length = 32)
	private String categoryId = "";	// 挂载的目录ID

	@Column(name = "bmID", nullable = true, length = 128)
	private String bmid; // 编目ID

	@Column(name = "fullbmID", nullable = true, length = 512)
	private String fullbmid;// 全编目ID
	
	@Column(name = "borned", nullable = false, length = 32)
	private String borned = "未落地";		// 是否落地	未落地/已落地
	
	@Column(name = "born_count", nullable = false, length = 11)
	private Integer bornCount = 0;		// 是否落地	未落地/已落地
	
	@Column(name = "extention", nullable = true, length = 11)
	private String extention = "";		// 扩展信息，可以用于注册模型时带入挂载的表	

	@Transient	// 非字段属性，必须添加该注解
	private List<ModelField> fields = new ArrayList<ModelField>();// 字段
	
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

	public List<ModelField> getFields() {
		return fields;
	}

	public void setFields(List<ModelField> fields) {
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