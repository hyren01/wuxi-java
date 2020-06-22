package com.jn.primiary.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_stdgl_datatype_table")
@Entity
public class TbStdglDatatypeTable {

    @Id
    @Column(name = "id", nullable = true)
    private String id = "";
    @Column(name = "type_cname", nullable = true)
    private String type_cname="";
    @Column(name = "type_ename", nullable = true)
    private String type_ename="";
    @Column(name = "final_cname", nullable = true)
    private String final_cname = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_cname() {
        return type_cname;
    }

    public void setType_cname(String type_cname) {
        this.type_cname = type_cname;
    }

    public String getType_ename() {
        return type_ename;
    }

    public void setType_ename(String type_ename) {
        this.type_ename = type_ename;
    }

    public String getFinal_cname() {
        return final_cname;
    }

    public void setFinal_cname(String final_cname) {
        this.final_cname = final_cname;
    }
}
