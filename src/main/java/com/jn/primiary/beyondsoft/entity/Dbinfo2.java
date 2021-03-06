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

@Table(name = "tb_stdgl_db_table")
@Entity
@DynamicInsert
@DynamicUpdate
public class Dbinfo2 {
    @Id
    @Column(name = "db_id",nullable = false,length = 32)
    private String db_id;

    @Column(name = "db_name",nullable = false,length = 80)
    private String db_name;

    @Column(name = "db_type",nullable = false,length = 80)
    private String db_type;

    @Column(name = "db_ip",nullable = false,length = 80)
    private String db_ip;

    @Column(name = "db_port",nullable = true,length = 10)
    private String db_port;

    @Column(name = "username",nullable = true,length = 80)
    private String username;

    @Column(name = "jdbc_url",nullable = true,length = 512)
    private String jdbc_url;

    @Column(name = "password",nullable = true,length = 10)
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time",nullable = true,length = 32)
    private String create_time;

    @Column(name = "status",nullable = true,length = 10)
    private String status;

    @Column(name = "is_auth",nullable = true,length = 16)
    private String is_auth;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(String is_auth) {
        this.is_auth = is_auth;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间，格式：yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time",nullable = true,length = 16)
    private String update_time;

    @Column(name = "update_person",nullable = true,length = 512)
    private String update_person;

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getDb_type() {
        return db_type;
    }

    public void setDb_type(String db_type) {
        this.db_type = db_type;
    }

    public String getDb_ip() {
        return db_ip;
    }

    public void setDb_ip(String db_ip) {
        this.db_ip = db_ip;
    }

    public String getDb_port() {
        return db_port;
    }

    public void setDb_port(String db_port) {
        this.db_port = db_port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJdbc_url() {
        return jdbc_url;
    }

    public void setJdbc_url(String jdbc_url) {
        this.jdbc_url = jdbc_url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
