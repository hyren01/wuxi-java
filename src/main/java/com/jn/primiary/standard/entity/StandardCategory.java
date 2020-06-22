package com.jn.primiary.standard.entity;

import java.util.Date;

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
 * @date 2019-05-15 10:00
 */

@Table(name = "tb_stdmodel_category")
@Entity
public class StandardCategory {

	
	@Id
	@Column(name = "id", nullable = false, length = 50)
    private String id;//目录ID

	@Column(name = "name", nullable = false, length = 50)
	private String name;//目录名称
    
	@Column(name = "parent_id", nullable = true, length = 50)
    private String parentId;//父节点ID
    
    
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "create_time", nullable = true)
	private Date createTime;//目录创建时间
    
	@Column(name = "creator", nullable = true, length = 50)
	private String creator;//目录创建者ID
	
	
	@Column(name = "pxh", nullable = false, length = 11)
	private Integer pxh;//排序号
	
	
	
	
	
	@Transient
    private String bmid;//编目id
	
	@Transient
	private String fullbmid;//全编目id
    
	
	
	
	

	

	
	

	public Integer getPxh() {
		return pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid;
	}

	public String getFullbmid() {
		return fullbmid;
	}

	public void setFullbmid(String fullbmid) {
		this.fullbmid = fullbmid;
	}
}
