package com.jn.primiary.dataQuality.entity;

/**
 * 
 * ClassName: QualityCheckType <br/>
 * Function: 数据质量检测类型. <br/>
 * Date: 2019年8月20日 下午2:57:45 <br/>
 *
 * @author 13616
 * @version 
 * @since JDK 1.7
 */
public enum QualityCheckType {
	ENUM("1", "枚举检测"), RANGE("2", "范围检测"), DATEFORMAT("3", "日期格式");
	
	private String code;
	private String message;
	
	QualityCheckType(String code, String message) {
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

