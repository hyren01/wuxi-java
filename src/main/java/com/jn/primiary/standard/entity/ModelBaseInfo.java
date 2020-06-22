package com.jn.primiary.standard.entity;

public class ModelBaseInfo {

	private int status = 0; // 命中1,相似2,没有命中0
	private String fieldId; // 字段ID
	private String fieldCode; // 属性标识
	private String fieldName; // 属性名称
	private String type; // 类型
	private String StdfieldId = "0"; // 命中的标准字段ID
	private String StdFieldCode;
	private String StdFieldType; // 命中的的标准字段类型
	private String StdschemaID; // 命中的标准字段所在的标准表ID
	private String StdschemaCode; // 命中的标准字段所在的标准表名
	private int table_status = 0;  //第一个字段标准检测返回有效表示整个表是否通过符合标准   0表示整个模型未通过标准，1表示通过

	public int getTable_status() {
		return table_status;
	}

	public void setTable_status(int table_status) {
		this.table_status = table_status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStdschemaID() {
		return StdschemaID;
	}

	public void setStdschemaID(String stdschemaID) {
		StdschemaID = stdschemaID;
	}

	public String getStdschemaCode() {
		return StdschemaCode;
	}

	public void setStdschemaCode(String stdschemaCode) {
		StdschemaCode = stdschemaCode;
	}

	public String getStdFieldType() {
		return StdFieldType;
	}

	public void setStdFieldType(String stdFieldType) {
		StdFieldType = stdFieldType;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getStdfieldId() {
		return StdfieldId;
	}

	public void setStdfieldId(String stdfieldId) {
		StdfieldId = stdfieldId;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStdFieldCode() {
		return StdFieldCode;
	}

	public void setStdFieldCode(String stdFieldCode) {
		StdFieldCode = stdFieldCode;
	}

}
