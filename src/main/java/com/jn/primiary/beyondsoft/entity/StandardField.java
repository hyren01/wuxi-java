package com.jn.primiary.beyondsoft.entity;

import javax.persistence.*;

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
	private String code;//	英文名称

	@Column(name = "fieldname", nullable = true, length = 100)
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


    @Column(name = "pxh", nullable = false, length = 20)
    private int pxh;//	字段排序号

    @Column(name = "fieldrange", nullable = true, length = 2048)
    private String range;//	fieldrange

	@Column(name = "rangee", nullable = true, length = 2048)
	private String rangee;//	值域

    @Column(name = "defination", nullable = true, length = 2048)
    private String defination;//	定义

    @Column(name = "maxcontaincount", nullable = false, length = 5)
    private String maxContains;//	字段最大出现次数

    @Column(name = "comments", nullable = true, length = 2048)
    private String comments;//	备注

	@Column(name = "version", nullable = false, length = 32)
	private String version;

	@Column(name = "moudle_id", nullable = false, length = 32)
	private String moudleId;

	@Column(name = "obj_id", nullable = true, length = 32)
	private String objId;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMoudleId() {
		return moudleId;
	}

	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
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

	public String getRangee() {
		return rangee;
	}

	public void setRangee(String rangee) {
		this.rangee = rangee;
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
}

	
