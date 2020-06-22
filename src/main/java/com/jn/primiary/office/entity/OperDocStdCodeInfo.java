/***********************************************************************
 * Model:  Model.java
 * Author:  Administrator
 * Purpose: 模型对象
 ***********************************************************************/

package com.jn.primiary.office.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "oper_tb_stdgl_code_info")
@Entity
public class OperDocStdCodeInfo {

	@Transient
	private String fulu_id = "";// 附录标识

	@Column(name = "batch_no", nullable = false, length = 10)
	private String batch_no = "";// 唯一标识

	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String code_id = "";// 标准id

	@Column(name = "codetable_cname", nullable = true, length = 100)
	private String codetable_cname = ""; // 码表中文名称

	@Column(name = "codetable_ename", nullable = true, length = 100)
	private String codetable_ename = ""; // 码表英文名称

	@Column(name = "code_cname", nullable = true, length = 100)
	private String code_cname = ""; // 码值中文名称

	@Column(name = "code_ename", nullable = true, length = 100)
	private String code_ename = ""; // 码值英文名称

	@Column(name = "code_value", nullable = true, length = 100)
	private String code_value = ""; // 代码值

	@Column(name = "remark", nullable = true, length = 100)
	private String remark = ""; // 备注

	@Column(name = "is_auth", nullable = true)
	private Integer auth_status ; // 审核状态

	@Column(name = "auth_remark", nullable = true, length = 1024)
	private String auth_remark = "";// 审核意见

	@Column(name = "create_user", nullable = true, length = 50)
	private String creator = "";// 创建人

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "create_time", nullable = true)
	private Date create_time;// 创建时间

	@Column(name = "file_id", nullable = true, length = 50)
	private String file_id = "";// 文件id

	@Column(name = "data_type", nullable = true)
	private int oper_flag;// 操作标识

	@Column(name = "status", nullable = true)
	private int status ;// 状态

	@Column(name = "update_user", nullable = true)
	private String update_user;//修改人

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "update_time", nullable = true)
	private Date update_time;// 修改时间

	@Column(name = "audit_user", nullable = true)
	private String audit_user;//审批人

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "audit_time", nullable = true)
	private Date audit_time;// 审核时间


//	@Transient
//	private OperDocStdCodeInfo tmpcodeinfo;
//
//	public OperDocStdCodeInfo getTmpcodeinfo() {
//		return tmpcodeinfo;
//	}
//
//	public void setTmpcodeinfo(OperDocStdCodeInfo tmpcodeinfo) {
//		this.tmpcodeinfo = tmpcodeinfo;
//	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getCode_id() {
		return code_id;
	}

	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}

	public String getCodetable_cname() {
		return codetable_cname;
	}

	public void setCodetable_cname(String codetable_cname) {
		this.codetable_cname = codetable_cname;
	}

	public String getCodetable_ename() {
		return codetable_ename;
	}

	public void setCodetable_ename(String codetable_ename) {
		this.codetable_ename = codetable_ename;
	}

	public String getCode_cname() {
		return code_cname;
	}

	public void setCode_cname(String code_cname) {
		this.code_cname = code_cname;
	}

	public String getCode_ename() {
		return code_ename;
	}

	public void setCode_ename(String code_ename) {
		this.code_ename = code_ename;
	}

	public String getCode_value() {
		return code_value;
	}

	public void setCode_value(String code_value) {
		this.code_value = code_value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAuth_status() {
		return auth_status;
	}

	public void setAuth_status(Integer auth_status) {
		this.auth_status = auth_status;
	}

	public String getAuth_remark() {
		return auth_remark;
	}

	public void setAuth_remark(String auth_remark) {
		this.auth_remark = auth_remark;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}


	public String getFulu_id() {
		return fulu_id;
	}

	public void setFulu_id(String fulu_id) {
		this.fulu_id = fulu_id;
	}

	public int getOper_flag() {
		return oper_flag;
	}

	public void setOper_flag(int oper_flag) {
		this.oper_flag = oper_flag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}

	public Date getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}
}