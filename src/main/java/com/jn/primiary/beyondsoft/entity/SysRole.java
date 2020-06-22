package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Table(name = "sys_role")
@Entity
@DynamicInsert
@DynamicUpdate
public class SysRole {
    @Id
    @Column(name = "role_id",nullable = false,length = 32)
    private String id;

    @Column(name = "role_name",nullable = false,length = 512)
    private String rolename;

    @Column(name = "type",nullable = false,length = 10)
    private String type;

    @Column(name = "remark",nullable = false,length = 1024)
    private String remark;

    @Transient
    @ManyToMany
    @JoinTable(name="permission_role_table",joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissionlist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Permission> getPermissionlist() {
        return permissionlist;
    }

    public void setPermissionlist(List<Permission> permissionlist) {
        this.permissionlist = permissionlist;
    }

}
