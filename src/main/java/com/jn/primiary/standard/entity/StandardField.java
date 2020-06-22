package com.jn.primiary.standard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 标准目录信息
 *
 * @author wld
 * @date 2019-05-08 16:00
 */

@Table(name = "tb_stdgl_schemafield")
@Entity
public class StandardField {

	@Id
	@Column(name = "id", nullable = false, length = 32)
    private String id;//目录ID
    
	@Column(name = "fieldcode", nullable = false, length = 100)
	private String code;//	字段编码
    
	@Column(name = "fieldname", nullable = false, length = 100)
    private String name;//	字段中文名
    
	@Column(name = "enname", nullable = true, length = 100)
    private String enName;//	字段英文名称
    
	@Column(name = "required", nullable = true, length = 50)
    private String required;//	字段是否必须
    
	@Transient
    private String defaultValue;//	字段缺省值
    
    @Column(name = "maxsize", nullable = true, length = 30)
    private int maxsize;//	字段长度
    
    @Column(name = "type", nullable = true, length = 20)
    private String type;//	字段类型
    
    @Transient
    private boolean primary;//	字段是否主键
    
    @Column(name = "pxh", nullable = false, length = 20)
    private int pxh;//	字段排序号
    
    @Column(name = "fieldrange", nullable = true, length = 2048)
    private String range;//	字段范围
    
    @Column(name = "defination", nullable = true, length = 2048)
    private String defination;//	字段定义
    
    @Column(name = "maxcontaincount", nullable = false, length = 5)
    private int maxContains;//	字段最大出现次数
    
    @Column(name = "comments", nullable = true, length = 2048)
    private String comments;//	字段注释
    
    @JsonIgnore
	@Column(name = "schemacodeid", nullable = false, length = 32)
	private String schemacodeId; // modelid
    
    @Column(name = "code_id", nullable = true, length = 32)
    private String codeId;
    
    @Transient
    private String security;//	（内部使用）
    
    @Transient
    private String state;//	字段状态（内部使用）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSchemacodeId() {
		return schemacodeId;
	}
	public void setSchemacodeId(String schemacodeId) {
		this.schemacodeId = schemacodeId;
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
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
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
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
}

	
