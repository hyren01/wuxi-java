package com.jn.primiary.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_stdgl_codetitlemess_table")
@Entity
public class TbStdglCodetitlemessTable {
    @Id
    @Column(name = "id", nullable = false)
    private String id ="";
    @Column(name = "codeinfo_title", nullable = true)
    private String codeinfo_title="";
    @Column(name = "codeinfo_title_value", nullable = true)
    private String codeinfo_title_value ="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeinfo_title() {
        return codeinfo_title;
    }

    public void setCodeinfo_title(String codeinfo_title) {
        this.codeinfo_title = codeinfo_title;
    }

    public String getCodeinfo_title_value() {
        return codeinfo_title_value;
    }

    public void setCodeinfo_title_value(String codeinfo_title_value) {
        this.codeinfo_title_value = codeinfo_title_value;
    }
}
