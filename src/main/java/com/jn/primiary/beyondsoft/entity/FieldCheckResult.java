package com.jn.primiary.beyondsoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_meta_modelfield_checkresult")
@Entity
public class FieldCheckResult {

	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id;
	
	@Column(name = "stdname", nullable = true, length = 512)
	private String stdname;

	@Column(name = "stdproperty", nullable = true, length = 512)
	private String stdProperty;
	
	@Column(name = "checkresult", nullable = true, length = 128)
	private String checkResult;
	
	@Column(name = "detail", nullable = true, length = 2048)
	private String detail;

	@Column(name = "field_id", nullable = true, length = 128)
	private String fieldId;
	@Column(name = "field_code", nullable = true, length = 128)
	private String fieldCode;
	@Column(name = "version", nullable = true, length = 128)
	private String version;
	@Column(name = "check_user", nullable = true, length = 128)
	private String checkUser;
	@Column(name = "check_date", nullable = true, length = 128)
	private Date checkDate;
	@Column(name = "moudle_id", nullable = true, length = 128)
	private String moudleId;



	@Column(name = "obj_id", nullable = true, length = 128)
	private String objId;

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getMoudleId() {
		return moudleId;
	}

	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStdname() {
		return stdname;
	}

	public void setStdname(String stdname) {
		this.stdname = stdname;
	}

	public String getStdProperty() {
		return stdProperty;
	}

	public void setStdProperty(String stdProperty) {
		this.stdProperty = stdProperty;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	
	
}
