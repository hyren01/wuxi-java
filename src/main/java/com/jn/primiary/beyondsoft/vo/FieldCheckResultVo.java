package com.jn.primiary.beyondsoft.vo;

import com.alibaba.fastjson.JSONObject;
import com.jn.primiary.beyondsoft.entity.StandardField;

import java.util.Date;

public class FieldCheckResultVo {
	private String id;
	private String stdname;
	private String stdProperty;
	private String checkResult;
	private String detail;
	private String fieldId;
	private String fieldCode;
	private String version;
	private String checkUser;
	private Date checkDate;
	private String moudleId;



	private StandardField standardField;


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

	public String getMoudleId() {
		return moudleId;
	}

	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}

	public StandardField getStandardField() {
		return standardField;
	}

	public void setStandardField(StandardField standardField) {
		this.standardField = standardField;
	}

}
