package com.jn.primiary.beyondsoft.entity;

/**
 * 审核状态
 */
public enum AuthType {

	SHTG(1, "审核通过"), SHJJ(2, "审核拒绝"), DSH(3, "待审核"),DSQ(4, "待申请"),BIANJI(5,"编辑");

	private int code;
	private String desc;

	AuthType(int code, String desc) {
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
        return "AuthType{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
	
}
