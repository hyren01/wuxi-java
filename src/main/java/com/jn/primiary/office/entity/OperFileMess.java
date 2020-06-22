package com.jn.primiary.office.entity;

import java.util.List;

public class OperFileMess {

    private String file_id;
    private List<OperDocStdModel> std_mess;
    private List<OperDocStdCodeInfo> codeinfo_mess;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public List<OperDocStdModel> getStd_mess() {
        return std_mess;
    }

    public void setStd_mess(List<OperDocStdModel> std_mess) {
        this.std_mess = std_mess;
    }

    public List<OperDocStdCodeInfo> getCodeinfo_mess() {
        return codeinfo_mess;
    }

    public void setCodeinfo_mess(List<OperDocStdCodeInfo> codeinfo_mess) {
        this.codeinfo_mess = codeinfo_mess;
    }

    @Override
    public String toString() {
        return "OperFileMess{" +
                "std_mess=" + std_mess +
                ", codeinfo_mess=" + codeinfo_mess +
                '}';
    }
}
