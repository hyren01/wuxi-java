package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Table(name = "sys_user")
@Entity
@DynamicInsert
@DynamicUpdate
public class SysUser {
    @Id
    @Column(name = "user_id",nullable = false,length = 32)
    private String id;

    @Column(name = "username",nullable = false,length = 512)
    private String username;

    @Column(name = "password",nullable = false,length = 100)
    private String password;

    @Column(name = "create_time",nullable = false,length = 50)
    private String create_time;

    @Column(name = "remark",nullable = false,length = 1024)
    private String remark;

    @Transient
    @ManyToMany
    @JoinTable(name="user_role_table",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<SysRole> rolelist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SysRole> getRolelist() {
        return rolelist;
    }

    public void setRolelist(List<SysRole> rolelist) {
        this.rolelist = rolelist;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", create_time='" + create_time + '\'' +
                ", remark='" + remark + '\'' +
                ", rolelist=" + rolelist +
                '}';
    }
}
