package com.jn.primiary.beyondsoft.vo;

public class CodeValueVo {

    private String id;
    private String batchNo;
    private String codeEname;
    private String required;
    private String type;
    private String maxsize;
    private String codeValue;

    public CodeValueVo(String id, String batchNo, String codeEname, String required, String type, String maxsize,String codeValue) {
        this.id = id;
        this.batchNo = batchNo;
        this.codeEname = codeEname;
        this.required = required;
        this.type = type;
        this.maxsize = maxsize;
        this.codeValue = codeValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCodeEname() {
        return codeEname;
    }

    public void setCodeEname(String codeEname) {
        this.codeEname = codeEname;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(String maxsize) {
        this.maxsize = maxsize;
    }
    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
