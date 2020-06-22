package com.jn.primiary.beyondsoft.api.vo;

/**
 * @Description TODO
 * @Author chenhong
 * @Date 2019/10/24 16:08
 */
public class FieldVo {

    private String id="";//目录ID
    private String code="";//	标准编码
    private String enName="";//	标准英文名称
    private String name="";//	标准中文名称
    private String required;//	是否必填
    private String defaultValue;//	字段缺省值
    private String maxsize;//	长度
    private String type;//	元素数据类型
    private String primary1="";//	是否主键
    private Integer pxh;//	字段排序号
    private String range1;//	值域
    private String defination;//	定义
    private Integer maxContains;//	字段最大出现次数
    private String comments;//	备注
    private String security;//
    private String state;//

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

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(String maxsize) {
        this.maxsize = maxsize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Integer getPxh() {
        return pxh;
    }

    public void setPxh(Integer pxh) {
        this.pxh = pxh;
    }


    public String getDefination() {
        return defination;
    }

    public void setDefination(String defination) {
        this.defination = defination;
    }

    public Integer getMaxContains() {
        return maxContains;
    }

    public void setMaxContains(Integer maxContains) {
        this.maxContains = maxContains;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrimary1() {
        return primary1;
    }

    public void setPrimary1(String primary1) {
        this.primary1 = primary1;
    }

    public String getRange1() {
        return range1;
    }

    public void setRange1(String range1) {
        this.range1 = range1;
    }

    public FieldVo(String id, String code, String enName, String name, String required, String defaultValue, String maxsize, String type, String primary1, Integer pxh, String range1, String defination, Integer maxContains, String comments, String security, String state) {
        this.id = id;
        this.code = code;
        this.enName = enName;
        this.name = name;
        this.required = required;
        this.defaultValue = defaultValue;
        this.maxsize = maxsize;
        this.type = type;
        this.primary1 = primary1;
        this.pxh = pxh;
        this.range1 = range1;
        this.defination = defination;
        this.maxContains = maxContains;
        this.comments = comments;
        this.security = security;
        this.state = state;
    }

    public FieldVo(){
        super();
    }
}
