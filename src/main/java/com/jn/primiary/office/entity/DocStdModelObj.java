/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.office.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tb_stdgl_object")
@Entity
public class DocStdModelObj {
	@Id
	@Column(name = "obj_id", nullable = false, length = 32)
	private String id = "";// 唯一标识

	@Column(name = "batch_no", nullable = false, length = 32)
	private String batch_no = "";// 操作id

	@Column(name = "version", nullable = false, length = 10)
	private String version; // 字段编码

	@Column(name = "obj_cname", nullable = true, length = 80)
	private String obj_cname; // 对象中文名称

	@Column(name = "obj_ename", nullable = true, length = 80)
	private String obj_ename; // 对象英文名称

	@Column(name = "code", nullable = true, length = 80)
	private String code; // code短名

	@Column(name = "obj_defined", nullable = true, length = 1024)
	private String obj_defined; // 定义

	@Column(name = "obj_data_type", nullable = true, length = 80)
	private String type; //数据类型

	@Column(name = "id", nullable = true, length = 80)
	private String model_id; //数据类型

	@Transient
	private List<DocStdModelField> fields = new ArrayList<DocStdModelField>();// 字段

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

	public String getObj_cname() {
		return obj_cname;
	}

	public void setObj_cname(String obj_cname) {
		this.obj_cname = obj_cname;
	}

	public String getObj_ename() {
		return obj_ename;
	}

	public void setObj_ename(String obj_ename) {
		this.obj_ename = obj_ename;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getObj_defined() {
		return obj_defined;
	}

	public void setObj_defined(String obj_defined) {
		this.obj_defined = obj_defined;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public List<DocStdModelField> getFields() {
		return fields;
	}

	public void setFields(List<DocStdModelField> fields) {
		this.fields = fields;
	}
}