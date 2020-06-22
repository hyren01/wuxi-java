package com.jn.primiary.beyondsoft.entity;

/**
 * 操作标识
 */
public enum DataType {

	ADD(1, "新增"), UPDATE(2, "修改"), DELETE(3, "删除");

	private int code;
	private String desc;

	DataType(int code, String desc) {
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
        return "DataType{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
	
}
