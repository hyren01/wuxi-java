package com.jn.primiary.beyondsoft.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Table(name = "tb_stdgl_category")
@Entity
@DynamicInsert
@DynamicUpdate
public class CategoryInfo implements Serializable {
    public CategoryInfo(){ }
    public CategoryInfo(String id,String name){
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "id",nullable = false,length = 50)
    private String id;

    @Column(name = "name",nullable = false,length = 50)
    private String name;

    @Column(name = "parent_id",nullable = true,length = 50)
    private String parentId;

    @Column(name = "creator",nullable = true,length = 50)
    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time",nullable = true,length = 32)
    private String createTime;

    @Column(name = "ismodel",nullable = true,length = 11)
    private Integer ismodel;

    @Column(name = "pxh",nullable = true,length = 11)
    private Integer pxh;

    @Column(name = "status",nullable = true,length = 2)
    private String status;

//    @Transient
//    private List<CategoryInfo> children;

//    public List<CategoryInfo> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<CategoryInfo> children) {
//        this.children = children;
//    }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIsmodel() {
        return ismodel;
    }

    public void setIsmodel(Integer ismodel) {
        this.ismodel = ismodel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPxh() {
        return pxh;
    }

    public void setPxh(Integer pxh) {
        this.pxh = pxh;
    }
}
