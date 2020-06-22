package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "permission_table")
@Entity
@DynamicInsert
@DynamicUpdate
public class Permission {
    @Id
    @Column(name = "permission_id",nullable = false,length = 32)
    private String id;

    @Column(name = "permission_name",nullable = false,length = 512)
    private String name;

    @Column(name = "permission_type",nullable = false,length = 100)
    private String type;

    @Column(name = "permission_remark",nullable = false,length = 100)
    private String remark;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
