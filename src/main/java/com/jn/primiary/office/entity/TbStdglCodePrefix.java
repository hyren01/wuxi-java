package com.jn.primiary.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_stdgl_cname_table")
@Entity
public class TbStdglCodePrefix {

    @Id
    @Column(name = "id", nullable = true)
    private String id;
    @Column(name = "filename", nullable = true)
    private String filename;
    @Column(name = "prefix", nullable = true)
    private String prefix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
