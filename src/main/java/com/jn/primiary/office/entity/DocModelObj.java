/***********************************************************************
 * Model:  ModelField.java
 * Author:  gongyf
 * Purpose: 模型的字段定义
 ***********************************************************************/

package com.jn.primiary.office.entity;

import javax.persistence.*;

@Table(name = "tb_stdgl_object")
@Entity
public class DocModelObj {
	
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id; // 字段id

	/*
	@JsonIgnore
	@Column(name = "model_id", nullable = false, length = 32)
	private String modelId; // 字段所属模型ID
   */
	@Column(name = "code", nullable = false, length = 128)
	private String code; // 字段编码
	
	@Column(name = "cn_name", nullable = true, length = 128)
	private String name; // 字段中文名称
	
	@Column(name = "en_name", nullable = true, length = 128)
	private String enName; // 英文名称
	
	@Column(name = "definded", nullable = true, length = 1024)
	private String definded; // 定义

	@Column(name = "type", nullable = true, length = 128)
	private String type; // 英文名称

	@ManyToOne(cascade={CascadeType.ALL,CascadeType.ALL})
	@JoinColumn(name="model_id")
	private DocModel model;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getDefinded() {
		return definded;
	}

	public void setDefinded(String definded) {
		this.definded = definded;
	}

	public DocModel getModel() {
		return model;
	}

	public void setModel(DocModel model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}