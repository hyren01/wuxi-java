package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user_role_table")
@Entity
@DynamicInsert
@DynamicUpdate
public class UserRoleTable {
    @Id
    @Column(name = "user_id",nullable = false,length = 32)
    private String user_id;


    @Column(name = "role_id",nullable = false,length = 32)
    private String role_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
