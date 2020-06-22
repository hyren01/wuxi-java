/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.office.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "oper_tb_stdgl_object")
@Entity
public class OperDocStdModelObj {

	@Column(name = "batch_no", nullable = false, length = 32)
	private String batch_no = "";// 操作id

	@Id
	@Column(name = "obj_id", nullable = false, length = 32)
	private String obj_id = "";// obj_id

	@Column(name = "obj_cname", nullable = true, length = 80)
	private String cname; // 对象中文名称

	@Column(name = "obj_ename", nullable = true, length = 80)
	private String ename; // 对象英文名称

	@Column(name = "short_name", nullable = true, length = 80)
	private String code; // code短名

	@Column(name = "obj_defined", nullable = true, length = 1024)
	private String defined; // 定义

	@Column(name = "data_type", nullable = true)
	private int oper_flag; //操作标识

	@Column(name = "obj_data_type", nullable = true, length = 80)
	private String data_type; //数据类型

	@Column(name = "moudle_id", nullable = true, length = 80)
	private String model_id; //模型id

	@Column(name = "status", nullable = true)
	private int status; //状态

	@Column(name = "version", nullable = true, length = 80)
	private String version; //版本

	@Column(name = "remark", nullable = true, length = 1024)
	private String remark; //备注

	@Transient
	private List<OperDocStdModelField> fields = new ArrayList<OperDocStdModelField>();// 字段

	@Transient
	private OperDocStdModelObj tmpobj ;

	public OperDocStdModelObj getTmpobj() {
		return tmpobj;
	}

	public void setTmpobj(OperDocStdModelObj tmpobj) {
		this.tmpobj = tmpobj;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefined() {
		return defined;
	}

	public void setDefined(String defined) {
		this.defined = defined;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public List<OperDocStdModelField> getFields() {
		return fields;
	}

	public void setFields(List<OperDocStdModelField> fields) {
		this.fields = fields;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}