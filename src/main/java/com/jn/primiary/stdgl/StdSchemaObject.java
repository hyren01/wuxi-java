/***********************************************************************
 * Module:  SchemaObject.java
 * Author:  Administrator
 * Purpose: Defines the Class SchemaObject
 ***********************************************************************/

package com.jn.primiary.stdgl;


public class StdSchemaObject {
	private String id = "";// 唯一标识
	private String schemaCode = "";// 模型对象英文名称
	private String schemaName = "";// 模型对象中文名称
	private Integer type = 0;// 对象类型（1表示为子树的根节点，0表示为普通对象）
	private String dataSource = "";// 数据源
	private String createTime = "";// 创建时间
	private String creator = "";// 创建人
	private String description = "";// 模型描述
	private StdFieldEntity[] fields;// 属性
	private String version = "";// 版本信息
	private String xmlContent = "";
	private String enName = ""; // 英文名称
	private String range = ""; // 值域
	private Integer pxh = -1; // 排序号

	private String diff = "";

	public StdSchemaObject() {
		super();
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
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

	public String getSchemaCode() {
		return schemaCode;
	}

	public void setSchemaCode(String schemaCode) {
		this.schemaCode = schemaCode;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public StdFieldEntity[] getFields() {
		return fields;
	}

	public void setFields(StdFieldEntity[] fields) {
		this.fields = fields;
	}

	// added by raoxiang:20160406
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPxh() {
		return pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}

}