package com.jn.primiary.beyondsoft.entity;

import javax.persistence.Column;

public class TbSchemaModule {
    @Column(name = "id",nullable = false,length = 32)
    private String id;
    @Column(name = "schemaname",nullable = true,length = 100)
    private String schemaname;
    @Column(name = "enname",nullable = true,length = 100)
    private String enname;
    @Column(name = "schemacode",nullable = false,length = 50)
    private String schemacode;
    @Column(name = "description",nullable = true,length = 65535)
    private String description;
    @Column(name = "datasource",nullable = false,length = 20)
    private String datasource;
    @Column(name = "version",nullable = true,length = 32)
    private String version;
    @Column(name = "type",nullable = true,length = 1)
    private Integer type;
    @Column(name = "creator",nullable = true,length = 50)
    private String creator;
    @Column(name = "createtime",nullable = true,length = 32)
    private String createtime;
    @Column(name = "categoryid",nullable = false,length = 50)
    private String categoryid;
    @Column(name = "update_time",nullable = true,length = 32)
    private String update_time;
    @Column(name = "update_person",nullable = true,length = 50)
    private String update_person;
    @Column(name = "status",nullable = true,length = 2)
    private String status;
    @Column(name = "auth_remark",nullable = true,length = 1024)
    private String auth_remark;
    @Column(name = "file_id",nullable = true,length = 32)
    private String file_id;
    @Column(name = "db_id",nullable = true,length = 32)
    private String db_id;

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

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuth_remark() {
        return auth_remark;
    }

    public void setAuth_remark(String auth_remark) {
        this.auth_remark = auth_remark;
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
}
