/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.office.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "tb_meta_modelfield_checkresult")
@Entity
public class DocFieldCheckResult {
	
	@Id
	@Column(name = "id", nullable = false, length = 128)
	private String id = "";// 唯一标识

	@Column(name = "stdname", nullable = false, length = 512)
	private String stdname = "";// 标准文件名
	
	@Column(name = "stdproperty", nullable = true, length = 512)
	private String stdproperty = ""; // 标准名称
	
	@Column(name = "checkresult", nullable = true, length = 128)
	private String checkresult = "";// 对标结果，取值为符合、不符合、相似
	
	@Column(name = "detail")
	private String detail = "";	// 模型类型，分为普通模型/组合模型

	@OneToOne(cascade={CascadeType.ALL,CascadeType.ALL},optional=false)
	@JoinColumn(name="field_id")
	private DocModelField modelfield;
	
	
	
	public DocModelField getModelfield() {
		return modelfield;
	}

	public void setModelfield(DocModelField modelfield) {
		this.modelfield = modelfield;
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

	public String getStdproperty() {
		return stdproperty;
	}

	public void setStdproperty(String stdproperty) {
		this.stdproperty = stdproperty;
	}

	public String getCheckresult() {
		return checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	

}