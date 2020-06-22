package com.jn.primiary.dataQuality.entity;


/**
 * 
 * ClassName: RunStatus <br/>
 * Function: 数据质量检测运行状态. <br/>
 * Date: 2019年8月20日 下午3:08:38 <br/>
 *
 * @author 13616
 * @version 
 * @since JDK 1.7
 */
public enum RunStatus {
	WAITING("1", "等待中"), RUNNING("2", "运行中"), SUCCESS("3", "运行成功"), FAILD("4", "运行失败");
	
	private String code;
	private String message;
	
	RunStatus(String code, String message) {
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

