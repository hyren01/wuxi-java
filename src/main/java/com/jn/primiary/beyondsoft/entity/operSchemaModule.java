package com.jn.primiary.beyondsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "oper_tb_stdgl_schemamodule")
@Entity
@DynamicInsert
@DynamicUpdate
public class operSchemaModule implements Serializable{
    @Id
    @Column(name = "batch_no",nullable = false,length = 32)
    private String batch_no;
    @Id
    @Column(name = "id",nullable = false,length = 32)
    private String id;
    @Column(name = "schemaname",nullable = true,length = 100)
    private String schemaname;
    @Column(name = "enname",nullable = true,length = 100)
    private String enname;
    @Column(name = "schemacode",nullable = true,length = 50)
    private String schemacode;
    @Column(name = "description",nullable = true,length = 65535)
    private String description;
    @Column(name = "datasource",nullable = true,length = 100)
    private String datasource;
    @Column(name = "version",nullable = true,length = 32)
    private String version;
    @Column(name = "type",nullable = true,length = 11)
    private Integer type;
    @Column(name = "creator",nullable = true,length = 50)
    private String creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "createtime",nullable = true,length = 32)
    private String createtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time",nullable = true,length = 32)
    private String update_time;
    @Column(name = "update_person",nullable = true,length = 50)
    private String update_person;
    @Column(name = "is_auth",nullable = true,length = 16)
    private String is_auth;
    @Column(name = "auth_remark",nullable = true,length = 1024)
    private String auth_remark;
    @Column(name = "categoryid",nullable = true,length = 50)
    private String categoryid;
    @Column(name = "file_id",nullable = true,length = 32)
    private String file_id;
    @Column(name = "db_id",nullable = true,length = 32)
    private String db_id;
    @Column(name = "data_type",nullable = true,length = 10)
    private String data_type;
    @Column(name = "audit_person",nullable = true,length = 32)
    private String audit_person;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "audit_time",nullable = true,length = 32)
    private String audit_time;
    @Column(name = "status",nullable = true,length = 2)
    private String status;

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemaname() {
        return schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getSchemacode() {
        return schemacode;
    }

    public void setSchemacode(String schemacode) {
        this.schemacode = schemacode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_person() {
        return update_person;
    }

    public void setUpdate_person(String update_person) {
        this.update_person = update_person;
    }

    public String getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(String is_auth) {
        this.is_auth = is_auth;
    }

    public String getAuth_remark() {
        return auth_remark;
    }

    public void setAuth_remark(String auth_remark) {
        this.auth_remark = auth_remark;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getAudit_person() {
        return audit_person;
    }

    public void setAudit_person(String audit_person) {
        this.audit_person = audit_person;
    }

    public String getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(String audit_time) {
        this.audit_time = audit_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
