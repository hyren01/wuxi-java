package com.jn.primiary.beyondsoft.entity;

/**
 * 数据质量检测任务使用，检测类型枚举
 */
public enum DateBaseType {
	
	ODPS(1, "odps"), HIVE(2, "hive"), HBASE(2, "hbase"), MYSQL(2, "mysql"), GBASE(2, "gbase");
	
	private int code;
	private String desc;
	
	DateBaseType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	@Override
    public String toString() {
        return "DateBaseType{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
	
}
