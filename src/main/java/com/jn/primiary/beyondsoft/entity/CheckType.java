package com.jn.primiary.beyondsoft.entity;

/**
 * 数据质量检测任务使用，检测类型枚举
 */
public enum CheckType {
	
	CODEVALUE(1, "码值检测"), RANGE(2, "范围检测");
	
	private int code;
	private String desc;
	
	CheckType(int code, String desc) {
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
        return "CheckType{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
	
}
