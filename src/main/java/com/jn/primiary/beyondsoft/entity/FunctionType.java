package com.jn.primiary.beyondsoft.entity;

/**
 * 标准审核使用的功能类型
 * @author gzz
 *
 */
public enum FunctionType {
	ALL(0, "标准与码表管理"), STANDARD(1, "标准管理"), CODEINFO(2, "码表管理");
	
	private int code;
	private String message;
	
	FunctionType(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
