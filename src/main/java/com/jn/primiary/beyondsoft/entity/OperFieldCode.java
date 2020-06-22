package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "oper_tb_stdgl_field_code")
@Entity
@DynamicInsert
@DynamicUpdate
public class OperFieldCode {

	@Id
	@Column(name = "batch_no", nullable = false, length = 32)
	private String batchNo;
	@Column(name = "field_id", nullable = false, length = 32)
	private String fieldId;
	@Column(name = "code_id", nullable = false, length = 32)
	private String codeId;
	@Column(name = "data_type", nullable = false, length = 32)
	private String dataType;
	@Column(name = "codetable_ename", nullable = false, length = 32)
	private String codeName;
	@Column(name = "category_id", nullable = false, length = 50)
	private String category_id;

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
}
