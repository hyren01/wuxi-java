package com.jn.primiary.metadata.entity;

public class RestFullReturn {

	private String retcode;//返回状态值
	private String message;//操作说明（例如：创建成功、创建失败）
	private Object data;//返回的数据
	private String errorDetail;//错误详细信息
	public RestFullReturn() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getErrorDetail() {
		return errorDetail;
	}
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
}
