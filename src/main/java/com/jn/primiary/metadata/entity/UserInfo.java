package com.jn.primiary.metadata.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "tb_user")
@Entity
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String STATIC_SALT = "jict";  // 江南计算技术研究所

	@Id
	@Column(name = "user_id", nullable = false, length = 50)
	private String userId; // 用户主键，可用uuid生成唯一编号

	@Column(name = "user_name", nullable = false, length = 50)
	private String userName; // 帐号

	@JsonIgnore
	@Column(name = "unit_id", nullable = true, length = 50)
	private String unitId; // 单位id

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "create_time", nullable = false)
	private Date createTime; //

	@JsonIgnore
	@Column(name = "delete_flag", nullable = false)
	private int deleteFlag = 0; //

	@JsonIgnore
	@Column(name = "area_id", nullable = true)
	private int areaId; //

	@Column(name = "nick_name", nullable = true, length = 50)
	private String nickName;// 名称（昵称或者真实姓名，不同系统不同定义）

	@JsonIgnore
	@Column(name = "password", nullable = false, length = 256)
	private String password; // 密码;

	@JsonIgnore
	@Column(name = "salt", nullable = false, length = 50)
	private String salt;// 加密密码的盐

	@JsonIgnore
	@Column(name = "status", nullable = false, length = 4)
	private byte status = 0;// 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.

	@JoinTable(name = "tb_user_role", 
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id") }, 
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id") })
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<SysRole> roles = new HashSet<SysRole>();// 一个用户具有多个角色

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	/**
	 * 密码盐.
	 * 
	 * @return
	 */
	// 重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
	@JsonIgnore
	public String getCredentialsSalt() {
		return STATIC_SALT + salt + userId;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", unitId=" + unitId + ", createTime="
				+ createTime + ", deleteFlag=" + deleteFlag + ", areaId=" + areaId + ", nickName=" + nickName
				+ ", password=" + password + ", salt=" + salt + ", status=" + status + ", roles=" + roles + "]";
	}
	
}