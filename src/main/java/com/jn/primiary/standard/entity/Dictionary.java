package com.jn.primiary.standard.entity;

import java.util.Date;
import java.util.List;

/**
 * 标准目录信息
 *
 * @author wld
 * @date 2019-05-08 16:00
 */
public class Dictionary {
	
	


    private String id;//字典id
     
    private String code;//	字典编码
    
    private String enName;//	字典英文名称
    
    private String name;//	字典中文名称
    
    private int type;//	51表示该条信息是字典信息
    
    private Date createTime;//	创建时间
    
    private String createPerson;//	创建人
    
    private Date updateTime;//	更新时间
    
    private String updatePerson;//	更新人
    
    private String description;//	标准描述
    
    private String version;//	版本
    
    private String categoryId;//	挂载目录ID
    
    private List<DictionaryInfo> fields;//	字段信息

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

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<DictionaryInfo> getFields() {
		return fields;
	}

	public void setFields(List<DictionaryInfo> fields) {
		this.fields = fields;
	}

	


  
    
   
}
