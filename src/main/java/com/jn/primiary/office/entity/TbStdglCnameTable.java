package com.jn.primiary.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_stdgl_cname_table")
@Entity
public class TbStdglCnameTable {
    @Id
    @Column(name = "id", nullable = true)
    private String id;
    @Column(name = "cname", nullable = true)
    private String cname;
    @Column(name = "ename", nullable = true)
    private String ename;
    @Column(name = "code", nullable = true)
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
