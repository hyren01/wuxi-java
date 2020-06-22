package com.jn.primiary.beyondsoft.api.vo;

import java.util.List;

/**
 * @Description TODO
 * @Author chenhong
 * @Date 2019/10/24 16:08
 */
public class StandardVo {

    private String id="";//目录ID
    private String code="";//	标准编码
    private String enName="";//	标准英文名称
    private String name="";//	标准中文名称
    private Boolean type;//	41表示该条信息是标准信息
    private String createTime;//	创建时间
    private String createPerson="";//	创建人
    private String updateTime;//	更新时间
    private String updatePerson="";//	更新人
    private String description="";//	标准描述
    private String version="";//	版本
    private String categoryId="";//挂载目录ID
    private List<FieldVo> fields;

    public List<FieldVo> getFields() {
        return fields;
    }

    public void setFields(List<FieldVo> fields) {
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public StandardVo(String id, String code, String enName, String name, Boolean type, String createTime, String createPerson, String updateTime, String updatePerson, String description, String version, String categoryId) {
        this.id = id;
        this.code = code;
        this.enName = enName;
        this.name = name;
        this.type = type;
        this.createTime = createTime;
        this.createPerson = createPerson;
        this.updateTime = updateTime;
        this.updatePerson = updatePerson;
        this.description = description;
        this.version = version;
        this.categoryId = categoryId;
    }
    public StandardVo(){
        super();
    }
}
