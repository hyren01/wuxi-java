package com.jn.primiary.beyondsoft.entity;

import javax.persistence.*;
import java.util.Date;


@Table(name = "oper_tb_stdgl_schemafield")
@Entity
public class OperStandardField {

	@Id
	@Column(name = "id", nullable = false, length = 32)
    private String id;//目录ID
    
	@Column(name = "fieldcode", nullable = false, length = 100)
	private String code;//英文名称
    
	@Column(name = "fieldname", nullable = false, length = 100)
    private String name;//	中文名称
    
	@Column(name = "enname", nullable = true, length = 100)
    private String enName;//	字段英文名称
    
	@Column(name = "required", nullable = true, length = 50)
    private String required;//	是否必填
    
	@Transient
    private String defaultValue;//	字段缺省值
    
    @Column(name = "maxsize", nullable = true, length = 30)
    private int maxsize;//	长度
    
    @Column(name = "type", nullable = true, length = 20)
    private String type;//	元素数据类型
    
    @Transient
    private boolean primary;//	字段是否主键
    
    @Column(name = "pxh", nullable = false, length = 20)
    private int pxh;//	字段排序号
    
    @Column(name = "fieldrange", nullable = true, length = 2048)
    private String range;//	fieldrange

	@Column(name = "rangee", nullable = true, length = 2048)
	private String rangee;//	值域
    
    @Column(name = "defination", nullable = true, length = 2048)
    private String defination;//	定义
    
    @Column(name = "maxcontaincount", nullable = false, length = 10)
    private String maxContains;//	最大出现次数
    
    @Column(name = "comments", nullable = true, length = 2048)
    private String comments;//	备注
    
    @Transient
    private String security;//	（内部使用）
    
    @Transient
    private String state;//	字段状态（内部使用）

	@Column(name = "createtime", nullable = true)
	private Date createTime;//	创建时间

	@Column(name = "creator", nullable = true, length = 50)
	private String createPerson;//	创建人


	@Column(name = "batch_no", nullable = false, length = 30)
	private String batchNo;
	@Column(name = "data_type", nullable = false, length = 10)
	private String dataType;
	@Column(name = "moudle_id", nullable = false, length = 32)
	private String moudleId;
	@Column(name = "version", nullable = false, length = 32)
	private String version;
	@Column(name = "obj_id", nullable = false, length = 32)
	private String objId;
	@Column(name = "status", nullable = false, length = 32)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRangee() {
		return rangee;
	}

	public void setRangee(String rangee) {
		this.rangee = rangee;
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

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getMoudleId() {
		return moudleId;
	}

	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}

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

	public String getMaxContains() {
		return maxContains;
	}

	public void setMaxContains(String maxContains) {
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
}

	
