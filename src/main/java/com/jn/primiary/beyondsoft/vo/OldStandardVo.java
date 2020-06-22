package com.jn.primiary.beyondsoft.vo;

/**
 * @Des 审核列表VO
 * @Author chenhong
 * @Date 2019/9/28 16:46
 */
public class OldStandardVo {

    private String id;
    private String code;
    private String name;
    private String enName;
    private String createTime;
    private String updateTime;
    private String version;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public OldStandardVo(String id, String code, String name, String enName, String createTime, String updateTime, String version) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.enName = enName;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.version = version;
    }

    public OldStandardVo() {
    }
}

	
