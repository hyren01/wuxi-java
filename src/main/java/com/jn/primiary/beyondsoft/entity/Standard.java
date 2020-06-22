package com.jn.primiary.beyondsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jn.primiary.beyondsoft.entity.primiarykey.StandardCompositeKey;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "tb_stdgl_schemamodule")
@Entity
@IdClass(StandardCompositeKey.class)
public class Standard {

	@Id
	@Column(name = "id", nullable = false, length = 32)
    private String id="";//目录I

    @Id
    @Column(name = "version", nullable = true, length = 32)
    private String version="";//	版本
    
	@Column(name = "schemacode", nullable = false, length = 50)
    private String code="";//	标准编码
    
	@Column(name = "enname", nullable = true, length = 100)
	private String enName="";//	标准英文名称
  
	@Column(name = "schemaname", nullable = true, length = 100)
    private String name="";//	标准中文名称
    
	@Column(name = "type", nullable = false, length = 1)
	private Integer type;//	41表示该条信息是标准信息

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Column(name = "createtime", nullable = true)
	private Date createTime;//	创建时间
    
	@Column(name = "creator", nullable = true, length = 50)
    private String createPerson="";//	创建人

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "update_time", nullable = true, length = 50)
    private Date updateTime;//	更新时间

    @Column(name = "update_person", nullable = true, length = 50)
    private String updatePerson="";//	更新人
    
    @Column(name = "description", nullable = true, length = 2048)
    private String description="";//	标准描述
    
    @Column(name = "datasource", nullable = true, length = 256)
    private String dataSource="";//	数据源
    
    
    @Column(name = "categoryid", nullable = false, length = 50)
    private String categoryId="";//挂载目录ID

    @Column(name = "count", nullable = true, length = 225)
    private int count=0;//标准检测次数

    @Transient
    private List<StandardField> standardField;
    @Transient
    private List<StandardObject> standardObject;

    public List<StandardObject> getStandardObject() {
        return standardObject;
    }

    public void setStandardObject(List<StandardObject> standardObject) {
        this.standardObject = standardObject;
    }

    public List<StandardField> getStandardField() {
        return standardField;
    }

    public void setStandardField(List<StandardField> standardField) {
        this.standardField = standardField;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
