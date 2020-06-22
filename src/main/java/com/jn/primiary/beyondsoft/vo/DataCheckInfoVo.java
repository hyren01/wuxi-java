package com.jn.primiary.beyondsoft.vo;

import java.util.List;

/**
 * @Des 用于数据检测信息
 */
public class DataCheckInfoVo {
    /**
     * 字段id、字段名、字段检测标识等使用下标来表示
     */
    private String tableName;
    private List<String> colIds;
    private List<String> colNames;
    private List<Integer> checkLengthFlags;
	private List<Integer> checkMaxLengthValues;
	private List<Integer> checkNullFlags;
	private List<Integer> checkCodeFlags;
	private List<List<String>> checkCodeValues;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColIds() {
        return colIds;
    }

    public void setColIds(List<String> colIds) {
        this.colIds = colIds;
    }

    public List<String> getColNames() {
        return colNames;
    }

    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }

    public List<Integer> getCheckLengthFlags() {
        return checkLengthFlags;
    }

    public void setCheckLengthFlags(List<Integer> checkLengthFlags) {
        this.checkLengthFlags = checkLengthFlags;
    }

    public List<Integer> getCheckMaxLengthValues() {
        return checkMaxLengthValues;
    }

    public void setCheckMaxLengthValues(List<Integer> checkMaxLengthValues) {
        this.checkMaxLengthValues = checkMaxLengthValues;
    }

    public List<Integer> getCheckNullFlags() {
        return checkNullFlags;
    }

    public void setCheckNullFlags(List<Integer> checkNullFlags) {
        this.checkNullFlags = checkNullFlags;
    }

    public List<Integer> getCheckCodeFlags() {
        return checkCodeFlags;
    }

    public void setCheckCodeFlags(List<Integer> checkCodeFlags) {
        this.checkCodeFlags = checkCodeFlags;
    }

    public List<List<String>> getCheckCodeValues() {
        return checkCodeValues;
    }

    public void setCheckCodeValues(List<List<String>> checkCodeValues) {
        this.checkCodeValues = checkCodeValues;
    }

    public DataCheckInfoVo(String tableName, List<String> colIds, List<String> colNames, List<Integer> checkLengthFlags,
                           List<Integer> checkMaxLengthValues, List<Integer> checkNullFlags, List<Integer> checkCodeFlags,
                           List<List<String>> checkCodeValues) {
        this.tableName = tableName;
        this.colIds = colIds;
        this.colNames = colNames;
        this.checkLengthFlags = checkLengthFlags;
        this.checkMaxLengthValues = checkMaxLengthValues;
        this.checkNullFlags = checkNullFlags;
        this.checkCodeFlags = checkCodeFlags;
        this.checkCodeValues = checkCodeValues;
    }

    public DataCheckInfoVo() {
        super();
    }
}

	
