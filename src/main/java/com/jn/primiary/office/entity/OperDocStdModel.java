/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.office.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "oper_tb_stdgl_schemamodule")
@Entity
public class OperDocStdModel {

	@Column(name = "batch_no", nullable = false, length = 10)
	private String batch_no = "";// 唯一标识	操作编号

	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String std_id = "";// 标准id
	
	@Column(name = "schemacode", nullable = false, length = 50)
	private String code = "";// 模型对象编码
	
	@Column(name = "enname", nullable = true, length = 100)
	private String ename = ""; // 英文名称
	
	@Column(name = "schemaname", nullable = true, length = 100)
	private String cname = "";// 中文名称

	@Column(name = "description", nullable = true, length = 2048)
	private String definded = "";

	@Column(name = "datasource", nullable = true, length = 20)
	private String datasource = "";

	@Column(name = "version", nullable = true, length = 32)
	private String version = "";

	@Column(name = "type", nullable = true)
	private int obj_type = 0;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "createtime", nullable = true)
	private Date create_time;// 创建时间

	@Column(name = "creator", nullable = true, length = 50)
	private String creator = "";// 创建人

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "update_time", nullable = true)
	private Date update_time;// 修改时间

	@Column(name = "update_person", nullable = true, length = 50)
	private String update_person = "";// 修改人

	@Column(name = "is_auth", nullable = true)
	private int auth_status;// 审核状态

	@Column(name = "auth_remark", nullable = true, length = 1024)
	private String auth_remark = "";// 审核意见

	@Column(name = "categoryid", nullable = true, length = 32)
	private String category_id = "";	// 挂载的目录ID

	@Column(name = "file_id", nullable = true, length = 32)
	private String file_id = "";	// 文档ID

	@Column(name = "db_id", nullable = true, length = 32)
	private String db_id = "";	// 数据库ID

	@Column(name = "data_type", nullable = true)
	private int oper_flag ;	// 操作标识

	@Column(name = "status", nullable = true)
	private int status ;// 状态

	@Transient	// 非字段属性，必须添加该注解
	//@OneToMany(mappedBy="stdmodel",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<OperDocStdModelField> fields = new ArrayList<OperDocStdModelField>();// 字段

	@Transient
	private List<OperDocStdModelObj> objfields = new ArrayList<OperDocStdModelObj>();// 对象类型

	@Transient
	private OperDocStdModel tmpstd;

	public OperDocStdModel getTmpstd() {
		return tmpstd;
	}

	public void setTmpstd(OperDocStdModel tmpstd) {
		this.tmpstd = tmpstd;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getStd_id() {
		return std_id;
	}

	public void setStd_id(String std_id) {
		this.std_id = std_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getDefinded() {
		return definded;
	}

	public void setDefinded(String definded) {
		this.definded = definded;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getObj_type() {
		return obj_type;
	}

	public void setObj_type(int obj_type) {
		this.obj_type = obj_type;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_person() {
		return update_person;
	}

	public void setUpdate_person(String update_person) {
		this.update_person = update_person;
	}

	public String getAuth_remark() {
		return auth_remark;
	}

	public void setAuth_remark(String auth_remark) {
		this.auth_remark = auth_remark;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getDb_id() {
		return db_id;
	}

	public void setDb_id(String db_id) {
		this.db_id = db_id;
	}

	public List<OperDocStdModelField> getFields() {
		return fields;
	}

	public void setFields(List<OperDocStdModelField> fields) {
		this.fields = fields;
	}

	public List<OperDocStdModelObj> getObjfields() {
		return objfields;
	}

	public void setObjfields(List<OperDocStdModelObj> objfields) {
		this.objfields = objfields;
	}

	public int getAuth_status() {
		return auth_status;
	}

	public void setAuth_status(int auth_status) {
		this.auth_status = auth_status;
	}

	public int getOper_flag() {
		return oper_flag;
	}

	public void setOper_flag(int oper_flag) {
		this.oper_flag = oper_flag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}