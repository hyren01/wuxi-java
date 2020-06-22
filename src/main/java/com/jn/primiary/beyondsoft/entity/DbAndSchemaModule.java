package com.jn.primiary.beyondsoft.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class DbAndSchemaModule implements Serializable {
    @Id
    private String db_id;
    private String db_name;
    private String db_type;
    private String status;
    private String createtime;
    private String description;
    private String id;
    private String batch_no;
    private String data_type;
    private String is_auth;
    private String schemacode;
    private String version;

    public String getSchemacode() {
        return schemacode;
    }

    public void setSchemacode(String schemacode) {
        this.schemacode = schemacode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DbAndSchemaModule(String id, String batch_no, String db_id, String status, String createtime, String description, String data_type, String db_name, String db_type, String is_auth, String schemacode, String version) {
        this.id = id;
        this.batch_no = batch_no;
        this.db_id = db_id;
        this.status = status;
        this.createtime = createtime;
        this.description = description;
        this.db_name = db_name;
        this.db_type = db_type;
        this.data_type = data_type;
        this.is_auth = is_auth;
        this.schemacode = schemacode;
        this.version = version;
    }



    public DbAndSchemaModule() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }
    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }
    public String getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(String is_auth) {
        this.is_auth = is_auth;
    }

}
