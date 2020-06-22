package com.jn.primiary.metadata.entity;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Table(name = "tb_role")
@Entity
@JsonIgnoreProperties(value= {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class SysRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role_id", nullable = false, length = 13)
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long roleId; // 编号
	
	@Column(name = "role_name", nullable = false, length = 100)
    private String roleName; // 角色名称,UI界面显示使用,例如"某某管理员角色"
	
	@Column(name = "role_sign", nullable = true, length = 64)
    private String roleSign; // 角色标示,如"admin",这个是唯一的
	
	@Column(name = "creator", nullable = true, length = 50)
    private String creator;
	
	@Column(name = "create_time", nullable = true)
    private Date createTime;
	
	@Column(name = "remark", nullable = true, length = 140)
    private String remark;
	
	@Column(name = "delete_flag", nullable = true, length = 1)
	private Integer deleteFlag = 0;
	
	@Column(name = "system_id", nullable = false, length = 11)
    private Integer systemId = 0;		// 系统ID
    
	@JoinTable(name = "tb_role_authority", 
			joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id") }, 
			inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authority_id") })
	@ManyToMany(fetch=FetchType.EAGER)
    private Set<SysPermission> permissions = new HashSet<SysPermission>();
	
	public SysRole() {
		createTime = Calendar.getInstance().getTime();
	}


	
	public Long getRoleId() {
		return roleId;
	}



	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public String getRoleSign() {
		return roleSign;
	}



	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}



	public String getCreator() {
		return creator;
	}



	public void setCreator(String creator) {
		this.creator = creator;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getDeleteFlag() {
		return deleteFlag;
	}



	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}



	public Integer getSystemId() {
		return systemId;
	}



	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}



	public Set<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SysPermission> permissions) {
        this.permissions = permissions;
    }



	@Override
	public String toString() {
		return "SysRole [roleId=" + roleId + ", roleName=" + roleName + ", roleSign=" + roleSign + ", creator="
				+ creator + ", createTime=" + createTime + ", remark=" + remark + ", deleteFlag=" + deleteFlag
				+ ", systemId=" + systemId + ", permissions=" + permissions + "]";
	}

}