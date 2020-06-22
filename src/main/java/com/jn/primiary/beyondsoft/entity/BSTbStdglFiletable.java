package com.jn.primiary.beyondsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_stdgl_filetable")
@Entity
public class BSTbStdglFiletable {

    @Id
    @Column(name = "file_id", nullable = false, length = 32)
    private String file_id = "";

    @Column(name = "file_name", nullable = false, length = 100)
    private String file_name = "";

    @Column(name = "upload_person", nullable = false, length = 50)
    private String upload_person = "";

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "upload_time", nullable = true)
    private Date upload_time;// 创建时间

    @Column(name = "is_auth", nullable = false)
    private int auth_status;

    @Column(name = "status", nullable = false)
    private int status;


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
}
