package com.jn.primiary.beyondsoft.entity;



import javax.persistence.*;


@Table(name = "tb_stdgl_checklist")
@Entity
public class Checklist {


    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id = "";//目录ID

    @Column(name = "modelcode", nullable = false, length = 128)
    private String modelCode = "";//	模型编码

    @Column(name = "stdcode", nullable = false, length = 128)
    private String stdCode = "";//	标准编码


    @Column(name = "modelid", nullable = true, length = 128)
    private String modelId = "";//	模型id

    @Column(name = "stdid", nullable = true, length = 128)
    private String stdId = "";//	标准id


    @Column(name = "objid", nullable = true, length = 128)
    private String objId = "";//	标准objid

    @Column(name = "stdCategory", nullable = true, length = 32)
    private String stdcategory = "";//	标准属于哪个类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }



}
