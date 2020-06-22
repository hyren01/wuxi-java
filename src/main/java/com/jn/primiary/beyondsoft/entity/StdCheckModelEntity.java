package com.jn.primiary.beyondsoft.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//add by wld 2020.04.10
//保存和前台交互根据标准字段信息查询命中情况与相应的模型信息

public class StdCheckModelEntity {
    //标准的字段id
    private String stdfieldid = "";
    //标准检查的结果
    private String checkresult = "";
    //标准检查的详细结果信息
    private String detail = "";
    //模型字段的id
    private String fieldid = "";
    //模型字段的code
    private String fieldcode = "";
    //模型字段的中文名
    private String fieldcnname= "";
    //模型model的id
    private String modelid = "";
    //模型model的code
    private String modelcode = "";
    //模型model的中文名
    private String modelcnname = "";
    //标准模型的id
    private String stdmodelid = "";
    //标准模型的code
    private String stdmodelcode = "";
    //标准模型的中文名
    private String stdmodelcnname = "";

    public String getStdmodelid() {
        return stdmodelid;
    }

    public void setStdmodelid(String stdmodelid) {
        this.stdmodelid = stdmodelid;
    }

    public String getStdmodelcode() {
        return stdmodelcode;
    }

    public void setStdmodelcode(String stdmodelcode) {
        this.stdmodelcode = stdmodelcode;
    }

    public String getStdmodelcnname() {
        return stdmodelcnname;
    }

    public void setStdmodelcnname(String stdmodelcnname) {
        this.stdmodelcnname = stdmodelcnname;
    }


    public String getStdfieldid() {
        return stdfieldid;
    }

    public void setStdfieldid(String stdfieldid) {
        this.stdfieldid = stdfieldid;
    }

    public String getCheckresult() {
        return checkresult;
    }

    public void setCheckresult(String checkresult) {
        this.checkresult = checkresult;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFieldid() {
        return fieldid;
    }

    public void setFieldid(String fieldid) {
        this.fieldid = fieldid;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public String getFieldcnname() {
        return fieldcnname;
    }

    public void setFieldcnname(String fieldcnname) {
        this.fieldcnname = fieldcnname;
    }

    public String getModelid() {
        return modelid;
    }

    public void setModelid(String modelid) {
        this.modelid = modelid;
    }

    public String getModelcode() {
        return modelcode;
    }

    public void setModelcode(String modelcode) {
        this.modelcode = modelcode;
    }

    public String getModelcnname() {
        return modelcnname;
    }

    public void setModelcnname(String modelcnname) {
        this.modelcnname = modelcnname;
    }
    @Override
    public boolean equals(Object arg0){
        StdCheckModelEntity p=(StdCheckModelEntity) arg0;
        return modelid.equals(p.modelid) && stdmodelid.equals(p.stdmodelid);
    }
    @Override
    public int hashCode(){
        String str=modelid+stdmodelid;
        return str.hashCode();
    }

}
