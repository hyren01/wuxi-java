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

@Table(name = "tb_stdgl_sysdb")
@Entity
@DynamicInsert
@DynamicUpdate
public class SysDb implements Serializable {

    @Id
    @Column(name = "id",nullable = false,length = 50)
    private String id;

    @Column(name = "sys_name",nullable = false,length = 50)
    private String sysName;

    @Column(name = "db_url",nullable = false,length = 50)
    private String dbUrl;

    @Column(name = "db_name",nullable = true,length = 50)
    private String dbName;

    @Column(name = "db_username",nullable = true,length = 50)
    private String dbUsername;

    @Column(name = "db_pwd",nullable = true,length = 11)
    private String dbpwd;

    @Column(name = "db_driver",nullable = true,length = 11)
    private String dbDriver;

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbpwd() {
        return dbpwd;
    }

    public void setDbpwd(String dbpwd) {
        this.dbpwd = dbpwd;
    }
}
