package com.jn.primiary.beyondsoft.entity;

/**
 * 布尔类型
 */
public enum BooleanFlag {

	SHI(0, "是"), FOU(1, "否");

	private int code;
	private String desc;

	BooleanFlag(int code, String desc) {
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
        return "BooleanFlag{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
	
}
