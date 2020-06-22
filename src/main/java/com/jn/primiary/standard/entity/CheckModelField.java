/***********************************************************************
 * Model:  ModelField.java
 * Author:  gongyf
 * Purpose: 模型的字段定义
 ***********************************************************************/

package com.jn.primiary.standard.entity;






public class CheckModelField {
	
	
	private String id; // 字段id
	

	private String modelId; // 字段所属模型ID

	
	private String code; // 字段编码
	

	private String name; // 字段中文名称
	

	private String enName; // 英文名称
	

	private boolean required = false; // 是否必填
	

	private boolean unique = false; // 是否唯一
	

	private String defaultValue = ""; // 默认值
	

	private int maxsize = 256; // 最大长度
	

	private Integer precition = 6;		// 精度
	

	private Integer scale = 5;		// 小数点位数
	

	private String type = "STRING"; // 类型
	

	private boolean primary = false; // 主键


	private int pxh = 128; // 排序号
	

	public String getStdProperty() {
		return stdProperty;
	}

	public void setStdProperty(String stdProperty) {
		this.stdProperty = stdProperty;
	}

	public String getStdname() {
		return stdname;
	}

	public void setStdname(String stdname) {
		this.stdname = stdname;
	}

	private String range; // 值域
	

	private String defination; // 定义

	
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

	private int maxContains = 1; // 最大出现次数
	
	
	private String comments; // 备注
	

	private Integer security=1; // 密级 （1表示公开、2表示内部、3表示秘密、4表示机密、5表示绝密）
	
	private String checkResult;//核标结果 
	
	private String detail;//核标详情
	
	private String stdProperty;
	
	private String stdname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
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

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public int getPxh() {
		return pxh;
	}

	public void setPxh(int pxh) {
		this.pxh = pxh;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getDefination() {
		return defination;
	}

	public void setDefination(String defination) {
		this.defination = defination;
	}

	public int getMaxContains() {
		return maxContains;
	}

	public void setMaxContains(int maxContains) {
		this.maxContains = maxContains;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getSecurity() {
		return security;
	}

	public void setSecurity(Integer security) {
		this.security = security;
	}

	public Integer getPrecition() {
		return precition;
	}

	public void setPrecition(Integer precition) {
		this.precition = precition;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "ModelField [id=" + id + ", modelId=" + modelId + ", code=" + code + ", name=" + name + ", enName="
				+ enName + ", required=" + required + ", unique=" + unique + ", defaultValue=" + defaultValue
				+ ", maxsize=" + maxsize + ", precition=" + precition + ", scale=" + scale + ", type=" + type
				+ ", primary=" + primary + ", pxh=" + pxh + ", range=" + range + ", defination=" + defination
				+ ", maxContains=" + maxContains + ", comments=" + comments + ", security=" + security + "]";
	}

	
}