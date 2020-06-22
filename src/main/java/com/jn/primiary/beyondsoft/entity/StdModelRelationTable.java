package com.jn.primiary.beyondsoft.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "std_model_relation_table")
@Entity
@DynamicInsert
@DynamicUpdate
public class StdModelRelationTable {

    @Id
    @Column(name = "id",nullable = false,length = 32)
    private String relation_id;     //关联id

    @Column(name = "tb_stdgl_schemamodule_id",nullable = true,length = 32)
    private String std_id;  //标准id

    @Column(name = "tb_meta_model_id",nullable = true,length = 32)
    private String model_id;    //模型id

    @Column(name = "version",nullable = true,length = 32)
    private String version;    //版本

    @Column(name = "obj_id",nullable = true,length = 32)
    private String obj_id;    //对象id

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getStd_id() {
        return std_id;
    }

    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getObj_id() {
        return obj_id;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }
}
