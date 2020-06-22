package com.jn.primiary.beyondsoft.entity.primiarykey;

import java.io.Serializable;

public class StandardCompositeKey implements Serializable {
    private String id="";
    private String version="";

    public StandardCompositeKey() {
    }

    public StandardCompositeKey(String id, String version) {
        this.id = id;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
