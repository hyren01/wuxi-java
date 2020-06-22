package com.jn.primiary.office.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "oper_tb_stdgl_schemafield")
@Entity
public class OperDocStdModelField {

	@Column(name = "batch_no", nullable = false, length = 32)
	private String batch_no = "";// 操作id

	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String field_id = "";// 字段id

	@Column(name = "fieldcode", nullable = true, length = 100)
	private String code; // 短名
	
	@Column(name = "fieldname", nullable = true, length = 100)
	private String cname; // 字段中文名称
	
	@Column(name = "enname", nullable = true, length = 100)
	private String ename; // 英文名称

	@Column(name = "defination", nullable = true, length = 2048)
	private String definded; // 定义
	
	@Column(name = "type", nullable = true, length = 20)
	private String datatype ; // 数据类型
	
	@Column(name = "maxsize", nullable = true, length = 30)
	private String length=""; // 最大长度
	
	@Column(name = "rangee", nullable = true, length = 2048)
	private String field_range; // 值域
	
	@Column(name = "required", nullable = true, length = 1)
	private String required=""; // 是否必填
	
	@Column(name = "comments", nullable = true, length = 2048)
	private String comments = ""; // 备注
	
	@Column(name = "maxcontaincount", nullable = true, length = 5)
	private String maxContains = ""; // 最大出现次数
	
	@Column(name = "pxh", nullable = true, length = 10)
	private int pxh = 128; // 排序号

	@Column(name = "obj_id", nullable = true, length = 32)
	private String obj_id; //对象id

	@Column(name = "creator", nullable = true, length = 128)
	private String creator = ""; // 创建者

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "createtime", nullable = true)
	private Date createtime;// 创建时间

	@Column(name = "fieldrange", nullable = true, length = 128)
	private String fieldrange = ""; // fieldrange

	@Column(name = "moudle_id", nullable = true, length = 32)
	private String model_id; //标准id

	@Column(name = "data_type", nullable = true)
	private int oper_flag; //操作标识

	@Column(name = "version", nullable = true, length = 32)
	private String version; //版本

	@Column(name = "status", nullable = true)
	private int status; //状态

	@Transient
	private OperDocStdModelField tmpfield;

	public OperDocStdModelField getTmpfield() {
		return tmpfield;
	}

	public void setTmpfield(OperDocStdModelField tmpfield) {
		this.tmpfield = tmpfield;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getField_id() {
		return field_id;
	}

	public void setField_id(String field_id) {
		this.field_id = field_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getDefinded() {
		return definded;
	}

	public void setDefinded(String definded) {
		this.definded = definded;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getField_range() {
		return field_range;
	}

	public void setField_range(String field_range) {
		this.field_range = field_range;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMaxContains() {
		return maxContains;
	}

	public void setMaxContains(String maxContains) {
		this.maxContains = maxContains;
	}

	public int getPxh() {
		return pxh;
	}

	public void setPxh(int pxh) {
		this.pxh = pxh;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getFieldrange() {
		return fieldrange;
	}

	public void setFieldrange(String fieldrange) {
		this.fieldrange = fieldrange;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public int getOper_flag() {
		return oper_flag;
	}

	public void setOper_flag(int oper_flag) {
		this.oper_flag = oper_flag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}