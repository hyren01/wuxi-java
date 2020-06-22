package com.jn.primiary.beyondsoft.entity;

import javax.persistence.*;
import java.util.List;


@Table(name = "tb_stdgl_object")
@Entity
public class StandardObject {

	@Id
	@Column(name = "obj_id", nullable = false, length = 32)
    private String objId;//目录ID
    
	@Column(name = "version", nullable = false, length = 100)
	private String version;//	字段编码
    
	@Column(name = "obj_cname", nullable = false, length = 100)
    private String objCname;//	字段中文名
    
	@Column(name = "obj_ename", nullable = true, length = 100)
    private String objEname;//	字段英文名称
    
	@Column(name = "short_name", nullable = true, length = 50)
    private String shortName;//	字段是否必须
    
    @Column(name = "obj_defined", nullable = true, length = 30)
    private String objDefined;//	字段长度
    
    @Column(name = "obj_data_type", nullable = true, length = 20)
    private String objDataType;//	字段类型

	@Column(name = "moudle_id", nullable = false, length = 32)
	private String moudleId;

	@Column(name = "remark", nullable = false, length = 1024)
	private String remark;	//备注

	@Transient
	private List<StandardField> standardField;

	public List<StandardField> getStandardField() {
		return standardField;
	}

	public void setStandardField(List<StandardField> standardField) {
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

	
