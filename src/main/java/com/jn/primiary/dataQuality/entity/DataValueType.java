package com.jn.primiary.dataQuality.entity;



public enum DataValueType {
	STRING("1", "字符型"), INTEGER("2", "整型"), DECIMAL("3", "浮点型");
	
	private String code;
	private String message;
	
	DataValueType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	
	public String getCode() {
	
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

