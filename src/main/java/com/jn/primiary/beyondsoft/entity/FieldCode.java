package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_stdgl_field_code")
@Entity
@DynamicInsert
@DynamicUpdate
public class FieldCode {
	
	@Id
	@Column(name = "field_id", nullable = false, length = 32)
	private String fieldId;
	@Column(name = "code_id", nullable = false, length = 32)
	private String codeId;
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
