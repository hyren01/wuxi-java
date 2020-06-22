package com.jn.primiary.office.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "tb_stdgl_schemafield")
@Entity
public class DocStdModelField {
	
	@Id
	@Column(name = "id", nullable = true, length = 32)
	private String id; // 字段id

	@Column(name = "batch_no", nullable = false, length = 32)
	private String batch_no = "";// 操作id

	@Column(name = "version", nullable = false, length = 10)
	private String version = "";// 版本

	@Column(name = "fieldcode", nullable = true, length = 100)
	private String code; // 字段编码
	
	@Column(name = "fieldname", nullable = true, length = 100)
	private String cName; // 字段中文名称
	
	@Column(name = "enname", nullable = true, length = 100)
	private String enName; // 英文名称

	@Column(name = "defination", nullable = true, length = 2048)
	private String defination; // 定义
	
	@Column(name = "type", nullable = true, length = 20)
	private String type ; // 数据类型
	
	@Column(name = "maxsize", nullable = true, length = 30)
	private String maxSize=""; // 最大长度
	
	@Column(name = "range", nullable = true, length = 2048)
	private String range; // 值域
	
	@Column(name = "required", nullable = true, length = 1)
	private String required=""; // 是否必填
	
	@Column(name = "comments", nullable = true, length = 2048)
	private String comments; // 备注
	
	@Column(name = "maxcontaincount", nullable = true, length = 5)
	private int maxContains = 1; // 最大出现次数
	
	@Column(name = "pxh", nullable = true, length = 10)
	private int pxh = 128; // 排序号
	
	@Column(name = "schemacode", nullable = true, length = 32)
	private String schemacode; //schemacode

	@Column(name = "obj_id", nullable = true, length = 32)
	private String obj_id; //对象id

	@Column(name = "code_id", nullable = true, length = 32)
	private String code_id; //码表id

	@Column(name = "creator", nullable = true, length = 128)
	private String creator = ""; // 创建者

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "createtime", nullable = true)
	private Date createTime;// 创建时间

	@Column(name = "fieldrange", nullable = true, length = 128)
	private String fieldrange = ""; // fieldrange

	@Column(name = "tb_stdgl_schemamodule_id", nullable = true, length = 32)
	private String model_id; //标准id

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

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getDefination() {
		return defination;
	}

	public void setDefination(String defination) {
		this.defination = defination;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
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

	public int getMaxContains() {
		return maxContains;
	}

	public void setMaxContains(int maxContains) {
		this.maxContains = maxContains;
	}

	public int getPxh() {
		return pxh;
	}

	public void setPxh(int pxh) {
		this.pxh = pxh;
	}

	public String getSchemacode() {
		return schemacode;
	}

	public void setSchemacode(String schemacode) {
		this.schemacode = schemacode;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String getCode_id() {
		return code_id;
	}

	public void setCode_id(String code_id) {
		this.code_id = code_id;
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
}