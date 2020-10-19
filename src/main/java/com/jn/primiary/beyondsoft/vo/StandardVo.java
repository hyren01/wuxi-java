package com.jn.primiary.beyondsoft.vo;

import com.jn.primiary.beyondsoft.entity.Standard;
import com.jn.primiary.commons.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * @Des 审核列表VO
 * @Author chenhong
 * @Date 2019/9/28 16:46
 */
public class StandardVo implements Comparator<StandardVo> {

    private String code;
    private String name;
    private String createPerson;
    private String createTime;
    private String isAuth;
    private String updateTime;
    private String id;
    private String batchNo;
    private String dataType;
    private String categoryId;
    private String count;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public StandardVo(String code, String name, String createPerson, String createTime, String isAuth, String updateTime,
                      String id, String batchNo, String dataType, String categoryId, String count) {
        this.code = code;
        this.name = name;
        this.createPerson = createPerson;
        this.createTime = createTime;
        this.isAuth = isAuth;
        this.updateTime = updateTime;
        this.id = id;
        this.batchNo = batchNo;
        this.dataType = dataType;
        this.categoryId = categoryId;
        this.count = count;
    }

    public StandardVo(String code, String name, String createPerson, String createTime, String isAuth, String updateTime,
                      String id, String batchNo, String dataType, String categoryId) {
        this.code = code;
        this.name = name;
        this.createPerson = createPerson;
        this.createTime = createTime;
        this.isAuth = isAuth;
        this.updateTime = updateTime;
        this.id = id;
        this.batchNo = batchNo;
        this.dataType = dataType;
        this.categoryId = categoryId;
    }


    public StandardVo() {
        super();
    }

    @Override
    public int compare(StandardVo o1, StandardVo o2) {
        long result = 0L;
        String o1time = StringUtils.isBlank(o1.getUpdateTime()) ? o1.getCreateTime() : o1.getUpdateTime();
        String o2time = StringUtils.isBlank(o2.getUpdateTime()) ? o2.getCreateTime() : o2.getUpdateTime();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = sdf.parse(o1time);
            Date d2 = sdf.parse(o2time);
            result = d2.getTime() - d1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result > 0) {
            return 1;
        } else if (result == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}

	
