package com.jn.primiary.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 标准目录信息
 *
 * @author wld
 * @date 2019-05-08 16:00
 */

@Table(name = "tb_stdgl_schemamodule")
@Entity
public class Standard {

	@Id
	@Column(name = "id", nullable = false, length = 32)
    private String id;//目录ID
    
	@Column(name = "schemacode", nullable = false, length = 50)
    private String code;//	标准编码
    
	@Column(name = "enname", nullable = true, length = 100)
	private String enName;//	标准英文名称
  
	@Column(name = "schemaname", nullable = true, length = 100)
    private String name;//	标准中文名称
    
	@Column(name = "type", nullable = false, length = 1)
	private Integer type;//	41表示该条信息是标准信息
    
    
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "createtime", nullable = true)
	private Date createTime;//	创建时间
    
	@Column(name = "creator", nullable = true, length = 50)
    private String createPerson;//	创建人
    
	@Transient
    private Date updateTime;//	更新时间
    
	@Transient
    private String updatePerson;//	更新人
    
    @Column(name = "description", nullable = true, length = 2048)
    private String description;//	标准描述
    
    @Column(name = "version", nullable = true, length = 32)
    private String version;//	版本
    
    
    @Column(name = "datasource", nullable = true, length = 256)
    private String dataSource;//	数据源
    
    
    @Column(name = "categoryid", nullable = false, length = 32)
    private String categoryId;//挂载目录ID
    
    public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return type;
	}


		
    
    @Transient
    private List<StandardField> fields=new ArrayList<StandardField>();//	字段信息(空，需要通过5.3.4标准详细信息获取接口获取)

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
    
	
	
	public List<StandardField> getFields() {
		return fields;
	}

	public void setFields(List<StandardField> fields) {
		this.fields = fields;
	}
    

  
    
   
}
