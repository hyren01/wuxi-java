package com.jn.primiary.beyondsoft.entity;

/**
 * 操作标识
 */
public enum Status {

	JIHUO(1, "激活"), SHIXIAO(2, "失效");

	private int code;
	private String desc;

	Status(int code, String desc) {
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
        return "Status{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
	
}
