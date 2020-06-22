/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.office.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "tb_stdgl_schemamodule")
@Entity
public class DocStdModel {
	
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id = "";// 唯一标识

	@Column(name = "batch_no", nullable = false, length = 32)
	private String batch_no = "";// 操作id

	@Column(name = "version", nullable = true, length = 32)
	private String version = "";// 版本信息

	@Column(name = "schemacode", nullable = false, length = 128)
	private String code = "";// 模型对象编码
	
	@Column(name = "enname", nullable = true, length = 100)
	private String enName = ""; // 英文名称
	
	@Column(name = "schemaname", nullable = true, length = 100)
	private String cName = "";// 中文名称
	
	@Column(name = "type", nullable = true, length = 4)
	private Integer type = 0;	// 模型类型
	
	@Column(name = "creator", nullable = true, length = 128)
	private String creator = "";// 创建人

	@Column(name = "datasource", nullable = true, length = 20)
	private String datasource = "";// 数据源

	@Column(name = "description", nullable = true, length = 2048)
	private String description = "";// 模型描述 // 改为和数据库一致description，原来用describe

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "createtime", nullable = true)
	private Date createTime;// 创建时间

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "update_time", nullable = true)
	private Date updateTime;// 修改时间

	@Column(name = "update_person", nullable = true, length = 128)
	private String update_person = "";// 修改人

	@Column(name = "categoryid", nullable = true, length = 32)
	private String categoryId = "";	// 挂载的目录ID

	@Column(name = "auth_remark", nullable = true, length = 2048)
	private String auth_remark = "";	//审核意见

	@Column(name = "status", nullable = true, length = 2)
	private String status = "";	//状态

	@Column(name = "fileid", nullable = true, length = 32)
	private String fileid = "";	// 文档ID

	@Column(name = "db_id", nullable = true, length = 32)
	private String db_id = "";	// 数据库ID

	@Transient	// 非字段属性，必须添加该注解
	//@OneToMany(mappedBy="stdmodel",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<DocStdModelField> fields = new ArrayList<DocStdModelField>();// 字段

	@Transient
	private List<DocStdModelObj> objfields = new ArrayList<DocStdModelObj>();// 对象类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdate_person() {
		return update_person;
	}

	public void setUpdate_person(String update_person) {
		this.update_person = update_person;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getAuth_remark() {
		return auth_remark;
	}

	public void setAuth_remark(String auth_remark) {
		this.auth_remark = auth_remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getDb_id() {
		return db_id;
	}

	public void setDb_id(String db_id) {
		this.db_id = db_id;
	}

	public List<DocStdModelField> getFields() {
		return fields;
	}

	public void setFields(List<DocStdModelField> fields) {
		this.fields = fields;
	}

	public List<DocStdModelObj> getObjfields() {
		return objfields;
	}

	public void setObjfields(List<DocStdModelObj> objfields) {
		this.objfields = objfields;
	}
}