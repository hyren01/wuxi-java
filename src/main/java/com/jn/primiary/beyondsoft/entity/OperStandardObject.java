package com.jn.primiary.beyondsoft.entity;

import javax.persistence.*;
import java.util.List;


@Table(name = "oper_tb_stdgl_object")
@Entity
public class OperStandardObject {
	@Id
	@Column(name = "obj_id", nullable = false, length = 32)
    private String objId;//目录ID
    
	@Column(name = "version", nullable = false, length = 100)
	private String version;//	对象中文名
    
	@Column(name = "obj_cname", nullable = false, length = 100)
    private String objCname;//	对象中文名
    
	@Column(name = "obj_ename", nullable = true, length = 100)
    private String objEname;//	对象英文名
    
	@Column(name = "short_name", nullable = true, length = 50)
    private String shortName;//	短名
    
    @Column(name = "obj_defined", nullable = true, length = 30)
    private String objDefined;//	定义
    
    @Column(name = "obj_data_type", nullable = true, length = 20)
    private String objDataType;//	数据类型


	@Column(name = "batch_no", nullable = false, length = 30)
	private String batchNo;
	@Column(name = "data_type", nullable = false, length = 10)
	private String dataType;
	@Column(name = "moudle_id", nullable = false, length = 32)
	private String moudleId;
	@Column(name = "status", nullable = false, length = 32)
	private String status;
	@Column(name = "remark", nullable = true, length = 1024)
	private String remark;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	private List<OperStandardField> standardField;

	public List<OperStandardField> getStandardField() {
		return standardField;
	}

	public void setStandardField(List<OperStandardField> standardField) {
		this.standardField = standardField;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getObjCname() {
		return objCname;
	}

	public void setObjCname(String objCname) {
		this.objCname = objCname;
	}

	public String getObjEname() {
		return objEname;
	}

	public void setObjEname(String objEname) {
		this.objEname = objEname;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getObjDefined() {
		return objDefined;
	}

	public void setObjDefined(String objDefined) {
		this.objDefined = objDefined;
	}

	public String getObjDataType() {
		return objDataType;
	}

	public void setObjDataType(String objDataType) {
		this.objDataType = objDataType;
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

	public String getMoudleId() {
		return moudleId;
	}

	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

	
