package com.jn.primiary.office.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_stdgl_filetable")
@Entity
public class TbStdglFiletable {

    @Id
    @Column(name = "file_id", nullable = false, length = 32)
    private String file_id = "";

    @Column(name = "file_name", nullable = false, length = 100)
    private String file_name = "";

    @Column(name = "upload_person", nullable = true, length = 50)
    private String upload_person = "";

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "upload_time", nullable = true)
    private Date upload_time;// 创建时间

    @Column(name = "is_auth", nullable = true)
    private int auth_status;

    @Column(name = "status", nullable = true)
    private int status;

    @Column(name = "update_person", nullable = true)
    private String update_person;//修改人

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "update_time", nullable = true)
    private Date update_time;// 修改时间

    @Column(name = "is_retract", nullable = true)
    private int is_retract;//是否可撤回

    @Column(name = "relative_file_prefix", nullable = false, length = 32)
    private String relative_file_prefix = "";


    public int getIs_retract() {
        return is_retract;
    }

    public void setIs_retract(int is_retract) {
        this.is_retract = is_retract;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getUpload_person() {
        return upload_person;
    }

    public void setUpload_person(String upload_person) {
        this.upload_person = upload_person;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public int getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(int auth_status) {
        this.auth_status = auth_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdate_person() {
        return update_person;
    }

    public void setUpdate_person(String update_person) {
        this.update_person = update_person;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getRelative_file_prefix() {
        return relative_file_prefix;
    }

    public void setRelative_file_prefix(String relative_file_prefix) {
        this.relative_file_prefix = relative_file_prefix;
    }
}
