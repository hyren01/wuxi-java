package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "permission_role_table")
@Entity
@DynamicInsert
@DynamicUpdate
public class PermissionRole {
    @Id
    @Column(name = "permission_id",nullable = false,length = 32)
    private String id;


    @Column(name = "role_id",nullable = false,length = 32)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
